package com.at.clipclip

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.at.clipclip.ui.screen.ActionScreen
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UploadActivity : ComponentActivity() {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, UploadActivity::class.java)
            intent.flags += Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

        fun createIntent(context: Context): Intent {
            val intent = Intent(context, UploadActivity::class.java)
            intent.action = Intent.ACTION_MAIN
            intent.flags += Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }

        fun createPendingIntent(context: Context): PendingIntent {
            val intent = createIntent(context)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE,
            )
            return pendingIntent
        }
    }

    private val focused: CompletableDeferred<Unit> = CompletableDeferred()
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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            focused.complete(Unit)
        }
    }

    private fun handleUpload() = lifecycleScope.launch {
        runCatching {
            loadingState.value = true
            messageState.value = "请求中"

            focused.await()
            val clip = getClip().getOrThrow()
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
