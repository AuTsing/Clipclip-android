package com.at.clipclip.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.at.clipclip.R
import com.at.clipclip.ui.component.PrimaryButton
import com.at.clipclip.ui.theme.ClipclipTheme

@Composable
fun MainScreen(
    addr: String,
    message: String,
    onAddrChange: (String) -> Unit,
    onClickAddUploadShortcut: () -> Unit,
    onClickAddDownloadShortcut: () -> Unit,
    onClickUpload: () -> Unit,
    onClickDownload: () -> Unit,
) {
    ClipclipTheme {
        Scaffold { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                MainContent(
                    addr,
                    message,
                    onAddrChange,
                    onClickAddUploadShortcut,
                    onClickAddDownloadShortcut,
                    onClickUpload,
                    onClickDownload,
                )
            }
        }
    }
}

@Composable
private fun MainContent(
    addr: String,
    message: String,
    onAddrChange: (String) -> Unit,
    onClickAddUploadShortcut: () -> Unit,
    onClickAddDownloadShortcut: () -> Unit,
    onClickUpload: () -> Unit,
    onClickDownload: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Image(
            painter = painterResource(R.mipmap.ic_launcher_foreground),
            contentDescription = null,
        )
        OutlinedTextField(
            value = addr,
            onValueChange = onAddrChange,
            label = { Text("服务地址") },
            modifier = Modifier.fillMaxWidth(),
        )
        PrimaryButton(
            text = "添加上传桌面快捷方式",
            iconId = R.drawable.ic_fluent_add_32_regular,
            onClick = onClickAddUploadShortcut,
        )
        PrimaryButton(
            text = "添加下载桌面快捷方式",
            iconId = R.drawable.ic_fluent_add_32_regular,
            onClick = onClickAddDownloadShortcut,
        )
        PrimaryButton(
            text = "上传",
            iconId = R.drawable.ic_fluent_arrow_upload_32_regular,
            onClick = onClickUpload,
        )
        PrimaryButton(
            text = "下载",
            iconId = R.drawable.ic_fluent_arrow_download_32_regular,
            onClick = onClickDownload,
        )
        Text(message)
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(
        addr = "http://192.168.6.1:8090",
        message = "Here is some message",
        onAddrChange = {},
        onClickAddUploadShortcut = {},
        onClickAddDownloadShortcut = {},
        onClickUpload = {},
        onClickDownload = {},
    )
}
