package com.at.clipclip.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.at.clipclip.ui.theme.ClipclipTheme

@Composable
fun MainScreen(
    addr: String,
    onAddrChange: (String) -> Unit,
    onClickUpload: () -> Unit,
    onClickDownload: () -> Unit,
) {
    ClipclipTheme {
        Scaffold { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                MainContent(
                    addr,
                    onAddrChange,
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
    onAddrChange: (String) -> Unit,
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = onClickUpload,
                modifier = Modifier.weight(0.5F),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_fluent_arrow_upload_32_regular),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "上传",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Button(
                onClick = onClickDownload,
                modifier = Modifier.weight(0.5F),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_fluent_arrow_download_32_regular),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "下载",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    ClipclipTheme {
        MainContent(
            addr = "http://192.168.6.1:8090",
            onAddrChange = {},
            onClickUpload = {},
            onClickDownload = {},
        )
    }
}
