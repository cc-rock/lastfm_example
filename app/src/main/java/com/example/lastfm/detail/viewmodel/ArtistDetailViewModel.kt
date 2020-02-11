package com.example.lastfm.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lastfm.detail.domain.ArtistDetailRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ArtistDetailViewModel(
    private val repository: ArtistDetailRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel(), CoroutineScope {

    // coroutine scope implementation
    private val mainJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = ioDispatcher + mainJob

    private var runningJob: Job? = null

    private val mutableViewData = MutableLiveData<ArtistDetailViewData>()
    val viewData: LiveData<ArtistDetailViewData> = mutableViewData

    fun loadArtistDetail(artistId: String) {
        runningJob?.cancel()

        runningJob = launch {
            mutableViewData.postValue(ArtistDetailViewData.Loading())
            val detail = try {
                repository.getArtistDetail(artistId)
            } catch (t: Throwable) {
                mutableViewData.postValue(
                    ArtistDetailViewData.Error(t)
                )
                return@launch
            }
            mutableViewData.postValue(
                ArtistDetailViewData.Success(detail)
            )
        }
    }

    override fun onCleared() {
        runningJob?.cancel()
    }

}