package com.example.taskintemp.util

import android.os.Build
import android.os.SystemClock
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Locale


object AppUtils {

    private var mLastClickTime: Long = 0
    fun isSafeClick(): Boolean {
        return if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            false
        } else {
            mLastClickTime = SystemClock.elapsedRealtime()
            true
        }
    }

    fun getCurrentSystemDateTime(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(Instant.now())
        } else {
            val currentTime = System.currentTimeMillis()
            return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(currentTime)
        }
    }

    fun getCurrentSystemDate(): String {
        val currentTime = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(currentTime)
    }
}