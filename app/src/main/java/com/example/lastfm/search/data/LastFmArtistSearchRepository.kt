package com.example.lastfm.search.data

import com.example.lastfm.common.data.entities.ArtistSearchResult
import com.example.lastfm.common.data.lastfm.LastFmApi
import com.example.lastfm.search.domain.ArtistSearchRepository
import javax.inject.Inject

class LastFmArtistSearchRepository @Inject constructor(
    private val lastFmApi: LastFmApi,
    private val converter: LastFmArtistSearchResultConverter
): ArtistSearchRepository {

    override suspend fun searchArtistsByName(
        query: String,
        offset: Int,
        maxResults: Int
    ): ArtistSearchResult {
        val (pageNumber, pageSize, toSkip) = computePageParameters(offset, maxResults)
        val result = lastFmApi.searchArtistsByName(query, pageNumber + 1, pageSize)
        return converter.convert(result.results, toSkip, maxResults)
    }

    /**
     * Computes the optimal page number and page size to retrieve the given range in one single network call
     */
    private fun computePageParameters(offset: Int, size: Int): Triple<Int, Int, Int> {
        var pageNum: Int = offset / size
        var pageSize: Int = size
        var toSkip: Int = offset - (pageNum * pageSize)
        while (((pageNum + 1) * pageSize) < (offset + size)) {
            pageSize++
            pageNum = offset / pageSize
            toSkip = offset - (pageNum * pageSize)
        }
        return Triple(pageNum, pageSize, toSkip)
    }

}