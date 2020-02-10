package com.example.lastfm.search.domain

import com.example.lastfm.common.data.entities.ArtistSearchResult

interface ArtistSearchRepository {

    suspend fun searchArtistsByName(query: String, offset: Int = 0, maxResults: Int = 30): ArtistSearchResult

}