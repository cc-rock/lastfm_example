package com.example.lastfm.detail.viewmodel

import com.example.lastfm.detail.domain.ArtistDetail

sealed class ArtistDetailViewData {
    class Loading : ArtistDetailViewData()
    class Error(val t: Throwable): ArtistDetailViewData()
    class Success(val artist: ArtistDetail):ArtistDetailViewData()
}