package com.eziosoft.moviesInParis.navigation

import org.koin.dsl.module

val navigationModule = module {
    single { SharedParameters() }
    single { ActionDispatcher(get()) }
}
