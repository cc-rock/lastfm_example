package com.example.lastfm.search.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.lastfm.common.data.entities.ArtistSearchItem
import com.example.lastfm.common.data.entities.ArtistSearchResult
import com.example.lastfm.search.domain.ArtistSearchNavigator
import com.example.lastfm.search.domain.ArtistSearchRepository
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException

class ArtistSearchViewModelTest {

    companion object {
        private val FIRST_RESULT_SET = ArtistSearchResult(300, 0, listOf(
            ArtistSearchItem("1", "Test 1", "url"),
            ArtistSearchItem("2", "Test 2", "url"),
            ArtistSearchItem("3", "Test 3", "url")
        ))
        private val SECOND_RESULT_SET = ArtistSearchResult(300, 3, listOf(
            ArtistSearchItem("4", "Test 4", "url"),
            ArtistSearchItem("5", "Test 5", "url"),
            ArtistSearchItem("6", "Test 6", "url")
        ))
    }

    @get:Rule
    val rule : InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: ArtistSearchRepository
    @Mock
    lateinit var navigator: ArtistSearchNavigator
    @Mock
    lateinit var observer: Observer<ArtistSearchViewData>

    lateinit var viewModel: ArtistSearchViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = ArtistSearchViewModel(repository, navigator, Dispatchers.Unconfined)
        viewModel.viewData.observeForever(observer)
        runBlocking {
            whenever(repository.searchArtistsByName("term", 0, 50)) doReturn FIRST_RESULT_SET
        }
    }

    @Test
    fun `When a new search is launched, the view in reinitialised with blank data and show the loading spinner`() {
        runBlocking {
            viewModel.newSearch("term")
            delay(400)
            verify(observer).onChanged(
                ArtistSearchViewData(
                    "term", emptyList(), 0, loading = true, error = false
                )
            )
        }
    }

    @Test
    fun `When a new search is launched, and an error occurs, the view is reinitialised with blank data an shows an error`() {
        runBlocking {
            whenever(repository.searchArtistsByName("term", 0, 50)) doThrow RuntimeException("test exception")
            viewModel.newSearch("term")
            delay(400)
            verify(observer).onChanged(
                ArtistSearchViewData(
                    "term", emptyList(), 0, loading = false, error = true
                )
            )
        }
    }

    @Test
    fun `When a new search is launched, and it succeeds, the view shows the results`() {
        runBlocking {
            viewModel.newSearch("term")
            delay(400)
            verify(observer).onChanged(
                ArtistSearchViewData(
                    "term", FIRST_RESULT_SET.artists, 300, loading = false, error = false
                )
            )
        }
    }

    @Test
    fun `When multiple searches are launched with less than 300ms between them, only the last one is executed`() {
        runBlocking {
            viewModel.newSearch("t1")
            delay(40)
            viewModel.newSearch("t2")
            delay(290)
            viewModel.newSearch("t3")
            delay(200)
            viewModel.newSearch("term")
            delay(400)
            verify(repository, never()).searchArtistsByName("t1", 0, 50)
            verify(repository, never()).searchArtistsByName("t2", 0, 50)
            verify(repository, never()).searchArtistsByName("t3", 0, 50)
            verify(repository).searchArtistsByName("term", 0, 50)
        }
    }

    @Test
    fun `When more results are requested after a successful search, the loading spinner is shown together with the current results`() {
        runBlocking {
            viewModel.newSearch("term")
            delay(400)
            whenever(repository.searchArtistsByName("term", 3, 50)) doReturn SECOND_RESULT_SET
            viewModel.loadMoreResults()
            verify(observer).onChanged(
                ArtistSearchViewData(
                    "term", FIRST_RESULT_SET.artists, 300, loading = true, error = false
                )
            )
        }
    }

    @Test
    fun `When more results are requested after a successful search, the view is updated with the current results plus the new ones`() {
        runBlocking {
            viewModel.newSearch("term")
            delay(400)
            whenever(repository.searchArtistsByName("term", 3, 50)) doReturn SECOND_RESULT_SET
            viewModel.loadMoreResults()
            verify(observer).onChanged(
                ArtistSearchViewData(
                    "term", FIRST_RESULT_SET.artists + SECOND_RESULT_SET.artists, 300, loading = false, error = false
                )
            )
        }
    }

}