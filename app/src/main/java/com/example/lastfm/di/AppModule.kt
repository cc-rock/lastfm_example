package com.example.lastfm.di

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.example.lastfm.MainActivity
import com.example.lastfm.common.data.di.DataModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module(includes = [DataModule::class])
abstract class AppModule {

    @Module
    companion object {

        const val MAIN_DISPATCHER = "MAIN_DISPATCHER"
        const val IO_DISPATCHER = "IO_DISPATCHER"

        @Provides
        @JvmStatic
        fun provideApplicationContext(application: Application): Context {
            return application.applicationContext
        }

        @Provides
        @JvmStatic
        fun provideResources(context: Context): Resources {
            return context.resources
        }

        @JvmStatic
        @Provides
        @Named(MAIN_DISPATCHER)
        fun provideMainThreadCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main

        @JvmStatic
        @Provides
        @Named(IO_DISPATCHER)
        fun provideIoCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main

    }

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun createMainActivityInjector(): MainActivity

}