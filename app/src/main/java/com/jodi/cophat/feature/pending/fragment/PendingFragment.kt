package com.jodi.cophat.feature.pending.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemPendingPresenter
import com.jodi.cophat.databinding.FragmentPendingBinding
import com.jodi.cophat.feature.pending.adapter.PendingListener
import com.jodi.cophat.feature.pending.adapter.PendingRecyclerAdapter
import com.jodi.cophat.feature.pending.viewmodel.PendingViewModel
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PendingFragment : BaseFragment<FragmentPendingBinding>(), PendingListener {

    private val viewModel: PendingViewModel by viewModel()

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
        viewModel.pendingPresenter.observe(this,
            Observer {
                adapter.setItems(it.pendings)
            })
    }

//    override fun onEdit(item: ItemPendingPresenter) {
//        findNavController().navigate(
//            PendingFragmentDirections.actionPendingFragmentToPendingDialog(
//                viewModel.getEditPending(item),
//                item.identifyCode
//            )
//        )
//    }

    override fun onRemove(item: ItemPendingPresenter) {
        findNavController().navigate(
            PendingFragmentDirections.actionPendingFragmentToConfigureExcludeDialog(
                item.keyQuestionnaire, item.parentPosition.toString(), item.typeInterviewee
            )
        )
    }
}
