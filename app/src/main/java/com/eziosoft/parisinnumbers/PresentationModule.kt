package com.eziosoft.parisinnumbers

import com.eziosoft.parisinnumbers.navigation.ActionDispatcher
import com.eziosoft.parisinnumbers.navigation.SharedParameters
import com.eziosoft.parisinnumbers.presentation.ui.detailsScreen.DetailsScreenViewModel
import com.eziosoft.parisinnumbers.presentation.ui.listScreen.ListScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        ListScreenViewModel(repository = get(), get())
    }

    viewModel {
        DetailsScreenViewModel(repository = get(), actionDispatcher = get())
    }

    single { SharedParameters() }
    single { ActionDispatcher(get()) }
}