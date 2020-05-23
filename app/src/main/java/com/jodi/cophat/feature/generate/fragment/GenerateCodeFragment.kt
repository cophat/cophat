package com.jodi.cophat.feature.generate.fragment

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.GenderType
import com.jodi.cophat.databinding.FragmentGenerateCodeBinding
import com.jodi.cophat.feature.generate.viewmodel.GenerateCodeViewModel
import com.jodi.cophat.helper.CustomSpinnerListener
import com.jodi.cophat.helper.OnOnlyItemSelectedListener
import com.jodi.cophat.ui.BaseFragment
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class GenerateCodeFragment : BaseFragment<FragmentGenerateCodeBinding>() {

    private val viewModel: GenerateCodeViewModel by viewModel()

    override fun getLayout(): Int {
        return R.layout.fragment_generate_code
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initBinding() {
        setViews(
            binding.tvTitleCode,
            binding.tilChildCode,
            binding.tvHospitalCode,
            binding.sHospitalCode,
            binding.tvAdminCode,
            binding.sAdminCode,
            binding.bbvCode
        )
        binding.presenter = viewModel.presenter

        binding.presenter?.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.validatePresenter()
            }
        })

        configureListeners()
        configureObservers()

        viewModel.initialize()
    }

    private fun <T> generateAdapter(context: Context, list: List<T>): ArrayAdapter<T> {
        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_spinner_item,
            list
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        return adapter
    }

    private fun configureListeners() {

        binding.bbvCode.setBottomButtonsListener(object : BottomButtonsListener {
            override fun onPrimaryClick() {
                viewModel.initiateQuestionnaire()
            }

            override fun onSecondaryClick() {
                activity?.onBackPressed()
            }
        })
    }

    private fun configureObservers() {
        viewModel.navigate.observe(this, Observer {
            findNavController().navigate(it)
        })

        viewModel.isButtonEnabled.observe(this, Observer {
            binding.bbvCode.updatePrimaryButton(it)
        })

        viewModel.admins.observe(this, Observer { admins ->
            context?.let { context ->
                binding.sAdminCode.adapter = generateAdapter(context, admins)
            }

            binding.sAdminCode.onItemSelectedListener = CustomSpinnerListener(
                object : OnOnlyItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        binding.presenter?.admin = admins[position]
                    }
                })
        })

        viewModel.hospitals.observe(this, Observer { hospitals ->
            context?.let { context ->
                binding.sHospitalCode.adapter = generateAdapter(context, hospitals)
            }

            binding.sHospitalCode.onItemSelectedListener = CustomSpinnerListener(
                object : OnOnlyItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        binding.presenter?.hospital = hospitals[position]
                    }
                })
        })
    }
}
