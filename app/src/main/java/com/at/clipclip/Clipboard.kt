package com.at.clipclip

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.core.content.getSystemService

fun Context.getClip(): Result<String> = runCatching {
    val service = getSystemService<ClipboardManager>()
        ?: throw NullPointerException("ClipboardManager is null")
    val clip = service
        .primaryClip
        ?.getItemAt(0)
        ?.text
        ?: throw NullPointerException("ClipData is null")
    clip.toString()
}

fun Context.setClip(clip: String): Result<Unit> = runCatching {
    val clipData = ClipData.newPlainText("plain_text", clip)
    val service = getSystemService<ClipboardManager>()
        ?: throw NullPointerException("ClipboardManager is null")
    service.setPrimaryClip(clipData)
}
