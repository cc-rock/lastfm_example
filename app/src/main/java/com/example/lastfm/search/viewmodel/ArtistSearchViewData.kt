package com.example.lastfm.search.viewmodel

import com.example.lastfm.common.data.entities.ArtistSearchItem

data class ArtistSearchViewData(
    val searchQuery: String,
    val results: List<ArtistSearchItem>,
    val totalCount: Int,
    val loading: Boolean,
    val error: Boolean
)
