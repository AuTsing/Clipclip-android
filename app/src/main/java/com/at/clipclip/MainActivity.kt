package com.at.clipclip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.at.clipclip.ui.screen.MainScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen(
                addr = "http://",
                onAddrChange = {},
                onClickUpload = {},
                onClickDownload = {},
            )
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}
