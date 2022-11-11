package com.eziosoft.moviesInParis.presentation.ui.listScreen

import com.eziosoft.moviesInParis.MainCoroutineRule
import com.eziosoft.moviesInParis.domain.Movie
import com.eziosoft.moviesInParis.domain.repository.DBState
import com.eziosoft.moviesInParis.domain.repository.LocalDatabaseRepository
import com.eziosoft.moviesInParis.navigation.Action
import com.eziosoft.moviesInParis.navigation.ActionDispatcher
import com.eziosoft.moviesInParis.navigation.SharedParameters
import com.eziosoft.moviesInParis.presentation.ProjectDispatchers
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ListScreenViewModelTest {

    @get:Rule
    val rule = MainCoroutineRule()

    private val sampleMovie = Movie(
        "id",
        "address",
        "year",
        "ardt",
        0.0,
        0.0,
        "startDate",
        "endDate",
        "placeId",
        "producer",
        "realisation",
        "title",
        "type"
    )

    private val dbRepo: LocalDatabaseRepository = mockk {
        coEvery { getPaged(any(), any(), any()) } returns listOf(sampleMovie)
        every { dbStateFlow } returns MutableStateFlow(DBState.Ready)
    }
    private val actionDispatcher: ActionDispatcher = ActionDispatcher(SharedParameters())
    private val projectDispatchers = ProjectDispatchers(
        mainDispatcher = Dispatchers.Main,
        ioDispatcher = Dispatchers.Main
    )

    @Test
    fun `check if db is queried when search`() = runTest {
         ListScreenViewModel(
            dbRepository = dbRepo,
            actionDispatcher = actionDispatcher,
            projectDispatchers = projectDispatchers
         )

        actionDispatcher.dispatchAction(Action.SearchMovie("movie"))
        advanceUntilIdle()
        coVerify { dbRepo.getPaged(any(), any(), "movie") }
    }
}
