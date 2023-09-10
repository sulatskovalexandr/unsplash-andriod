package com.example.myapplication.common

sealed interface Messages {
    object NetworkIsDisconnected : Messages
    object NetworkIsConnected : Messages
    object AlreadyDownloaded : Messages
}