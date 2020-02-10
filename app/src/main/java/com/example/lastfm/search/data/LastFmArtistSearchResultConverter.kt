package com.example.lastfm.search.data

import com.example.lastfm.common.data.entities.ArtistSearchItem
import com.example.lastfm.common.data.entities.ArtistSearchResult
import com.example.lastfm.common.data.lastfm.LastFmArtistImage
import com.example.lastfm.common.data.lastfm.LastFmArtistMatch
import com.example.lastfm.common.data.lastfm.LastFmArtistSearchResults
import javax.inject.Inject

/**
 * Converts search results data from last.fm api format to the app's domain format
 * @param toSkip number of results to skip at the beginning of the lastfm set
 * @param maxResults maximum number of results to convert
 */
class LastFmArtistSearchResultConverter @Inject constructor() {

    fun convert(lastFmArtistSearchResults: LastFmArtistSearchResults, toSkip: Int, maxResults: Int): ArtistSearchResult {
        return ArtistSearchResult(
            lastFmArtistSearchResults.totalResults,
            lastFmArtistSearchResults.startIndex,
            lastFmArtistSearchResults.artistmatches.artist
                .drop(toSkip)
                .take(maxResults)
                .map(::convertItem)
        )
    }

    private fun convertItem(lastFmArtistItem: LastFmArtistMatch): ArtistSearchItem {
        return ArtistSearchItem(
            lastFmArtistItem.mbid,
            lastFmArtistItem.name,
            findSmallImageUrl(lastFmArtistItem.image)
        )
    }

    private fun findSmallImageUrl(images: List<LastFmArtistImage>): String? {
        return (images.firstOrNull { it.size == "small" } ?: images.firstOrNull())?.url
    }

}