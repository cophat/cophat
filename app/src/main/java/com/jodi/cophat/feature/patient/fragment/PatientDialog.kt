package com.jodi.cophat.feature.patient.fragment

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jodi.cophat.R
import com.jodi.cophat.data.local.entity.*
import com.jodi.cophat.databinding.DialogPatientBinding
import com.jodi.cophat.feature.patient.viewmodel.PatientViewModel
import com.jodi.cophat.helper.*
import com.jodi.cophat.ui.BaseDialog
import com.jodi.cophat.ui.BaseViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsListener
import kotlinx.android.synthetic.main.dialog_patient.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PatientDialog : BaseDialog<DialogPatientBinding>() {

    private val viewModel: PatientViewModel by viewModel()

    private val args: PatientDialogArgs by navArgs()

    lateinit var tempRelationship: String

    lateinit var tempReligion: String

    override fun getLayout(): Int {
        return R.layout.dialog_patient
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun getDialogTag(): String {
        return "dialog_patient"
    }

    override fun initBinding() {
        viewModel.initialize()

        tempRelationship = ""
        tempReligion = ""

        isCancelable = false

        binding.presenter = args.presenter

        binding.presenter?.addOnPropertyChangedCallback(object :
            Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                viewModel.verifyDialogPresenter(binding.presenter)
            }
        })

        viewModel.isButtonEnabled.observe(this, Observer {
            binding.bbvPatient.updatePrimaryButton(it)
        })

        viewModel.statusPatient.observe(this, Observer {
            context?.showToast(it)
            dismiss()
            findNavController().navigate(R.id.action_patientDialog_to_patientFragment)
        })

        configureListeners()
        configureObservers()
        initRadioButton()
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
        MoneyMask(binding.etIncomeSchool)
        DateMask(binding.etBirthdayPatient)

        // RelationShip
        binding.rgRelationship.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbFatherRegister.id ->
                    tempRelationship = RelationshipType.FATHER.relationship
                binding.rbMotherRegister.id ->
                    tempRelationship = RelationshipType.MOTHER.relationship
                binding.rbGrandmotherpRegister.id ->
                    tempRelationship = RelationshipType.GRAND_MOTHER_P.relationship
                binding.rbGrandfatherpRegister.id ->
                    tempRelationship = RelationshipType.GRAND_FATHER_P.relationship
                binding.rbGrandmothermRegister.id ->
                    tempRelationship = RelationshipType.GRAND_MOTHER_M.relationship
                binding.rbGrandfathermRegister.id ->
                    tempRelationship = RelationshipType.GRAND_FATHER_M.relationship
                binding.rbOtherRelationshipRegister.id ->
                    tempRelationship = RelationshipType.OTHER.relationship
            }
            when (checkedId) {
                binding.rbOtherRelationshipRegister.id -> binding.etOtherRelationshipRegister.isEnabled = true
                else -> {
                    binding.etOtherRelationshipRegister.isEnabled = false
                    binding.presenter?.relationship = ""
                }
            }
        }

        // MaritalStatus
        binding.rgMaritalStatusRegister.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbMarriedRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.MARRIED.maritalStatus
                binding.rbAmassedRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.AMASSED.maritalStatus
                binding.rbDivorcedRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.DIVORCED_SEPARATED.maritalStatus
                binding.rbSingleRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.SINGLE.maritalStatus
                binding.rbWidowerRegister.id ->
                    binding.presenter?.maritalStatus = MaritalStatusType.WIDOWER.maritalStatus
            }
        }

        // Gender
        binding.rgGenderCode.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbMaleCode.id ->
                    binding.presenter?.gender = GenderType.MALE.genderType
                binding.rbFemaleCode.id ->
                    binding.presenter?.gender = GenderType.FEMALE.genderType
            }
        }

        // Religion
        binding.tilReligionPatient.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbCatholicRegister.id ->
                    tempReligion = ReligionType.CATHOLIC.religion
                binding.rbEvangelicalRegister.id ->
                    tempReligion = ReligionType.EVANGELICAL.religion
                binding.rbSpiritistRegister.id ->
                    tempReligion = ReligionType.SPIRITIST.religion
                binding.rbOtherRegister.id ->
                    tempReligion = ReligionType.OTHER.religion
                binding.rbNoneRegister.id ->
                    tempReligion = ReligionType.NONE.religion
            }
            when (checkedId) {
                binding.rbOtherRegister.id -> binding.etOtherRegister.isEnabled = true
                else -> {
                    binding.etOtherRegister.isEnabled = false
                    binding.presenter?.religion = ""
                }
            }
        }

        // SchoolFrequency
        binding.rgOutSchool.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId){
                binding.rbOutYesSchool.id ->
                    binding.presenter?.schoolFrequency = SchoolFrequencyType.YES.schoolFrequency
                binding.rbOutNoSchool.id ->
                    binding.presenter?.schoolFrequency = SchoolFrequencyType.NO.schoolFrequency
            }
        }

        // LiveInThisCity
        binding.rgResidentSchool.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId){
                binding.rbResidentYesSchool.id ->
                    binding.presenter?.liveInThisCity = ResidentCityType.YES.liveInThisCity
                binding.rbResidentNoSchool.id ->
                    binding.presenter?.liveInThisCity = ResidentCityType.NO.liveInThisCity
            }
        }

        // EducationDegree
        binding.rgEducationSchool.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId){
                binding.rbIlliterateSchool.id ->
                    binding.presenter?.educationDegree = EducationType.ILLITERATE.education
                binding.rbFundamental1School.id ->
                    binding.presenter?.educationDegree = EducationType.COMPLETE_FUNDAMENTAL_I.education
                binding.rbFundamental2School.id ->
                    binding.presenter?.educationDegree = EducationType.COMPLETE_FUNDAMENTAL_II.education
                binding.rbMediumSchool.id ->
                    binding.presenter?.educationDegree = EducationType.COMPLETE_MEDIUM.education
                binding.rbGraduatedSchool.id ->
                    binding.presenter?.educationDegree = EducationType.GRADUATED.education
            }

        }

        // Schooling
        binding.rgSchoolingSchool1.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId){
                binding.rbPreSchool.id ->
                    binding.presenter?.schooling = SchoolingType.PRE.schooling
                binding.rbFirstSchool.id ->
                    binding.presenter?.schooling = SchoolingType.FIRST_YEAR.schooling
                binding.rbSecondSchool.id ->
                    binding.presenter?.schooling = SchoolingType.SECOND_YEAR.schooling
                binding.rbThirdSchool.id ->
                    binding.presenter?.schooling = SchoolingType.THIRD_YEAR.schooling
                binding.rbFourthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.FOURTH_YEAR.schooling
                binding.rbFifthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.FIFTH_YEAR.schooling
                binding.rbSixthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.SIXTH_YEAR.schooling
                binding.rbSeventhSchool.id ->
                    binding.presenter?.schooling = SchoolingType.SEVENTH_YEAR.schooling
                binding.rbEighthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.EIGHTH_YEAR.schooling
                binding.rbNinthSchool.id ->
                    binding.presenter?.schooling = SchoolingType.NINTH_YEAR.schooling
            }

        }

        binding.bbvPatient.setBottomButtonsListener(object : BottomButtonsListener {
            override fun onPrimaryClick() {
                if(!tempRelationship.equals(RelationshipType.OTHER.relationship) && !tempRelationship.equals("")) {
                    binding.presenter?.relationship = tempRelationship
                }
                if(!tempReligion.equals(ReligionType.OTHER.religion) && !tempReligion.equals("")){
                    binding.presenter?.religion = tempReligion
                }
                viewModel.saveOrUpdatePatient(binding.presenter, args.key)
            }

            override fun onSecondaryClick() {
                dismiss()
            }
        })
    }

    private fun configureObservers() {
        viewModel.navigate.observe(this, Observer {
            findNavController().navigate(it)
        })

        viewModel.isButtonEnabled.observe(this, Observer {
            binding.bbvPatient.updatePrimaryButton(it)
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
                        binding.presenter?.admin = admins[position].name
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
                        binding.presenter?.hospital = hospitals[position].name
                    }
                })
        })
    }

    private fun initRadioButton() {
        // RelationShip
        if(binding.presenter?.relationship.equals(RelationshipType.FATHER.relationship)){
            binding.rbFatherRegister.isChecked = true
        }else if(binding.presenter?.relationship.equals(RelationshipType.MOTHER.relationship)){
            binding.rbMotherRegister.isChecked = true
        }
        else if(binding.presenter?.relationship.equals(RelationshipType.GRAND_MOTHER_P.relationship)){
            binding.rbGrandmotherpRegister.isChecked = true
        }
        else if(binding.presenter?.relationship.equals(RelationshipType.GRAND_FATHER_P.relationship)){
            binding.rbGrandfatherpRegister.isChecked = true
        }
        else if(binding.presenter?.relationship.equals(RelationshipType.GRAND_MOTHER_M.relationship)){
            binding.rbGrandmothermRegister.isChecked = true
        }
        else if(binding.presenter?.relationship.equals(RelationshipType.GRAND_FATHER_M.relationship)){
            binding.rbGrandfathermRegister.isChecked = true
        }else if(!binding.presenter?.relationship.equals("")){
            binding.rbOtherRelationshipRegister.isChecked = true
        }

        // MaritalStatus
        when(binding.presenter?.maritalStatus) {
            MaritalStatusType.MARRIED.maritalStatus -> binding.rbMarriedRegister.isChecked = true
            MaritalStatusType.AMASSED.maritalStatus -> binding.rbAmassedRegister.isChecked = true
            MaritalStatusType.DIVORCED_SEPARATED.maritalStatus -> binding.rbDivorcedRegister.isChecked = true
            MaritalStatusType.SINGLE.maritalStatus -> binding.rbSingleRegister.isChecked = true
            MaritalStatusType.WIDOWER.maritalStatus -> binding.rbWidowerRegister.isChecked = true
        }

        // Gender
        when(binding.presenter?.gender) {
            GenderType.MALE.genderType -> binding.rbMaleCode.isChecked = true
            GenderType.FEMALE.genderType -> binding.rbFemaleCode.isChecked = true
        }

        // Religion
        if(binding.presenter?.religion.equals(ReligionType.CATHOLIC.religion)){
            binding.rbCatholicRegister.isChecked = true
        } else if (binding.presenter?.religion.equals(ReligionType.EVANGELICAL.religion)){
            binding.rbEvangelicalRegister.isChecked = true
        } else if (binding.presenter?.religion.equals(ReligionType.SPIRITIST.religion)){
            binding.rbSpiritistRegister.isChecked = true
        } else if (binding.presenter?.religion.equals(ReligionType.NONE.religion)){
            binding.rbNoneRegister.isChecked = true
        } else if(!binding.presenter?.religion.equals("")){
            binding.rbOtherRegister.isChecked = true
        }

        // SchoolFrequency
        when(binding.presenter?.schoolFrequency) {
            SchoolFrequencyType.YES.schoolFrequency -> binding.rbOutYesSchool.isChecked = true
            SchoolFrequencyType.NO.schoolFrequency -> binding.rbOutNoSchool.isChecked = true
        }

        // LiveInThisCity
        when(binding.presenter?.liveInThisCity) {
            ResidentCityType.YES.liveInThisCity -> binding.rbResidentYesSchool.isChecked = true
            ResidentCityType.NO.liveInThisCity -> binding.rbResidentNoSchool.isChecked = true
        }

        // EducationDegree
        when(binding.presenter?.educationDegree) {
            EducationType.ILLITERATE.education -> binding.rbIlliterateSchool.isChecked = true
            EducationType.COMPLETE_FUNDAMENTAL_I.education -> binding.rbFundamental1School.isChecked = true
            EducationType.COMPLETE_FUNDAMENTAL_II.education -> binding.rbFundamental2School.isChecked = true
            EducationType.COMPLETE_MEDIUM.education -> binding.rbMediumSchool.isChecked = true
            EducationType.GRADUATED.education -> binding.rbGraduatedSchool.isChecked = true
        }

        // Schooling
        when(binding.presenter?.schooling) {
            SchoolingType.PRE.schooling -> binding.rbPreSchool.isChecked = true
            SchoolingType.FIRST_YEAR.schooling -> binding.rbFirstSchool.isChecked = true
            SchoolingType.SECOND_YEAR.schooling -> binding.rbSecondSchool.isChecked = true
            SchoolingType.THIRD_YEAR.schooling -> binding.rbThirdSchool.isChecked = true
            SchoolingType.FOURTH_YEAR.schooling -> binding.rbFourthSchool.isChecked = true
            SchoolingType.FIFTH_YEAR.schooling -> binding.rbFifthSchool.isChecked = true
            SchoolingType.SIXTH_YEAR.schooling -> binding.rbSixthSchool.isChecked = true
            SchoolingType.SEVENTH_YEAR.schooling -> binding.rbSeventhSchool.isChecked = true
            SchoolingType.EIGHTH_YEAR.schooling -> binding.rbEighthSchool.isChecked = true
            SchoolingType.NINTH_YEAR.schooling -> binding.rbNinthSchool.isChecked = true
        }
    }
}