package com.example.lastfm.detail.domain

interface ArtistDetailRepository {

    suspend fun getArtistDetail(id: String): ArtistDetail

}