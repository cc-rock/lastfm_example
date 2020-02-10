package com.example.lastfm.common.data.entities

data class ArtistSearchResult(
    val totalCount: Int,
    val startPosition: Int,
    val artists: List<ArtistSearchItem>
)