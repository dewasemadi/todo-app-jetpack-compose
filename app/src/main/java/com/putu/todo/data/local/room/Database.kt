package com.putu.todo.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.putu.todo.data.local.entity.Todo
import com.putu.todo.data.local.room.Database as TaskDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var instance: TaskDatabase? = null
        fun getInstance(context: Context): TaskDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java, "task_database"
                ).build()
            }
    }
}