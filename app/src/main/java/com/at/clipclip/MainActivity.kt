package com.at.clipclip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.at.clipclip.ui.screen.MainScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val addrState: MutableStateFlow<String> = MutableStateFlow("")
    private val messageState: MutableStateFlow<String> = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen(
                addr = addrState.collectAsState().value,
                message = messageState.collectAsState().value,
                onAddrChange = { addrState.value = it },
                onClickAddUploadShortcut = { handleAddUploadShortcut() },
                onClickAddDownloadShortcut = { handleAddDownloadShortcut() },
                onClickUpload = { UploadActivity.startActivity(this) },
                onClickDownload = { DownloadActivity.startActivity(this) },
            )
        }
        handleLoadAddr()
        handleObserveAddr()
    }

    private fun handleAddUploadShortcut() = runCatching {
        addUploadShortcut().getOrThrow()
    }.onFailure { e ->
        messageState.value = e.message ?: e.stackTraceToString()
    }

    private fun handleAddDownloadShortcut() = runCatching {
        addDownloadShortcut().getOrThrow()
    }.onFailure { e ->
        messageState.value = e.message ?: e.stackTraceToString()
    }

    private fun handleLoadAddr() = lifecycleScope.launch {
        getAddr().getOrNull()?.let { addrState.value = it }
    }

    private fun handleObserveAddr() = lifecycleScope.launch {
        addrState.drop(1).collect { setAddr(it).getOrNull() }
    }
}
