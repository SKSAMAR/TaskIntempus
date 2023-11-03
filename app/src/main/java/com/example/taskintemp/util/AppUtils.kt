package com.example.taskintemp.util

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Context
import android.os.SystemClock
import android.widget.DatePicker
import android.widget.Toast
import java.text.SimpleDateFormat


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
        val currentTime = System.currentTimeMillis()
        return SimpleDateFormat(Constants.TIME_STAMP_FORMAT).format(currentTime)
    }

    fun getCurrentSystemDate(): String {
        val currentTime = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-MM-dd").format(currentTime)
    }


    fun Context.showTimePickerDialog(
        hour: Int,
        minute: Int,
        newTimeSelected: (hour: Int, minute: Int) -> Unit
    ) {
        val timePickerDialog = TimePickerDialog(this,
            { _, hourOfDay, minutesOfDay -> newTimeSelected(hourOfDay, minutesOfDay) },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }


    fun Context.showDatePickerDialog(
        dYear: Int,
        dMonth: Int,
        dDay: Int,
        newDateSelected: (date: String) -> Unit
    ) {
        val fromDatePicker = OnDateSetListener { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->

            val fixedMonth = if (monthOfYear < 10) "0$monthOfYear" else "$monthOfYear"
            val fixedDay = if (dayOfMonth < 10) "0$dayOfMonth" else "$dayOfMonth"

            newDateSelected.invoke("$year-$fixedMonth-$fixedDay")
        }
        DatePickerDialog(
            this, fromDatePicker,
           dYear, dMonth, dDay
        ).show()
    }

    fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

}