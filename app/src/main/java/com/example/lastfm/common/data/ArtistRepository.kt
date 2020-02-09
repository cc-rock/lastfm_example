package com.example.lastfm.common.data

import com.example.lastfm.common.data.entities.ArtistDetail
import com.example.lastfm.common.data.entities.ArtistSearchResult

interface ArtistRepository {

    suspend fun searchArtistsByName(query: String, initialPosition: Int = 0, maxResults: Int = 30): ArtistSearchResult

    suspend fun getArtistDetail(id: String): ArtistDetail

}