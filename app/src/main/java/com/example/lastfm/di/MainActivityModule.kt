package com.example.lastfm.di

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.lastfm.MainActivity
import com.example.lastfm.R
import com.example.lastfm.detail.di.ArtistDetailModule
import com.example.lastfm.detail.ui.ArtistDetailFragment
import com.example.lastfm.search.di.ArtistSearchModule
import com.example.lastfm.search.ui.ArtistSearchFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @Module
    companion object {

        @Provides
        @ActivityScope
        @JvmStatic
        fun provideNavController(activity: MainActivity): NavController = Navigation.findNavController(
            activity, R.id.nav_host_fragment
        )

    }

    // fragment injectors

    @FragmentScope
    @ContributesAndroidInjector(modules = [ArtistSearchModule::class])
    abstract fun createArtistSearchFragmentInjector(): ArtistSearchFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ArtistDetailModule::class])
    abstract fun createArtistDetailInjector(): ArtistDetailFragment

}