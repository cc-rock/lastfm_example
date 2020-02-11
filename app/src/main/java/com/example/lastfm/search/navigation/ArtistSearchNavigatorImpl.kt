package com.example.lastfm.search.navigation

import androidx.navigation.NavController
import com.example.lastfm.R
import com.example.lastfm.detail.ui.ArtistDetailFragment
import com.example.lastfm.search.domain.ArtistSearchNavigator
import javax.inject.Inject

class ArtistSearchNavigatorImpl @Inject constructor(
    private val navController: dagger.Lazy<NavController>
): ArtistSearchNavigator {

    override fun goToArtistDetail(artistId: String) {
        navController.get().navigate(
            R.id.to_artist_detail,
            ArtistDetailFragment.getArgumentsBundle(artistId)
        )
    }

}