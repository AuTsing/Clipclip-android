package com.at.clipclip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.at.clipclip.ui.screen.ActionScreen
import kotlinx.coroutines.flow.MutableStateFlow

class DownloadActivity : ComponentActivity() {

    private val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val messageState: MutableStateFlow<String> = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActionScreen(
                loading = loadingState.collectAsState().value,
                message = messageState.collectAsState().value,
                onClickClose = {},
            )
        }
    }
}
