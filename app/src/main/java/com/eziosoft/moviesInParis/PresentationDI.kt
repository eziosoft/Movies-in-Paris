package com.eziosoft.moviesInParis

import com.eziosoft.moviesInParis.presentation.ProjectDispatchers
import com.eziosoft.moviesInParis.presentation.ui.listScreen.ListScreenViewModel
import com.eziosoft.moviesInParis.presentation.ui.mapScreen.MapScreenViewModel
import com.eziosoft.moviesInParis.presentation.ui.movieDetailsBottomSheet.MovieDetailsBottomSheetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        ListScreenViewModel(get(), get(), get())
    }

    viewModel {
        MapScreenViewModel(get(), get(), get())
    }

    single {
        ProjectDispatchers()
    }

    viewModel {
        MovieDetailsBottomSheetViewModel(get(), get(), get())
    }
}
