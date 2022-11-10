package com.eziosoft.moviesInParis.presentation.ui.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eziosoft.moviesInParis.navigation.Action
import com.eziosoft.moviesInParis.navigation.ActionDispatcher
import kotlinx.coroutines.launch

class MainScreenViewModel(
    val actionDispatcher: ActionDispatcher
) : ViewModel() {

    fun search(text: String) {
        viewModelScope.launch {
            actionDispatcher.dispatchAction(Action.SearchMovie(text))
        }
    }
}
