package com.unsplash.sulatskov.services.photoDownloadService

interface PhotoDownloader {
    fun downloadFile(fileName: String, url: String): Long
}