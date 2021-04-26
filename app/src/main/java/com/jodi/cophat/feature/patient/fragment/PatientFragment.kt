package com.jodi.cophat.feature.patient.fragment

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.presenter.ItemPatientPresenter
import com.jodi.cophat.databinding.FragmentPatientBinding
import com.jodi.cophat.feature.patient.adapter.PatientListener
import com.jodi.cophat.feature.patient.adapter.PatientRecyclerAdapter
import com.jodi.cophat.feature.patient.viewmodel.PatientViewModel
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PatientFragment : BaseFragment<FragmentPatientBinding>(), PatientListener {

    private val viewModel: PatientViewModel by viewModel()

    private val adapter: PatientRecyclerAdapter by inject()

    override fun getLayout(): Int {
        return R.layout.fragment_patient
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvTitlePatient,
            binding.tvSubtitlePatient,
            binding.rvPatient,
            binding.ivAddPatient
        )

        viewModel.initialize()

        configureObservers()
        configureAdapter()
        configureListener()
    }

    private fun configureAdapter() {
        adapter.patientListener = this
        binding.rvPatient.adapter = adapter
    }

    private fun configureListener() {
        binding.ivAddPatient.setOnClickListener {
            findNavController().navigate(
                PatientFragmentDirections.actionPatientFragmentToPatientDialog(
                    viewModel.getAddPatient()
                )
            )
        }
    }

    private fun configureObservers() {
        viewModel.patientPresenter.observe(this,
            Observer {
                adapter.setItems(it.patients)
            })
    }

    override fun onEdit(item: ItemPatientPresenter) {
        findNavController().navigate(
            PatientFragmentDirections.actionPatientFragmentToPatientDialog(
                viewModel.getEditPatient(item),
                item.patientFirebaseKey
            )
        )
    }

    override fun onRemove(item: ItemPatientPresenter) {
        findNavController().navigate(
            PatientFragmentDirections.actionPatientFragmentToConfigureExcludeDialog(
                item.patientFirebaseKey
            )
        )
    }
}
