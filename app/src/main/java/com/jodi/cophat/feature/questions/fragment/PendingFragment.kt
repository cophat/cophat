package com.jodi.cophat.feature.questions.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemPendingPresenter
import com.jodi.cophat.data.repository.RegisterRepository
import com.jodi.cophat.databinding.FragmentPendingBinding
import com.jodi.cophat.feature.intro.fragment.BeginFragmentDirections
import com.jodi.cophat.feature.questions.adapter.PendingListener
import com.jodi.cophat.feature.questions.adapter.PendingRecyclerAdapter
import com.jodi.cophat.feature.questions.viewmodel.QuestionsViewModel
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PendingFragment : BaseFragment<FragmentPendingBinding>(),
    PendingListener {

    private val viewModel: QuestionsViewModel by viewModel()

    private val adapter: PendingRecyclerAdapter by inject()

    override fun getLayout(): Int {
        return R.layout.fragment_pending
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvTitlePending,
            binding.tvSubtitlePending,
            binding.rvPending
        )

        viewModel.continueQuestionnaire = true
        viewModel.initialize()

        configureObservers()
        configureAdapter()
        configureListener()
    }

    private fun configureAdapter() {
        adapter.pendingListener = this
        binding.rvPending.adapter = adapter
    }

    private fun configureListener() {
    }

    private fun configureObservers() {
        viewModel.applicationPending.observe(this,
        Observer {
            if(!it.typeInterviewee.equals("Paciente") && it.name.equals("Não informado")){
                findNavController().navigate(
                    BeginFragmentDirections.actionBeginFragmentToRegisterActivity(1)
                )
            }else{
                findNavController().navigate(R.id.action_beginFragment_to_nav_questions)
            }

        })

        viewModel.pendingPresenter.observe(this,
            Observer {
                adapter.setItems(it.pendings)
            })

        viewModel.completeQuestionnaire.observe(this, Observer {
            findNavController().navigate(it)
        })
    }

    override fun onEdit(item: ItemPendingPresenter) {
        runBlocking {
            viewModel.setValues(item)
        }
    }

    override fun onRemove(item: ItemPendingPresenter) {
        findNavController().navigate(
            PendingFragmentDirections.actionPendingFragmentToConfigureExcludeDialog(
                item.keyQuestionnaire, item.parentPosition.toString(), item.typeInterviewee
            )
        )
    }
}
