package com.example.lastfm.common.data.lastfm

import com.squareup.moshi.Json

data class LastFmArtistSearchResponse(
    val results: LastFmArtistSearchResults
)

data class LastFmArtistSearchResults(
    @Json(name = "opensearch:totalResults") val totalResults: Int,
    @Json(name = "opensearch:startIndex") val startIndex: Int,
    @Json(name = "opensearch:itemsPerPage") val itemsPerPage: Int,
    val artistMatches: LastFmArtistMatches
)

data class LastFmArtistMatches(
    val artist: List<LastFmArtistMatch>
)

data class LastFmArtistMatch(
    val name: String,
    val mbid: String,
    val image: List<LastFmArtistImage>
)

data class LastFmArtistImage(
    val size: String,
    @Json(name = "#text") val url: String
)

data class LastFmArtistDetailResponse(
    val artist: LastFmArtistDetail
)

data class LastFmArtistDetail(
    val name: String,
    val mbid: String,
    val url: String,
    val image: List<LastFmArtistImage>,
    val content: String
)