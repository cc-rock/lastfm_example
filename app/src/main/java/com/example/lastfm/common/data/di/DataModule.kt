package com.example.lastfm.common.data.di

import com.example.lastfm.common.data.lastfm.BASE_URL
import com.example.lastfm.common.data.lastfm.LastFmApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class DataModule {

    @Provides
    fun provideLastFmApi(): LastFmApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(LastFmApi::class.java)
    }

}