package com.example.myapplication.services.photoDownloadService

interface PhotoDownloader {
    fun downloadFile(fileName: String, url: String): Long
}