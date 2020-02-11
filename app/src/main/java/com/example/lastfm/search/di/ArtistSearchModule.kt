package com.example.lastfm.search.di

import com.example.lastfm.search.data.LastFmArtistSearchRepository
import com.example.lastfm.search.domain.ArtistSearchNavigator
import com.example.lastfm.search.domain.ArtistSearchRepository
import com.example.lastfm.search.navigation.ArtistSearchNavigatorImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ArtistSearchModule {

    @Binds
    abstract fun bindArtistSearchRepository(lastFmRepository: LastFmArtistSearchRepository): ArtistSearchRepository

    @Binds
    abstract fun bindNavigator(navigatorImpl: ArtistSearchNavigatorImpl): ArtistSearchNavigator

}