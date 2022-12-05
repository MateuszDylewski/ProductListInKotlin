package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        createChannel(this)

        val productName = intent.getStringExtra("productName").toString()
        val productId = intent.getLongExtra("productId", -1)
        val productIfBought = intent.getBooleanExtra("productIfBought", false)

        val notificationIntent = Intent().also{
            it.component = ComponentName(
                "com.example.productlist",
                "com.example.productlist.AddProduct"
            )
            it.putExtra("productId", productId)
            it.putExtra("productName", productName)
            it.putExtra("productIfBought", productIfBought)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            productId.toInt(),
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, "productAddedNotification")
            .setContentTitle("You have new product!")
            .setContentText(productName)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(productId.toInt(), notification)
        println("DONE")
        return START_NOT_STICKY
    }

    fun createChannel(context: Context) {
        val notificationChannel = NotificationChannel(
            "productAddedNotification",
            "Product Added Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        NotificationManagerCompat.from(context)
            .createNotificationChannel(notificationChannel)
    }
}