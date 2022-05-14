package com.putu.todo.di

import android.content.Context
import com.putu.todo.data.local.room.Database
import com.putu.todo.data.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val database = Database.getInstance(context)
        val dao = database.dao()
        return Repository.getInstance(dao)
    }
}