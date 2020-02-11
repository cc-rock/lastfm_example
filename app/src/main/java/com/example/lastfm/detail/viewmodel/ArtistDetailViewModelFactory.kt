package com.example.lastfm.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lastfm.detail.domain.ArtistDetailRepository
import com.example.lastfm.di.AppModule
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@Suppress("UNCHECKED_CAST")
class ArtistDetailViewModelFactory @Inject constructor(
    private val repository: ArtistDetailRepository,
    @param:Named(AppModule.IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return (ArtistDetailViewModel(
            repository,
            ioDispatcher
        ) as? T)
            ?: throw IllegalArgumentException("This ViewModel factory can only provide ArtistSearchViewModel(s)")
    }

}