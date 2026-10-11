package com.example.autoclicker

import android.content.Context
import rikka.shizuku.Shizuku

class ShizukuManager(private val context: Context) {

    fun isAvailable(): Boolean = Shizuku.pingBinder()

    fun requestPermission(listener: (Boolean) -> Unit) {
        val hasPermission = isAvailable()
        listener(hasPermission)
    }
}
