package com.smilias.learnit.video_screen.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MovieCreation
import androidx.core.app.NotificationCompat
import com.smilias.learnit.R
import timber.log.Timber

const val INTENT_COMMAND = "Command"
const val INTENT_COMMAND_EXIT = "Exit"

private const val NOTIFICATION_CHANNEL_GENERAL = "Checking"
private const val CODE_FOREGROUND_SERVICE = 1

class PhotoCaptureService : Service() {


    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val command = intent?.getStringExtra(INTENT_COMMAND)
        if (command == INTENT_COMMAND_EXIT) {
            stopService()
            return START_NOT_STICKY
        }
        showNotification()
        return START_STICKY
    }

    private fun stopService() {
        stopForeground(true)
        stopSelf()
    }

    private fun showNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                with(
                    NotificationChannel(
                        NOTIFICATION_CHANNEL_GENERAL,
                        "Video on background",
                        NotificationManager.IMPORTANCE_DEFAULT

                    )
                ) {
                    enableLights(false)
                    setShowBadge(false)
                    enableVibration(false)
                    setSound(null, null)
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    manager.createNotificationChannel(this)
                }
            } catch (e: Exception) {
                Timber.d("showNotification: ${e.localizedMessage}")
            }
        }
        with(
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_GENERAL)
        ) {
            setTicker(null)
            setContentTitle("LearnIt")
            setContentText("Video on background")
            setAutoCancel(false)
            setOngoing(true)
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            priority = Notification.PRIORITY_MAX
            startForeground(CODE_FOREGROUND_SERVICE, build())
        }
    }
}