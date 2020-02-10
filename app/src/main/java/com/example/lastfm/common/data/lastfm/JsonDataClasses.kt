package com.example.lastfm.common.data.lastfm

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LastFmArtistSearchResponse(
    val results: LastFmArtistSearchResults
)

@JsonClass(generateAdapter = true)
data class LastFmArtistSearchResults(
    @Json(name = "opensearch:totalResults") val totalResults: Int,
    @Json(name = "opensearch:startIndex") val startIndex: Int,
    @Json(name = "opensearch:itemsPerPage") val itemsPerPage: Int,
    val artistmatches: LastFmArtistMatches
)

@JsonClass(generateAdapter = true)
data class LastFmArtistMatches(
    val artist: List<LastFmArtistMatch>
)

@JsonClass(generateAdapter = true)
data class LastFmArtistMatch(
    val name: String,
    val mbid: String,
    val image: List<LastFmArtistImage>
)

@JsonClass(generateAdapter = true)
data class LastFmArtistImage(
    val size: String,
    @Json(name = "#text") val url: String
)

@JsonClass(generateAdapter = true)
data class LastFmArtistDetailResponse(
    val artist: LastFmArtistDetail
)

@JsonClass(generateAdapter = true)
data class LastFmArtistDetail(
    val name: String,
    val mbid: String,
    val url: String,
    val image: List<LastFmArtistImage>,
    val content: String
)