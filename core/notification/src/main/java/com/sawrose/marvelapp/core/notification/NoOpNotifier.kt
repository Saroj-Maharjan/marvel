package com.sawrose.marvelapp.core.notification

import com.sawrose.marvelapp.core.model.Character

/**
 * Implementation of [Notifier] that does nothing.
 */
class NoOpNotifier : Notifier {
    override fun postCharacterNotification(characters: List<Character>) = Unit
}