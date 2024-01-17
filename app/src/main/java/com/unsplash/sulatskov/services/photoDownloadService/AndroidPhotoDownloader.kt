package com.unsplash.sulatskov.services.photoDownloadService

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.unsplash.sulatskov.constants.Const.UNSPLASH_DIRECTORY
import java.io.File

class AndroidPhotoDownloader(
    context: Context
) : PhotoDownloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    /**
     * Загрузка файла (фото) в хранилище (DIRECTORY_PICTURES)
     */
    override fun downloadFile(fileName: String, url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setTitle("$fileName.jpg")
            .addRequestHeader("Authorization", "Bearer <Token>")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                "$UNSPLASH_DIRECTORY${File.separator}$fileName.jpg"
            )
        return downloadManager.enqueue(request)
    }
}