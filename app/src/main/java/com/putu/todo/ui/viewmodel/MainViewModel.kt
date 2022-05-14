package com.putu.todo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.putu.todo.data.local.entity.Todo
import com.putu.todo.data.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository): ViewModel() {
    private val _id = MutableLiveData(0)
    val id: LiveData<Int> = _id

    private val _todo = MutableLiveData("")
    val todo: LiveData<String> = _todo

    private val _desc = MutableLiveData("")
    val desc: LiveData<String> = _desc

    private val _date = MutableLiveData("")
    val date: LiveData<String> = _date

    private val _time = MutableLiveData("")
    val time: LiveData<String> = _time

    private val _isEditTodo = MutableLiveData(false)
    val isEditTodo: LiveData<Boolean> = _isEditTodo

    private val _isDeleteAll = MutableLiveData(false)
    val isDeleteAll: LiveData<Boolean> = _isDeleteAll

    private val _isWithDeadline = MutableLiveData(false)
    val isWithDeadline: LiveData<Boolean> = _isWithDeadline

    private val _isShowAddOrEditTodoDialog = MutableLiveData(false)
    val isShowAddOrEditTodoDialog: LiveData<Boolean> = _isShowAddOrEditTodoDialog

    fun onIdChange(id: Int) {
        _id.value = id
    }

    fun onTodoChange(todo: String) {
        _todo.value = todo
    }

    fun onDescChange(desc: String) {
        _desc.value = desc
    }

    fun onDateChange(date: String) {
        _date.value = date
    }

    fun onTimeChange(time: String) {
        _time.value = time
    }

    fun onIsEditTodoChange(isEditTodo: Boolean) {
        _isEditTodo.value = isEditTodo
    }

    fun onIsDeleteAllChange(isDeleteAll: Boolean) {
        _isDeleteAll.value = isDeleteAll
    }

    fun onIsWithDeadlineChange(isWithDeadline: Boolean) {
        _isWithDeadline.value = isWithDeadline
    }

    fun onIsShowAddOrEditTodoDialogChange(isShowAddOrEditTodoDialog: Boolean) {
        _isShowAddOrEditTodoDialog.value = isShowAddOrEditTodoDialog
    }

    fun getTodos(): LiveData<List<Todo>> {
        return repository.getTodos()
    }

    fun countCompletedTodos(isCompleted: Boolean): LiveData<Int> {
        return repository.countCompletedTodos(isCompleted)
    }

    fun insertTodo(todo: Todo) {
        viewModelScope.launch {
            repository.insertTodo(todo)
        }
    }

    fun updateIsCompletedTodo(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateIsCompletedTodo(id, isCompleted)
        }
    }

    fun updateTodo(id: Int, todo: String, description: String, date: String, time: String) {
        viewModelScope.launch {
            repository.updateTodo(id, todo, description, date, time)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

    fun deleteAllTodos(){
        viewModelScope.launch {
            repository.deleteAllTodos()
        }
    }
}