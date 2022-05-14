package com.putu.todo.utils

import com.putu.todo.ui.viewmodel.MainViewModel

fun updateState(
    mainViewModel: MainViewModel,
    id: Int = 0,
    todo: String = "",
    desc: String = "",
    date: String = "",
    time: String = "",
    isEditTodo: Boolean = false,
    isDeleteAll: Boolean = false,
    isWithDeadline: Boolean = false,
    isShowAddOrEditTodoDialog: Boolean = false,
) {
    mainViewModel.apply {
        onIdChange(id)
        onTodoChange(todo)
        onDescChange(desc)
        onDateChange(date)
        onTimeChange(time)
        onIsEditTodoChange(isEditTodo)
        onIsDeleteAllChange(isDeleteAll)
        onIsWithDeadlineChange(isWithDeadline)
        onIsShowAddOrEditTodoDialogChange(isShowAddOrEditTodoDialog)
    }
}