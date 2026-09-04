package com.vuq0z.client

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chkPvpHud = findViewById<CheckBox>(R.id.chkPvpHud)
        val btnLaunch = findViewById<Button>(R.id.btnLaunch)

        btnLaunch.setOnClickListener {
            if (chkPvpHud.isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                    startActivityForResult(intent, 101)
                    return@setOnClickListener
                } else {
                    startService(Intent(this, HudOverlayService::class.java))
                }
            }
            val minecraft = packageManager.getLaunchIntentForPackage("com.mojang.minecraftpe")
            if (minecraft != null) {
                startActivity(minecraft)
            } else {
                Toast.makeText(this, "Minecraft chưa được cài đặt!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
