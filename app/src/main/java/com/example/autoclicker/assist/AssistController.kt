package com.example.autoclicker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FloatingMenuService : Service() {

    private var windowManager: WindowManager? = null
    private var floatingView: View? = null
    private var overlayJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1001, buildNotification())
        showFloatingMenu()
    }

    override fun onDestroy() {
        overlayJob?.cancel()
        removeFloatingMenu()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun showFloatingMenu() {
        val inflater = getSystemService(LayoutInflater::class.java)
        val root = inflater.inflate(R.layout.floating_menu, null)

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        params.gravity = Gravity.TOP or Gravity.START
        params.x = 40
        params.y = 120

        windowManager = getSystemService(WindowManager::class.java)
        windowManager?.addView(root, params)
        floatingView = root

        val btnStart = root.findViewById<Button>(R.id.btnStartAssist)
        val btnRecord = root.findViewById<Button>(R.id.btnRecord)
        val btnOpenLibrary = root.findViewById<Button>(R.id.btnLibrary)
        val tvState = root.findViewById<TextView>(R.id.tvState)

        btnStart.setOnClickListener {
            tvState.text = "IA assist actif"
            CoroutineScope(Dispatchers.Main).launch {
                val controller = AssistController(applicationContext)
                controller.startBurstClick(40, 2500)
            }
        }

        btnRecord.setOnClickListener {
            val manager = RecordingManager(applicationContext)
            manager.startRecording()
            tvState.text = "Enregistrement en cours…"
        }

        btnOpenLibrary.setOnClickListener {
            tvState.text = "Bibliothèque des enregistrements"
        }
    }

    private fun removeFloatingMenu() {
        val view = floatingView ?: return
        windowManager?.removeView(view)
        floatingView = null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "auto_clicker_overlay",
                "AutoClicker",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification =
        NotificationCompat.Builder(this, "auto_clicker_overlay")
            .setContentTitle("AutoClicker")
            .setContentText("Le menu flottant est actif")
            .setSmallIcon(R.drawable.ic_stat_name)
            .build()
}
