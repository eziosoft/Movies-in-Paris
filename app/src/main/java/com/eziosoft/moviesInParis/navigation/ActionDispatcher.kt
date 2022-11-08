package com.eziosoft.moviesInParis.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class ActionDispatcher(val sharedParameters: SharedParameters) {
    private val _actionFlow = MutableSharedFlow<Action>(replay = 1)
    val actionFlow = _actionFlow.asSharedFlow()

    suspend fun dispatchAction(action: Action) {
        _actionFlow.emit(action)
    }
}
