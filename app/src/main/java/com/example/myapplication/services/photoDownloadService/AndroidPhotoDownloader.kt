package com.example.myapplication.services.photoDownloadService

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import java.io.File

class AndroidPhotoDownloader(
    context: Context
) : PhotoDownloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(fileName: String, url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setTitle(fileName)
            .addRequestHeader("Authorization", "Bearer <Token>")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                File.separator + fileName + ".jpg"
            )
        return downloadManager.enqueue(request)
    }
}