package com.at.clipclip

import android.content.Context
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import androidx.core.content.getSystemService

fun Context.addUploadShortcut(): Result<Unit> = runCatching {
    val service = getSystemService<ShortcutManager>()
        ?: throw NullPointerException("ShortcutManager is null")
    if (!service.isRequestPinShortcutSupported) {
        throw Exception("Not support to request pin shortcut")
    }
    val shortcut = ShortcutInfo.Builder(this, "launcher_upload")
        .setShortLabel(getString(R.string.upload))
        .setLongLabel(getString(R.string.upload))
        .setIcon(Icon.createWithResource(this, R.mipmap.ic_upload_launcher))
        .setIntent(UploadActivity.createIntent(this))
        .build()
    service.requestPinShortcut(shortcut, null)
}

fun Context.addDownloadShortcut(): Result<Unit> = runCatching {
    val service = getSystemService<ShortcutManager>()
        ?: throw NullPointerException("ShortcutManager is null")
    if (!service.isRequestPinShortcutSupported) {
        throw Exception("Not support to request pin shortcut")
    }
    val shortcut = ShortcutInfo.Builder(this, "launcher_download")
        .setShortLabel(getString(R.string.download))
        .setLongLabel(getString(R.string.download))
        .setIcon(Icon.createWithResource(this, R.mipmap.ic_download_launcher))
        .setIntent(DownloadActivity.createIntent(this))
        .build()
    service.requestPinShortcut(shortcut, null)
}
