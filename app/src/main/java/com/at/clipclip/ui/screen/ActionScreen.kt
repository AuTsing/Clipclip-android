package com.at.clipclip.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.at.clipclip.R
import com.at.clipclip.ui.component.PrimaryButton
import com.at.clipclip.ui.theme.ClipclipTheme

@Composable
fun ActionScreen(
    loading: Boolean,
    message: String,
    onClickClose: () -> Unit,
) {
    ClipclipTheme {
        Scaffold { paddingValues ->
            Surface(modifier = Modifier.padding(paddingValues)) {
                ActionContent(
                    loading,
                    message,
                    onClickClose,
                )
            }
        }
    }
}

@Composable
private fun ActionContent(
    loading: Boolean,
    message: String,
    onClickClose: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (loading) {
                CircularProgressIndicator()
            }
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
            )
            PrimaryButton(
                text = "关闭",
                iconId = R.drawable.ic_fluent_dismiss_32_regular,
                onClick = onClickClose,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionScreenPreview() {
    ActionScreen(
        loading = true,
        message = "请求中",
        onClickClose = {},
    )
}
