package com.example.taskintemp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskintemp.data.db.entity.Employee
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCheckInTime(employee: Employee): Long

    @Query("SELECT * FROM Employee ORDER BY id DESC")
    fun getAllCheckInTimes(): Flow<List<Employee>>

    @Query("SELECT * FROM Employee WHERE check_in_date = :check_in_date LIMIT 1")
    suspend fun getEmployeeByTimestamp(check_in_date: String): Employee?

    @Query("SELECT * FROM Employee ORDER BY id DESC LIMIT 1")
    suspend fun getMostRecentCheckIn(): Employee?
}