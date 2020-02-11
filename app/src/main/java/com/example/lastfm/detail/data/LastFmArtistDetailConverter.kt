package com.example.lastfm.detail.data

import com.example.lastfm.common.data.lastfm.LastFmArtistDetail
import com.example.lastfm.common.data.lastfm.LastFmArtistImage
import com.example.lastfm.detail.domain.ArtistDetail
import javax.inject.Inject

class LastFmArtistDetailConverter @Inject constructor() {

    fun convert(lastFmArtistDetail: LastFmArtistDetail): ArtistDetail {
        return ArtistDetail(
            lastFmArtistDetail.mbid,
            lastFmArtistDetail.name,
            findLargeImageUrl(lastFmArtistDetail.image),
            lastFmArtistDetail.bio?.content,
            lastFmArtistDetail.url
        )
    }

    private fun findLargeImageUrl(images: List<LastFmArtistImage>): String? {
        return (images.firstOrNull { it.size == "large" } ?: images.firstOrNull())?.url
    }

}