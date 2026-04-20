package com.at.clipclip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.at.clipclip.ui.screen.MainScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val addrState: MutableStateFlow<String> = MutableStateFlow("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen(
                addr = addrState.collectAsState().value,
                onAddrChange = { addrState.value = it },
                onClickUpload = {},
                onClickDownload = {},
            )
        }
        handleLoadAddr()
        handleObserveAddr()
    }

    private fun handleLoadAddr() = lifecycleScope.launch {
        getAddr().getOrNull()?.let { addrState.value = it }
    }

    private fun handleObserveAddr() = lifecycleScope.launch {
        addrState.collect { setAddr(it).getOrNull() }
    }
}
