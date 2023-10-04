package com.sawrose.marvelapp.core.database.di

import android.content.Context
import androidx.room.Room
import com.sawrose.marvelapp.core.database.MarvelDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule{

    @Provides
    fun provideCharactersDao(
        database: MarvelDatabase
    ) = database.charactersDao()

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) :MarvelDatabase =
        Room.databaseBuilder(
            context,
            MarvelDatabase::class.java,
            "marvel.db"
        ).build()
}