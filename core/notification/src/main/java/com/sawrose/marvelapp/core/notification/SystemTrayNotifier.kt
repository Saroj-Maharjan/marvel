package com.sawrose.marvelapp.core.notification

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat.Builder
import androidx.core.app.NotificationCompat.InboxStyle
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.app.NotificationManagerCompat
import com.sawrose.marvelapp.core.model.Character
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


private const val MAX_NUM_NOTIFICATIONS = 5
private const val TARGET_ACTIVITY_NAME = "com.saworse.marvelapp.MainActivity"
private const val CHARACTER_NOTIFICATION_REQUEST_CODE = 0
private const val CHARACTER_NOTIFICATION_SUMMARY_ID = 1
private const val CHARACTER_NOTIFICATION_CHANNEL_ID = ""
private const val CHARACTER_NOTIFICATION_GROUP = "CHARACTER_NOTIFICATIONS"

/**
 * Implementation of [Notifier] that uses the system tray to display notifications.
 * */
@Singleton
class SystemTrayNotifier @Inject constructor(
    @ApplicationContext private val context: Context
): Notifier{
    override fun postCharacterNotification(characters: List<Character>) = with(context){
        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS,
        ) != PackageManager.PERMISSION_GRANTED){
            return
        }

        val truncatedCharacters = characters
            .take(MAX_NUM_NOTIFICATIONS)
        val notification = truncatedCharacters
            .map { character ->
                createCharacterNotification {
                    setSmallIcon(
                        com.sawrose.marvelapp.core.common.R.drawable.ic_nia_notification
                    )
                        .setContentTitle(character.name)
                        .setContentText(character.description)
                        .setGroup(CHARACTER_NOTIFICATION_GROUP)
                        .setAutoCancel(true)
                }
            }

        val summaryNotification = createCharacterNotification {
            val title = getString(
                R.string.character_notification_group_summary,
                characters.size
            )

            setContentTitle(title)
                .setContentText(title)
                .setSmallIcon(
                    com.sawrose.marvelapp.core.common.R.drawable.ic_nia_notification
                )
                .setStyle(characterNotificationStyle(truncatedCharacters, title))
                .setGroup(CHARACTER_NOTIFICATION_GROUP)
                .setGroupSummary(true)
                .setAutoCancel(true)
                .build()
        }

        // Send the notifications
        val notificationManager = NotificationManagerCompat.from(this)

        notification.forEachIndexed { index, notification ->
            notificationManager.notify(
                truncatedCharacters[index].id.hashCode(),
                notification,
            )
        }
        notificationManager.notify(CHARACTER_NOTIFICATION_SUMMARY_ID, summaryNotification)
    }

    /**
     * Creates an inbox style summary notification for news updates
     */
    private fun characterNotificationStyle(
        characters: List<Character>,
        title: String,
    ): InboxStyle = characters
        .fold(InboxStyle()) { inboxStyle, character ->
            inboxStyle.addLine(character.name)
        }
        .setBigContentTitle(title)
        .setSummaryText(title)

}

private fun Context.createCharacterNotification(
    block: Builder.() -> Unit,
): Notification {
    ensureNotificationChannelExists()
    return Builder(
        this,
        CHARACTER_NOTIFICATION_CHANNEL_ID
    )
        .setPriority(PRIORITY_DEFAULT)
        .apply(block)
        .build()
}

/**
 * Ensures the a notification channel is is present if applicable
 */
private fun Context.ensureNotificationChannelExists() {
    if (VERSION.SDK_INT < Build.VERSION_CODES.O) return

    val channel = NotificationChannel(
        CHARACTER_NOTIFICATION_CHANNEL_ID,
        getString(R.string.character_notification_channel_name),
        NotificationManager.IMPORTANCE_DEFAULT,
    ).apply {
        description = getString(R.string.character_notification_channel_description)
    }
    // Register the channel with the system
    NotificationManagerCompat.from(this).createNotificationChannel(channel)
}

//private fun Context.characterPendingIntent(
//    character: Character,
//): PendingIntent? = PendingIntent.getActivity(
//    this,
//    CHARACTER_NOTIFICATION_REQUEST_CODE,
//    Intent().apply {
//        action = Intent.ACTION_VIEW
//        data = character.newsDeepLinkUri()
//        component = ComponentName(
//            packageName,
//            TARGET_ACTIVITY_NAME,
//        )
//    },
//    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
//)