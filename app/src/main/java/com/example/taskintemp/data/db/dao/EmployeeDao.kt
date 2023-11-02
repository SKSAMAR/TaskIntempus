package com.example.taskintemp.data.db.dao

import androidx.compose.runtime.State
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.taskintemp.data.db.entity.Employee
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert
    suspend fun insertCheckInTime(employee: Employee): Long

    @Query("SELECT * FROM employee ORDER BY id DESC")
    fun getAllCheckInTimes(): Flow<List<Employee>>


}