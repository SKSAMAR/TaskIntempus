package com.example.taskintemp.data.di

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.taskintemp.R
import com.example.taskintemp.data.db.EmployeeDb
import com.example.taskintemp.data.db.dao.EmployeeDao
import com.example.taskintemp.data.db.entity.Employee
import com.example.taskintemp.data.remote.api.CheckInApi
import com.example.taskintemp.data.repository.CheckInRepositoryImp
import com.example.taskintemp.domain.repository.CheckInRepository
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CheckInModule {

    @Singleton
    @Provides
    fun getCheckInApi(
        @ApplicationContext context: Context
    ): CheckInApi {
        val gson = GsonBuilder()
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .create()
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(context.getString(R.string.base_url))//url to hit all apis
            .build()
        return retrofit.create(CheckInApi::class.java)
    }


    @Singleton
    @Provides
    fun getCheckInRepository(api: CheckInApi, dao: EmployeeDao): CheckInRepository {
        return CheckInRepositoryImp(api = api, employeeDao = dao)
    }


    @Singleton
    @Provides
    fun getEmployeeDatabase(@ApplicationContext context: Context): EmployeeDb =
        Room.databaseBuilder(
            context.applicationContext,
            EmployeeDb::class.java,
            context.getString(R.string.database_name)
        ).addMigrations().fallbackToDestructiveMigration().build()


    @Singleton
    @Provides
    fun getEmployeeDao(db: EmployeeDb): EmployeeDao = db.employeeDao()

}