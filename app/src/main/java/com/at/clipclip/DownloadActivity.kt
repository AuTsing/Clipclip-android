package com.at.clipclip

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.at.clipclip.ui.screen.ActionScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DownloadActivity : ComponentActivity() {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, DownloadActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val messageState: MutableStateFlow<String> = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ActionScreen(
                loading = loadingState.collectAsState().value,
                message = messageState.collectAsState().value,
                onClickClose = { finishAndRemoveTask() },
            )
        }
        handleDownload()
    }

    private fun handleDownload() = lifecycleScope.launch {
        loadingState.value = true
        messageState.value = "请求中"
        delay(3000)
        loadingState.value = false
        messageState.value = "已下载 1 条内容"
        delay(1000)
        finishAndRemoveTask()
    }
}
