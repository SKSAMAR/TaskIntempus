package com.example.taskintemp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskintemp.data.db.entity.Employee
import com.example.taskintemp.data.db.dao.EmployeeDao


@Database(entities = [Employee::class], version = 3)
abstract class EmployeeDb : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}
