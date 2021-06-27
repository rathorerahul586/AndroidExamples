package com.rathoreapps.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.rathoreapps.mpchartexample.R
import kotlinx.android.synthetic.main.activity_notification.*

class Notification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        grid_layout[1].setOnClickListener { sendNotification(R.drawable.locodrive_icon) }
        grid_layout[2].setOnClickListener { sendNotification(R.drawable.exam) }
        grid_layout[3].setOnClickListener { sendNotification(R.drawable.telegram) }
        grid_layout[4].setOnClickListener { sendNotification(R.drawable.loconav) }
        grid_layout[5].setOnClickListener { sendNotification(R.drawable.warning) }
    }
    val CHANNEL_ID = "1st Channel"

    private fun sendNotification(@DrawableRes icon: Int) {
        val bitmap = BitmapFactory.decodeResource(this.resources,icon)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setLargeIcon(bitmap)
            .setSmallIcon(icon)
            .setContentTitle("Notification")
            .setContentText("Testing notification and launcher icon")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val colorString = color_et.text.toString()
            var color = Color.parseColor("#36a2eb")
            try {
                color = Color.parseColor(colorString)
            } catch (exp: IllegalArgumentException){
                Toast.makeText(this, "Incorrect color code", Toast.LENGTH_SHORT).show()
            }

            notificationBuilder.color = color
        }
        createNotificationChannel()

        NotificationManagerCompat.from(this).notify(1, notificationBuilder.build())
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}