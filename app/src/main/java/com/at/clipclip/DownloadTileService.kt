package com.at.clipclip

import android.annotation.SuppressLint
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class DownloadTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        qsTile.state = Tile.STATE_INACTIVE
    }

    override fun onClick() {
        super.onClick()
        handleClick()
    }

    private fun handleClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val intent = DownloadActivity.createPendingIntent(this)
            startActivityAndCollapse(intent)
        } else {
            val intent = DownloadActivity.createIntent(this)
            @Suppress("DEPRECATION")
            @SuppressLint("StartActivityAndCollapseDeprecated")
            startActivityAndCollapse(intent)
        }
    }
}
