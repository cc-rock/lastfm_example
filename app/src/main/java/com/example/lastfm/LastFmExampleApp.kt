package com.example.lastfm

import com.example.lastfm.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class LastFmExampleApp: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}