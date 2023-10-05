package com.example.myapplication.common

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.io.File


/**
 * Запускает (и перезапускает) на фрагменте collect для переданного flow только в состоянии активности
 */
inline fun <T> Fragment.observeData(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner = viewLifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: (T) -> Unit
) = lifecycleOwner.lifecycleScope.launch {
    lifecycleOwner.repeatOnLifecycle(state) {
        flow.collect { data ->
            block(data)
        }
    }
}

/**
 * Progress bar
 */
fun Context.getProgressBar(): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(this)
    circularProgressDrawable.setColorSchemeColors(Color.GRAY)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

/**
 * Формат 1000 -> 1K
 */
val Int.formated: String
    get() = if (this >= 1000000000) {
        String.format("%.1fB", this / 1000000000.0)
    } else if (this in 1000000..999999999) {
        String.format("%.1fM", this / 1000000.0)
    } else if (this in 1000..999999) {
        String.format("%.1fK", this / 1000.0)
    } else this.toString()

fun Fragment.snackbar(msg: String) {
    view?.apply {
        Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
    }
}

const val UNSPLASH_DIRECTORY = "Unsplash "
val UNSPLASH_RELATIVE_PATH = "${Environment.DIRECTORY_PICTURES}${File.separator}$UNSPLASH_DIRECTORY"
val UNSPLASH_LEGACY_PATH =
    "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}${File.separator}$UNSPLASH_DIRECTORY"

@RequiresApi(Build.VERSION_CODES.Q)
fun isPhotoExists(context: Context, fileName: String): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
        val selection = //"${MediaStore.MediaColumns.RELATIVE_PATH} like ? and " +
            "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val relativePath = UNSPLASH_RELATIVE_PATH
        val selectionArgs = arrayOf(
            "%$relativePath%",
            fileName
        )
        val uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use {
            return it.count > 0
        } ?: return false
    } else {
        return File(UNSPLASH_LEGACY_PATH, "$fileName.jpg").exists()
    }
}








