package com.sawrose.marvelapp.core.network.di

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger
import com.sawrose.marvelapp.core.network.BuildConfig
import com.sawrose.marvelapp.core.network.retrofit.RetrofitMarvelNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    /**
     * Create a provider method binding for [HttpLoggingInterceptor].
     *
     * @return Instance of http interceptor.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    /**
     * Create a provider method binding for [OkHttpClient].
     *
     * @return Instance of http client.
     * @see Provides
     */
    @Singleton
    @Provides
    fun provideHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(interceptor)
        }
        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitMarvelNetwork(client: OkHttpClient): RetrofitMarvelNetwork {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MARVEL_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitMarvelNetwork::class.java)
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