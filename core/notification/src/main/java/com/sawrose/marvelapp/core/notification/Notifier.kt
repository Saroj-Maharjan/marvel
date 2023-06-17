package com.sawrose.marvelapp.core.notification

import com.sawrose.marvelapp.core.model.Character

/**
 * Interface for notifying the user of events.
 */
interface Notifier {
    fun postCharacterNotification(characters: List<Character>)
}
