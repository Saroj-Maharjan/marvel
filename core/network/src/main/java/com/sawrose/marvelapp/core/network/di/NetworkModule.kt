package com.sawrose.marvelapp.core.network.di

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger
import com.sawrose.marvelapp.core.network.BuildConfig
import com.sawrose.marvelapp.core.network.MarvelNetworkDataSource
import com.sawrose.marvelapp.core.network.retrofit.RetrofitMarvelNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MARVEL_API_BASE_URL)
            .client(client)
            .build()
    }


    /**
     * @see <a href="https://github.com/coil-kt/coil/blob/main/coil-singleton/src/main/java/coil/Coil.kt">Coil</a>
     */
    @Provides
    @Singleton
    fun imageLoader(
        okhttpCallFactory: Call.Factory,
        @ApplicationContext application: Context,
    ): ImageLoader = ImageLoader.Builder(application)
        .callFactory(okhttpCallFactory)
        .respectCacheHeaders(false)
        .apply {
            if(BuildConfig.DEBUG) {
                logger(DebugLogger())
            }
        }
        .build()
}