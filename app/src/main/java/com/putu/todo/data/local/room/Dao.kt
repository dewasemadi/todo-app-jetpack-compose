package com.putu.todo.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.putu.todo.data.local.entity.Todo

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Query("SELECT * FROM todo ORDER BY id DESC")
    fun getTodos(): LiveData<List<Todo>>

    @Query("SELECT COUNT(*) FROM todo WHERE is_completed = :isCompleted")
    fun countCompletedTodos(isCompleted: Boolean): LiveData<Int>

    @Query("UPDATE todo SET is_completed = :isCompleted WHERE id = :id")
    suspend fun updateIsCompletedTodo(id: Int, isCompleted: Boolean)

    @Query("UPDATE todo SET todo = :todo, description = :description, date = :date, time = :time WHERE id = :id")
    suspend fun updateTodo(id: Int, todo: String, description: String, date: String, time: String)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo")
    suspend fun deleteAllTodos()
}