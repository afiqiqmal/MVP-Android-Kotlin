package com.kotlin.mvp.mvpandroid.notification

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kotlin.mvp.mvpandroid.BuildConfig
import com.kotlin.mvp.mvpandroid.R
import com.kotlin.mvp.mvpandroid.ui.MainActivity
import com.kotlin.mvp.mvpandroid.utils.ErrorUtils

/**
 * @author : hafiq on 08/09/2016.
 */
class FirebaseNotificationService : FirebaseMessagingService() {

    lateinit var errorUtils: ErrorUtils

    override fun onCreate() {
        super.onCreate()
        errorUtils = ErrorUtils(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        sendNotification(remoteMessage)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        try {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "PUSH TITLE = " + remoteMessage.data["title"])
                Log.i(TAG, "PUSH BODY = " + remoteMessage.data["body"])
            }

            var body: String? = null
            var intent: Intent? = null
            try {
                body = remoteMessage.data["body"]
                intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                intent.putExtra("message", body)
            } catch (e: Exception) {
                errorUtils.recordError(e)
            }

            if (intent != null) {
                val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

                val NOTIFICATION_CHANNEL_ID = "my_channel_id_01"

                val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(remoteMessage.data["title"])
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(0, notificationBuilder.build())
            } else {
                errorUtils.recordError(Throwable("Intent is null for notification"))
            }
        } catch (e: Exception) {
            errorUtils.recordError(e)
        }

    }

    companion object {
        private const val TAG = "Push"
    }
}