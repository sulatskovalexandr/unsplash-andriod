package com.example.myapplication.services.photoDownloadService

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.example.myapplication.common.UNSPLASH_DIRECTORY
import java.io.File

class AndroidPhotoDownloader(
    context: Context
) : PhotoDownloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)


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


//    override fun isPhotoExists(fileName: String): Boolean {
//        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
//        val selection = //"${MediaStore.MediaColumns.RELATIVE_PATH} like ? and " +
//            "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
//        val relativePath = "${Environment.DIRECTORY_DOWNLOADS}${"fileName.jpg"}"
//        val selectionArgs = arrayOf(
//            //"%$relativePath%",
//            fileName
//        )
//        val uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
//        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use {
//            return it.count > 0
//        } ?: return false
//    }
}