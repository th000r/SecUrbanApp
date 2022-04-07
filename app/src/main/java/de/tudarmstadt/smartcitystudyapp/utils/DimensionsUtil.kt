package de.tudarmstadt.smartcitystudyapp.utils

import android.util.DisplayMetrics

class DimensionsUtil {
    companion object {
        fun dpToPx(displayMetrics: DisplayMetrics, dp: Int): Int {
            val density: Float = displayMetrics.density
            return Math.round(dp.toFloat() * density)
        }
    }
}