// AQUI

package com.jodi.cophat.feature.questionnaires.fragment

import android.Manifest
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.QuestionnaireReport
import com.jodi.cophat.data.presenter.ItemQuestionnairePresenter
import com.jodi.cophat.databinding.FragmentListQuestionnairesBinding
import com.jodi.cophat.feature.patient.adapter.QuestionnaireRecyclerAdapter
import com.jodi.cophat.feature.questionnaires.activity.QuestionnairesActivity
import com.jodi.cophat.feature.questionnaires.adapter.QuestionnaireListener
import com.jodi.cophat.feature.questionnaires.viewmodel.QuestionnairesViewModel
import com.jodi.cophat.helper.showToast
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.time.ExperimentalTime

class ListQuestionnairesFragment : BaseFragment<FragmentListQuestionnairesBinding>(),
    QuestionnaireListener {

    private val viewModel: QuestionnairesViewModel by sharedViewModel()

    private val adapter: QuestionnaireRecyclerAdapter by inject()

    lateinit var questionnaireListener: QuestionnaireListener

    override fun getLayout(): Int {
        return R.layout.fragment_list_questionnaires
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    @ExperimentalTime
    override fun initBinding() {
        setViews(
            binding.tvTitleQuestionnaires,
            binding.tvSubtitleQuestionnaires,
            binding.btExcelQuestionnaires,
            binding.rvQuestionnaires
        )

        viewModel.initialize()

        configureAdapter()
        configureListeners()
        configureObservers()
        checkWritePermission()
    }

    private fun configureAdapter() {
        adapter.questionnaireListener = this
        binding.rvQuestionnaires.adapter = adapter
        binding.btExcelQuestionnaires.isEnabled = true
    }

    private fun configureListeners() {
        binding.btExcelQuestionnaires.setOnClickListener {
            generateQuestionnaires()?.let { questionnaires ->
                showDialog(questionnaires)
            }
        }
    }

    @ExperimentalTime
    private fun configureObservers() {
        viewModel.questionnaireReportPresenter.observe(this,
            Observer {
                var temp: MutableList<ItemQuestionnairePresenter> = ArrayList<ItemQuestionnairePresenter>()
                it.questionnaireReports.map { questionnaireReport ->
                    temp.add(viewModel.convertToPresenter(questionnaireReport))
                }
                adapter.setItems(temp)
            })

        viewModel.hasPermission.observe(this, Observer {
            if (!it) {
                context?.showToast(getString(R.string.turn_on_permission))
            }
        })
    }

    private fun checkWritePermission() {
        val baseActivity = activity as QuestionnairesActivity
        baseActivity.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun generateQuestionnaires(): Array<QuestionnaireReport>? {
        return adapter.presenterList
            .map { snapshot ->
                val questionnaire = snapshot.questionnaireReport
                viewModel.getArgsByQuestionnaire(questionnaire)!!
            }
            .toTypedArray()
    }

    override fun onClickExcel(item: QuestionnaireReport) {
        showDialog(viewModel.getArrayByQuestionnaire(item))
    }

    private fun showDialog(questionnaires: Array<QuestionnaireReport>) {
        viewModel.hasPermission.value?.let {
            if (it) {
                findNavController().navigate(
                    ListQuestionnairesFragmentDirections.actionListQuestionnairesFragmentToExportExcelDialog(
                        questionnaires
                    )
                )
            } else {
                context?.showToast(getString(R.string.turn_on_permission))
            }
        }
    }

}
