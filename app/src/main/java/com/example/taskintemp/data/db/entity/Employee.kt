package com.example.taskintemp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("employee")
data class Employee(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "timeStamp") val timeStamp: String?
)