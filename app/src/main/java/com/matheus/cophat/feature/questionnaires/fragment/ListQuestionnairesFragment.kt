package com.matheus.cophat.feature.questionnaires.fragment

import android.Manifest
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.firebase.ui.database.paging.DatabasePagingOptions
import com.firebase.ui.database.paging.FirebaseRecyclerPagingAdapter
import com.firebase.ui.database.paging.LoadingState
import com.matheus.cophat.R
import com.matheus.cophat.data.local.entity.Questionnaire
import com.matheus.cophat.databinding.FragmentListQuestionnairesBinding
import com.matheus.cophat.feature.questionnaires.activity.QuestionnairesActivity
import com.matheus.cophat.feature.questionnaires.adapter.QuestionnaireListener
import com.matheus.cophat.feature.questionnaires.adapter.QuestionnaireViewHolder
import com.matheus.cophat.feature.questionnaires.viewmodel.QuestionnairesViewModel
import com.matheus.cophat.helper.showToast
import com.matheus.cophat.ui.BaseFragment
import com.matheus.cophat.ui.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListQuestionnairesFragment : BaseFragment<FragmentListQuestionnairesBinding>(),
    QuestionnaireListener {

    private val viewModel: QuestionnairesViewModel by sharedViewModel()
    private val config: PagedList.Config by inject()
    lateinit var options: DatabasePagingOptions<Questionnaire>
    lateinit var adapter: FirebaseRecyclerPagingAdapter<Questionnaire, QuestionnaireViewHolder>

    override fun getLayout(): Int {
        return R.layout.fragment_list_questionnaires
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvTitleQuestionnaires,
            binding.tvSubtitleQuestionnaires,
            binding.btExcelQuestionnaires,
            binding.rvQuestionnaires
        )

        configureAdapter()
        configureListeners()
        configureObservers()
        checkWritePermission()
    }

    private fun configureAdapter() {
        options = DatabasePagingOptions.Builder<Questionnaire>()
            .setLifecycleOwner(this)
            .setQuery(viewModel.getQuery(), config, Questionnaire::class.java)
            .build()

        adapter = object :
            FirebaseRecyclerPagingAdapter<Questionnaire, QuestionnaireViewHolder>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): QuestionnaireViewHolder {
                return QuestionnaireViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_questionnaire,
                        parent,
                        false
                    ), this@ListQuestionnairesFragment
                )
            }

            override fun onBindViewHolder(
                holder: QuestionnaireViewHolder,
                position: Int,
                model: Questionnaire
            ) {
                holder.bind(viewModel.convertToPresenter(model), position)
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                when (state) {
                    LoadingState.LOADING_INITIAL, LoadingState.LOADING_MORE ->
                        viewModel.isLoading.postValue(true)
                    LoadingState.LOADED ->
                        viewModel.isLoading.postValue(false)
                    LoadingState.FINISHED -> {
                        viewModel.isLoading.postValue(false)
                        binding.btExcelQuestionnaires.alpha = 1f
                        binding.btExcelQuestionnaires.isEnabled = true
                        context?.showToast(getString(R.string.loaded_all))
                    }
                    LoadingState.ERROR -> retry()
                }
            }
        }

        binding.rvQuestionnaires.adapter = adapter
    }

    private fun configureListeners() {
        binding.btExcelQuestionnaires.setOnClickListener {
            generateQuestionnaires()?.let { questionnaires ->
                showDialog(questionnaires)
            }
        }
    }

    private fun configureObservers() {
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

    private fun generateQuestionnaires(): Array<Questionnaire>? {
        return adapter.currentList
            ?.map { snapshot ->
                val questionnaire = snapshot.getValue(Questionnaire::class.java)
                viewModel.getArgsByQuestionnaire(questionnaire)!!
            }
            ?.toTypedArray()
    }

    override fun onClickExcel(item: Questionnaire) {
        showDialog(viewModel.getArrayByQuestionnaire(item))
    }

    private fun showDialog(questionnaires: Array<Questionnaire>) {
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

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
