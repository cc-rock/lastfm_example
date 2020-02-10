package com.example.lastfm.search.di

import com.example.lastfm.search.data.LastFmArtistSearchRepository
import com.example.lastfm.search.domain.ArtistSearchRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ArtistSearchModule {

    @Binds
    abstract fun bindArtistSearchRepository(lastFmRepository: LastFmArtistSearchRepository): ArtistSearchRepository

}