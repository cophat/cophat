package com.jodi.cophat.feature.hospital.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemHospitalPresenter
import com.jodi.cophat.databinding.FragmentHospitalBinding
import com.jodi.cophat.feature.hospital.adapter.HospitalListener
import com.jodi.cophat.feature.hospital.adapter.HospitalRecyclerAdapter
import com.jodi.cophat.feature.hospital.viewmodel.HospitalViewModel
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HospitalFragment : BaseFragment<FragmentHospitalBinding>(), HospitalListener {

    private val viewModel: HospitalViewModel by viewModel()

    private val adapter: HospitalRecyclerAdapter by inject()

    override fun getLayout(): Int {
        return R.layout.fragment_hospital
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvTitleHospital,
            binding.tvSubtitleHospital,
            binding.rvHospital,
            binding.ivAddHospital
        )

        viewModel.initialize()

        hospitalObservers()
        hospitalAdapter()
        hospitalListener()
    }

    private fun hospitalAdapter() {
        adapter.hospitalListener = this
        binding.rvHospital.adapter = adapter
    }

    private fun hospitalListener() {
        binding.ivAddHospital.setOnClickListener {
            findNavController().navigate(
                HospitalFragmentDirections.actionHospitalFragmentToHospitalDialog(
                    viewModel.getAddHospital()
                )
            )
        }
    }

    private fun hospitalObservers() {
        viewModel.hospitalPresenter.observe(this,
            Observer {
                adapter.setItems(it.hospitals)
            })
    }

    override fun onEdit(item: ItemHospitalPresenter) {
        findNavController().navigate(
            HospitalFragmentDirections.actionHospitalFragmentToHospitalDialog(
                viewModel.getEditHospital(item),
                item.hospitalFirebaseKey
            )
        )
    }

    override fun onRemove(item: ItemHospitalPresenter) {
        findNavController().navigate(
            HospitalFragmentDirections.actionHospitalFragmentToHospitalExcludeDialog(
                item.hospitalFirebaseKey
            )
        )
    }
}
