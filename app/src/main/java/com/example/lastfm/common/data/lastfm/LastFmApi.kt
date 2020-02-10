package com.example.lastfm.common.data.lastfm

import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
const val API_KEY = "e69f8cb9a295a2efc166e49c762ed6ea"

interface LastFmApi {

    @GET("?method=artist.search&format=json&api_key=$API_KEY")
    suspend fun searchArtistsByName(
        @Query("artist") query: String,
        @Query("page") pageNumber: Int,
        @Query("limit") pageSize: Int
    ): LastFmArtistSearchResponse

    @GET("?method=artist.getInfo&format=json&api_key=$API_KEY")
    suspend fun getArtistDetail(
        @Query("mbid") artistId: String
    ): LastFmArtistDetailResponse

}