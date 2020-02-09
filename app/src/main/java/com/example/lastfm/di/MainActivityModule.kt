package com.example.lastfm.di

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.lastfm.MainActivity
import com.example.lastfm.R
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

  /*  @FragmentScope
    @ContributesAndroidInjector(modules = [CurrencyConversionModule::class])
    abstract fun createCurrencyConversionFragmentInjector(): CurrencyConversionFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CurrencyCompareModule::class])
    abstract fun createCurrencyCompareFragmentInjector(): CurrencyCompareFragment */

}