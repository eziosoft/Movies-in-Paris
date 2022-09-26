package com.eziosoft.parisinnumbers

import com.eziosoft.parisinnumbers.presentation.ProjectDispatchers
import com.eziosoft.parisinnumbers.presentation.ui.detailsScreen.DetailsScreenViewModel
import com.eziosoft.parisinnumbers.presentation.ui.listScreen.ListScreenViewModel
import com.eziosoft.parisinnumbers.presentation.ui.mapScreen.MapScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        ListScreenViewModel(repository = get(), get(), get(), get())
    }

    viewModel {
        DetailsScreenViewModel(
            openApiRepository = get(),
            actionDispatcher = get(),
            movieDbRepository = get(),
            projectDispatchers = get()
        )
    }

    viewModel {
        MapScreenViewModel(get(), get(), get())
    }

    single {
        ProjectDispatchers()
    }
}