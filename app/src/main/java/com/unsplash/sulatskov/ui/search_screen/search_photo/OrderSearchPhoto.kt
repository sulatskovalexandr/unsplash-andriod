package com.unsplash.sulatskov.ui.search_screen.search_photo

import com.unsplash.sulatskov.R

enum class OrderSearchPhoto(val titleRes: Int, val value: String) {
    LATEST(R.string.order_by_latest, "latest"),
    RELEVANT(R.string.order_by_relevant, "relevant")
}

enum class ContentFilterSearchPhoto(val titleRes: Int, val value: String) {
    HIGH(R.string.content_filter_high, "high"),
    LOW(R.string.content_filter_low, "low"),
}

enum class ColorSearchPhoto(val titleRes: Int, val value: String?) {
    ANY(R.string.color_all, null),
    BLACK_AND_WHITE(R.string.color_black_and_white, "black_and_white"),
    BLACK(R.string.color_black, "black"),
    WHITE(R.string.color_white, "white"),
    YELLOW(R.string.color_yellow, "yellow"),
    ORANGE(R.string.color_orange, "orange"),
    RED(R.string.color_red, "red"),
    PURPLE(R.string.color_purple, "purple"),
    MAGENTA(R.string.color_magenta, "magenta"),
    GREEN(R.string.color_green, "green"),
    TEAL(R.string.color_teal, "teal"),
    BLUE(R.string.color_blue, "blue")
}

enum class OrientationSearchPhoto(val titleRes: Int, val value: String?) {
    ANY(R.string.orientation_ALL, null),
    LANDSCAPE(R.string.orientation_landscape, "landscape"),
    PORTRAIT(R.string.orientation_portrait, "portrait"),
    SQUARISH(R.string.orientation_squarish, "squarish")
}
