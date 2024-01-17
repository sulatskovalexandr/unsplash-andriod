package com.unsplash.sulatskov.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.R)
class ManageStorageContract : ActivityResultContract<Unit, Boolean>() {

    /**
     * Доступ к хранилищу
     */
    override fun createIntent(context: Context, input: Unit): Intent =
        Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
            addCategory("android.intent.category.DEFAULT")
            data = Uri.parse("package:${context.applicationContext.packageName}")
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean =
        Environment.isExternalStorageManager()
}