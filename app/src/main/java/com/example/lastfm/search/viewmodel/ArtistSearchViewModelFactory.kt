package com.example.lastfm.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lastfm.di.AppModule.Companion.IO_DISPATCHER
import com.example.lastfm.search.domain.ArtistSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Named

@Suppress("UNCHECKED_CAST")
class ArtistSearchViewModelFactory @Inject constructor(
    private val artistSearchRepository: ArtistSearchRepository,
    @param:Named(IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return (ArtistSearchViewModel(
            artistSearchRepository,
            ioDispatcher
        ) as? T)
            ?: throw IllegalArgumentException("This ViewModel factory can only provide ArtistSearchViewModel(s)")
    }

}