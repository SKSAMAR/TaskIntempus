package com.example.taskintemp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Employee")
data class Employee(
    @PrimaryKey(autoGenerate = true) var id: Int = 0, // just made it for future purpose
    @ColumnInfo(name = "check_in_date") val check_in_date: String // for check in date and time
)