package com.matheus.cophat.di

import com.matheus.cophat.feature.configure.viewmodel.ConfigureViewModel
import com.matheus.cophat.feature.generate.viewmodel.GenerateCodeViewModel
import com.matheus.cophat.feature.intro.viewmodel.IntroViewModel
import com.matheus.cophat.feature.questionnaires.viewmodel.ExportExcelViewModel
import com.matheus.cophat.feature.questionnaires.viewmodel.QuestionnairesViewModel
import com.matheus.cophat.feature.questions.viewmodel.CompleteViewModel
import com.matheus.cophat.feature.questions.viewmodel.QuestionsViewModel
import com.matheus.cophat.feature.questions.viewmodel.SubQuestionViewModel
import com.matheus.cophat.feature.register.viewmodel.RegisterInternalViewModel
import com.matheus.cophat.feature.register.viewmodel.RegisterParentsViewModel
import com.matheus.cophat.feature.register.viewmodel.RegisterPatientViewModel
import com.matheus.cophat.feature.register.viewmodel.RegisterSchoolViewModel
import com.matheus.cophat.ui.base.dialog.ErrorViewModel
import com.matheus.cophat.ui.base.view.BottomButtonsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ErrorViewModel(get()) }

    viewModel { BottomButtonsViewModel(get()) }

    viewModel { IntroViewModel(get(), get()) }

    viewModel { QuestionnairesViewModel(get(), get()) }

    viewModel { ExportExcelViewModel(get()) }

    viewModel { ConfigureViewModel(get(), get()) }

    viewModel { GenerateCodeViewModel(get()) }

    viewModel { RegisterParentsViewModel(get(), get()) }

    viewModel { RegisterPatientViewModel(get(), get()) }

    viewModel { RegisterInternalViewModel(get(), get()) }

    viewModel { RegisterSchoolViewModel(get(), get()) }

    viewModel { QuestionsViewModel(get()) }

    viewModel { SubQuestionViewModel(get(), get()) }

    viewModel { CompleteViewModel(get()) }
}