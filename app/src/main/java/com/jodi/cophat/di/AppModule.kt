package com.jodi.cophat.di

import androidx.paging.PagedList
import com.jodi.cophat.feature.configure.adapter.AdminRecyclerAdapter
import com.jodi.cophat.feature.patient.adapter.PatientRecyclerAdapter
import com.jodi.cophat.feature.patient.adapter.QuestionnaireRecyclerAdapter
import com.jodi.cophat.feature.pending.adapter.PendingRecyclerAdapter
import com.jodi.cophat.feature.questions.adapter.SubQuestionRecyclerAdapter
import com.jodi.cophat.helper.ExportWorkbook
import com.jodi.cophat.helper.ResourceManager
import org.koin.dsl.module

val appModule = module {
    single {
        ResourceManager(get())
    }

    factory {
        PatientRecyclerAdapter()
    }

    factory {
        QuestionnaireRecyclerAdapter()
    }

    factory {
        PendingRecyclerAdapter()
    }

    factory {
        AdminRecyclerAdapter()
    }

    factory {
        SubQuestionRecyclerAdapter()
    }

    single {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(10)
            .setPageSize(20)
            .build()
    }

    factory {
        ExportWorkbook(get(), get())
    }
}