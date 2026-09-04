package com.vuq0z.client

import android.app.Service
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

class HudOverlayService : Service() {
    private var windowManager: WindowManager? = null
    private var hudView: TextView? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        hudView = TextView(this).apply {
            text = "VUQ0Z CLIENT\nFPS: 60 | Ping: 18ms"
            setTextColor(Color.parseColor("#00E676"))
            setBackgroundColor(Color.argb(160, 10, 10, 15))
            setPadding(20, 15, 20, 15)
            textSize = 12f
        }
        val layoutType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutType,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 40
            y = 40
        }
        windowManager?.addView(hudView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (hudView != null) windowManager?.removeView(hudView)
    }
}
