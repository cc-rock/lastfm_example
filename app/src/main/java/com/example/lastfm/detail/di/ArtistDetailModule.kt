package com.example.lastfm.detail.di

import com.example.lastfm.detail.data.LastFmArtistDetailRepository
import com.example.lastfm.detail.domain.ArtistDetailRepository
import dagger.Binds
import dagger.Module

@Module
abstract class ArtistDetailModule {

    @Binds
    abstract fun bindArtistDetailRepository(lastFmArtistDetailRepository: LastFmArtistDetailRepository): ArtistDetailRepository

}