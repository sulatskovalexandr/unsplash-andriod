package com.example.myapplication.common

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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





