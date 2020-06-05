package com.jodi.cophat.di

import com.jodi.cophat.feature.configure.viewmodel.ConfigureViewModel
import com.jodi.cophat.feature.patient.viewmodel.PatientViewModel
import com.jodi.cophat.feature.generate.viewmodel.GenerateCodeViewModel
import com.jodi.cophat.feature.intro.viewmodel.IntroViewModel
import com.jodi.cophat.feature.pending.viewmodel.PendingViewModel
import com.jodi.cophat.feature.questionnaires.viewmodel.ExportExcelViewModel
import com.jodi.cophat.feature.questionnaires.viewmodel.QuestionnairesViewModel
import com.jodi.cophat.feature.questions.viewmodel.CompleteViewModel
import com.jodi.cophat.feature.questions.viewmodel.QuestionsViewModel
import com.jodi.cophat.feature.questions.viewmodel.SubQuestionViewModel
import com.jodi.cophat.feature.register.viewmodel.RegisterParentsViewModel
import com.jodi.cophat.ui.base.dialog.ErrorViewModel
import com.jodi.cophat.ui.base.view.BottomButtonsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ErrorViewModel(get()) }

    viewModel { BottomButtonsViewModel(get()) }

    viewModel { IntroViewModel(get(), get()) }

    viewModel { QuestionnairesViewModel(get(), get()) }

    viewModel { ExportExcelViewModel(get()) }

    viewModel { PatientViewModel(get(), get(), get()) }

    viewModel { ConfigureViewModel(get(), get()) }

    viewModel { PendingViewModel(get(), get()) }

    viewModel { GenerateCodeViewModel(get(), get()) }

    viewModel { RegisterParentsViewModel(get()) }

    viewModel { QuestionsViewModel(get()) }

    viewModel { SubQuestionViewModel(get(), get()) }

    viewModel { CompleteViewModel(get()) }
}