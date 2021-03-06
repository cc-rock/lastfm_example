package com.example.lastfm.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lastfm.common.data.entities.ArtistSearchItem
import com.example.lastfm.search.domain.ArtistSearchNavigator
import com.example.lastfm.search.domain.ArtistSearchRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ArtistSearchViewModel(
    private val repository: ArtistSearchRepository,
    private val navigator: ArtistSearchNavigator,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel(), CoroutineScope {

    companion object {
        private const val PAGE_SIZE = 50
    }

    // coroutine scope implementation
    private val mainJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = ioDispatcher + mainJob

    private var searchJob: Job? = null

    private val mutableViewData = MutableLiveData<ArtistSearchViewData>()
    val viewData: LiveData<ArtistSearchViewData> = mutableViewData

    fun newSearch(query: String) {
        performSearch(query, emptyList(), 0, true)
    }

    fun loadMoreResults() {
        viewData.value?.let { currentData ->
            performSearch(
                currentData.searchQuery,
                currentData.results,
                currentData.totalCount,
                false
            )
        }
    }

    fun goToArtistDetail(artistId: String) {
        navigator.goToArtistDetail(artistId)
    }

    private fun performSearch(
        query: String,
        currentResults: List<ArtistSearchItem>,
        currentTotalCount: Int,
        debounce: Boolean
    ) {
        searchJob?.cancel()

        // launch coroutine in our scope (on background thread)
        searchJob = launch {

            if (debounce) {
                delay(300)
            }

            // show loading
            mutableViewData.postValue(
                ArtistSearchViewData(
                    query, currentResults, currentTotalCount,
                    loading = true, error = false
                )
            )

            val results = try {
                repository.searchArtistsByName(query, currentResults.size, PAGE_SIZE)
            } catch (t: Throwable) {
                mutableViewData.postValue(
                    ArtistSearchViewData(
                        query, currentResults, currentTotalCount,
                        loading = false, error = true
                    )
                )
                return@launch
            }

            mutableViewData.postValue(
                ArtistSearchViewData(
                    query, currentResults + results.artists, results.totalCount,
                    loading = false, error = false
                )
            )
        }
    }

    override fun onCleared() {
        mainJob.cancel()
    }

}