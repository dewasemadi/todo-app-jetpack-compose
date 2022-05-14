package com.putu.todo.data.repository

import androidx.lifecycle.LiveData
import com.putu.todo.data.local.entity.Todo
import com.putu.todo.data.local.room.Dao

class Repository(private val dao: Dao) {
    fun getTodos(): LiveData<List<Todo>> = dao.getTodos()

    fun countCompletedTodos(isCompleted: Boolean): LiveData<Int> = dao.countCompletedTodos(isCompleted)

    suspend fun insertTodo(todo: Todo) = dao.insertTodo(todo)

    suspend fun deleteTodo(todo: Todo) = dao.deleteTodo(todo)

    suspend fun updateIsCompletedTodo(id: Int, isCompleted: Boolean) = dao.updateIsCompletedTodo(id, isCompleted)

    suspend fun updateTodo(id: Int, todo: String, description: String, date: String, time: String) = dao.updateTodo(id, todo, description, date, time)

    suspend fun deleteAllTodos() = dao.deleteAllTodos()

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(dao: Dao): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(dao)
            }.also { instance = it }
    }
}