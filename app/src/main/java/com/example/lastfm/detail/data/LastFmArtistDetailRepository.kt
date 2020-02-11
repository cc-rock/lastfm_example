package com.example.lastfm.detail.data

import com.example.lastfm.common.data.lastfm.LastFmApi
import com.example.lastfm.detail.domain.ArtistDetail
import com.example.lastfm.detail.domain.ArtistDetailRepository
import javax.inject.Inject

class LastFmArtistDetailRepository @Inject constructor(
    private val lastFmApi: LastFmApi,
    private val converter: LastFmArtistDetailConverter
): ArtistDetailRepository {

    override suspend fun getArtistDetail(id: String): ArtistDetail {
        return converter.convert(lastFmApi.getArtistDetail(id).artist)
    }

}