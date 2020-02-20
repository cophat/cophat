package com.matheus.cophat.di

import androidx.paging.PagedList
import com.matheus.cophat.feature.configure.adapter.AdminRecyclerAdapter
import com.matheus.cophat.feature.questions.adapter.SubQuestionRecyclerAdapter
import com.matheus.cophat.helper.ExportWorkbook
import com.matheus.cophat.helper.ResourceManager
import org.koin.dsl.module

val appModule = module {
    single {
        ResourceManager(get())
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