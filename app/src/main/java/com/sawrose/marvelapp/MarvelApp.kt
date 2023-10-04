package com.sawrose.marvelapp

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.sawrose.marvelapp.sync.initializer.Sync
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

/**
 * [Application] class for the app.
 * */

@HiltAndroidApp
class MarvelApp: Application(), ImageLoaderFactory {
    @Inject
    lateinit var imageLoader: Provider<ImageLoader>

    override fun onCreate() {
        super.onCreate()
        //initialize sync; the system is responsible for calling this method
        Sync.initialize(context = this)
    }

    override fun newImageLoader() = imageLoader.get()
}