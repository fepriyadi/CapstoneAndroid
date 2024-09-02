package com.example.core.utils
import android.content.Context
import android.util.Log
import com.example.core.BuildConfig
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.formatDate(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())

    return try {
        // Parse the input date string to a Date object
        val date = inputFormat.parse(this)
        // Format the Date object to the desired output format
        outputFormat.format(date)
    } catch (e: ParseException) {
        // Handle the case where parsing fails
        "Invalid date"
    }
}

fun String.formatYear(): String? {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy", Locale.getDefault())

    return try {
        // Parse the input date string to a Date object
        val date = inputFormat.parse(this)
        // Format the Date object to the desired output format
        outputFormat.format(date)
    } catch (e: ParseException) {
        // Handle the case where parsing fails
        "Invalid date"
    }
}

fun Int.toFormattedRuntime(): String {
    val hours = this / 60
    val minutes = this % 60
    return "${hours}h ${minutes}m"
}

fun Double.toAsteriskRating(scale: Int = 5): String {
    val maxScore = 10.0
    val normalizedScore = (this / maxScore) * scale
    val stars = normalizedScore.toInt()
    return "*".repeat(stars) // Return a string with `stars` asterisks
}

fun Float.getNumberOfColumns(context: Context): Int {
    val screenWidth = context.resources.displayMetrics.widthPixels
    return screenWidth.div(this).toInt()
}

fun Date.format(pattern: String) = SimpleDateFormat(pattern).format(this)

fun Number?.format(pattern: String) = String.format(pattern, this)

fun String.log(msg: String) {
    if (BuildConfig.DEBUG) {
        Log.d(this::class.simpleName, "===================>  $msg $this")
    }
}

fun dpToPx(context: Context, dp: Int): Int {
    val density: Float = context.resources.displayMetrics.density
    return Math.round(dp * density)
}