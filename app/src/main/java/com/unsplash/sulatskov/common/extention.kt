package com.unsplash.sulatskov.common

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.ContextWrapper
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.unsplash.sulatskov.R
import com.google.android.material.snackbar.Snackbar
import com.unsplash.sulatskov.constants.Const.UNSPLASH_DIRECTORY
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
    val circularProgressDrawable = CircularProgressDrawable(this).apply {
        strokeWidth = 7f
        centerRadius = 40f
        setColorSchemeColors(ContextCompat.getColor(this@getProgressBar, R.color.text_disabled))
        start()
    }
    return circularProgressDrawable
}

/**
 * Формат чисел 1000 -> 1K
 */
val Int.formated: String
    get() = if (this >= 1000000000) {
        String.format("%.1fB", this / 1000000000.0)
    } else if (this in 1000000..999999999) {
        String.format("%.1fM", this / 1000000.0)
    } else if (this in 1000..999999) {
        String.format("%.1fK", this / 1000.0)
    } else this.toString()

/**
 * Показать snackbar
 */
fun Fragment.snackbar(msg: String) {
    view?.apply {
        Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
    }
}

/**
 * Проверяет начилие файла в хранилище
 */
@RequiresApi(Build.VERSION_CODES.Q)
fun isPhotoExists(context: Context, fileName: String): Boolean {

    val UNSPLASH_RELATIVE_PATH = "${Environment.DIRECTORY_PICTURES}${File.separator}$UNSPLASH_DIRECTORY"
    val UNSPLASH_LEGACY_PATH =
        "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}${File.separator}$UNSPLASH_DIRECTORY"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
        val selection = "${MediaStore.MediaColumns.RELATIVE_PATH} like ? and " +
                "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val relativePath = UNSPLASH_RELATIVE_PATH
        val selectionArgs = arrayOf("%$relativePath%", "$fileName.jpg")
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use {
            return it.count > 0
        } ?: return false
    } else {
        return File(UNSPLASH_LEGACY_PATH, "$fileName.jpg").exists()
    }
}

/**
 * Показывает клавиатуру
 */
fun EditText.showKeyboard() {
    if (requestFocus()) {
        (getActivity()?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, SHOW_IMPLICIT)
        setSelection(text.length)
    }
}

/**
 * Context
 */
fun View.getActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

/**
 * Анимация при переключении фрагментов
 */
fun fragmentAnim(): NavOptions {
    return  NavOptions.Builder()
        .setEnterAnim(R.anim.enter_right)
        .setExitAnim(R.anim.exit_left)
        .setPopEnterAnim(R.anim.enter_left)
        .setPopExitAnim(R.anim.exit_right)
        .build()
}