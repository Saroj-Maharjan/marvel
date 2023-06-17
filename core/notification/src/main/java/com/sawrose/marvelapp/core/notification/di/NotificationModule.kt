package com.sawrose.marvelapp.core.notification.di

import com.sawrose.marvelapp.core.notification.Notifier
import com.sawrose.marvelapp.core.notification.SystemTrayNotifier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    abstract fun bindNotifier(
        notifier: SystemTrayNotifier
    ): Notifier
}