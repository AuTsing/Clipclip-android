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

class UploadActivity : ComponentActivity() {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, UploadActivity::class.java)
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
        handleUpload()
    }

    private fun handleUpload() = lifecycleScope.launch {
        runCatching {
            loadingState.value = true
            messageState.value = "请求中"

            val clip = "abc"
            val addr = getAddr().getOrThrow()
            val client = newHttpClient()
            val message = clip.toUploadMessage()
            client.upload(addr, message).getOrThrow()
        }.onSuccess {
            loadingState.value = false
            messageState.value = "已上传 1 条内容"
            delay(1000)
            finishAndRemoveTask()
        }.onFailure { e ->
            loadingState.value = false
            messageState.value = e.message ?: e.stackTraceToString()
        }
    }
}
