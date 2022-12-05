package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, NotificationService::class.java)

        val productName = intent.getStringExtra("productName").toString()
        val productId = intent.getLongExtra("productId", -1)
        val productIfBought = intent.getBooleanExtra("productIfBought", false)

        serviceIntent.putExtra("productId", productId)
        serviceIntent.putExtra("productName", productName)
        serviceIntent.putExtra("productIfBought", productIfBought)

        context.startService(serviceIntent)
    }
}