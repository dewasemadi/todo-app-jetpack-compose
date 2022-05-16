package com.putu.todo.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.putu.todo.R
import com.putu.todo.data.local.entity.Todo
import com.putu.todo.ui.component.*
import com.putu.todo.ui.theme.Blue500
import com.putu.todo.ui.theme.CustomBlue
import com.putu.todo.ui.theme.TodoTheme
import com.putu.todo.ui.viewmodel.MainViewModel
import com.putu.todo.ui.viewmodel.ViewModelFactory
import com.putu.todo.utils.currentDateAndTime
import com.putu.todo.utils.updateState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val listState = rememberLazyListState()
            val scaffoldState = rememberScaffoldState()
            val backdropScaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed)
            val todos by mainViewModel.getTodos().observeAsState(listOf())
            val isDeleteAll by mainViewModel.isDeleteAll.observeAsState(false)
            val isShowAddOrEditTodoDialog by mainViewModel.isShowAddOrEditTodoDialog.observeAsState(false)

            TodoTheme(darkTheme = false) {
                Scaffold(
                    scaffoldState = scaffoldState,
                    floatingActionButton = { FloatingButton(mainViewModel) },
                    drawerShape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp),
                    drawerContent = { MyDrawer() }
                ) {
                    BackdropScaffold(
                        scaffoldState = backdropScaffoldState,
                        appBar = { MyTopAppBar(mainViewModel, todos, scaffoldState, backdropScaffoldState) },
                        backLayerBackgroundColor = CustomBlue,
                        backLayerContent = {
                            Banner(
                                stringResource(R.string.greeting),
                                stringResource(R.string.motivation),
                                R.drawable.banner
                            )
                        },
                        frontLayerContent = {
                            if (isShowAddOrEditTodoDialog) {
                                AddOrEditTodoDialogView(mainViewModel, backdropScaffoldState, listState) {
                                    updateState(mainViewModel)
                                }
                            }

                            if (todos.isEmpty()) {
                                Empty(stringResource(R.string.empty_todo))
                            } else {
                                if (isDeleteAll)
                                    DeleteDialog(mainViewModel)
                                TodoListView(mainViewModel, listState, todos)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FloatingButton(mainViewModel: MainViewModel) {
    FloatingActionButton(
        backgroundColor = Blue500,
        onClick = { mainViewModel.onIsShowAddOrEditTodoDialogChange(true) },
        content = { AddIcon() }
    )
}

@Composable
fun MyDrawer() {
    Column(Modifier.fillMaxSize().padding(bottom = 20.dp), Arrangement.Bottom, Alignment.CenterHorizontally) {
        Text(text = "Todo App", color = Color.LightGray, style = TextStyle(fontSize = 14.sp))
        Text(text = "version 1.0.0", color = Color.LightGray, style = TextStyle(fontSize = 12.sp))
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun MyTopAppBar(
    mainViewModel: MainViewModel,
    todos: List<Todo>,
    scaffoldState: ScaffoldState,
    backdropScaffoldState: BackdropScaffoldState,
) {
    val coroutineScope  = rememberCoroutineScope()
    val countInProgress by mainViewModel.countCompletedTodos(false).observeAsState(0)

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
        backgroundColor = CustomBlue,
        title = {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = backdropScaffoldState.isConcealed,
                    enter = fadeIn(animationSpec = tween(durationMillis = 100)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 100)),
                ) {
                    Text (
                        text = when {
                            backdropScaffoldState.isConcealed -> when {
                                countInProgress > 1 -> stringResource(R.string.progress, countInProgress)
                                countInProgress == 1 -> stringResource(R.string.almost_done)
                                countInProgress == 0  -> stringResource(R.string.all_done)
                                else -> ""
                            }
                            else -> ""
                        },
                        style = TextStyle(fontSize = 18.sp),
                        color = Color.White,
                    )
                }

                if (todos.isNotEmpty()) {
                    AnimatedVisibility(
                        visible = backdropScaffoldState.isConcealed,
                        enter = scaleIn(animationSpec = tween(durationMillis = 100)),
                        exit = scaleOut(animationSpec = tween(durationMillis = 100)),
                    ) {
                        IconButton(onClick = { mainViewModel.onIsDeleteAllChange(true) }) {
                            DeleteIcon()
                        }
                    }
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } }) {
                MenuIcon()
            }
        },
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun TodoListView(
    mainViewModel: MainViewModel,
    listState: LazyListState,
    todos: List<Todo>,
) {
    val context = LocalContext.current

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize().padding(top = 15.dp)) {
        itemsIndexed(items = todos, key = { _, item -> item.id.hashCode() }) { _, data ->
            val (_todo, _desc, _date, _time, _isCompleted, _id) = data

            val state = rememberDismissState(
                initialValue = DismissValue.Default,
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart ) {
                        mainViewModel.deleteTodo(data)
                        Toast.makeText(context, R.string.delete, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
            )

            SwipeToDismiss(
                state = state,
                modifier = Modifier.animateItemPlacement(),
                background = { SwipeBackground(state) },
                directions = setOf(DismissDirection.EndToStart),
                dismissContent = {
                    TodoItem(
                        todo = _todo,
                        desc = _desc,
                        date = _date,
                        time = _time,
                        isChecked = _isCompleted,
                        onEdited = {
                            updateState(
                                mainViewModel, _id, _todo, _desc, _date, _time,
                                isWithDeadline = _date != "" && _time != "",
                                isShowAddOrEditTodoDialog = true,
                                isEditTodo = true,
                            )
                        },
                        onChecked = {
                            mainViewModel.updateIsCompletedTodo(_id, !_isCompleted)
                        }
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddOrEditTodoDialogView(
    mainViewModel: MainViewModel,
    backdropScaffoldState: BackdropScaffoldState,
    listState: LazyListState,
    updateState: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope  = rememberCoroutineScope()
    val id by mainViewModel.id.observeAsState(0)
    val todo by mainViewModel.todo.observeAsState("")
    val desc by mainViewModel.desc.observeAsState("")
    val date by mainViewModel.date.observeAsState("")
    val time by mainViewModel.time.observeAsState("")
    val isEditTodo by mainViewModel.isEditTodo.observeAsState(false)
    val isWithDeadline by mainViewModel.isWithDeadline.observeAsState(false)

    AddOrEditTodoDialog(
        todo = todo,
        desc = desc,
        date = date,
        time = time,
        isChecked = isWithDeadline,
        confirmText = stringResource(R.string.save),
        onTodoChange = { mainViewModel.onTodoChange(it) },
        onDescChange = { mainViewModel.onDescChange(it) },
        onDateChange = { mainViewModel.onDateChange(it) },
        onTimeChange = { mainViewModel.onTimeChange(it) },
        onCheckedChange = {
            val (currentDate, currentTime) = currentDateAndTime()

            mainViewModel.apply {
                onIsWithDeadlineChange(!isWithDeadline)
                onDateChange(if (isWithDeadline) currentDate else "")
                onTimeChange(if (isWithDeadline) currentTime else "")
            }
        },
        onConfirmClicked = {
            if (todo != "") {
                if (isEditTodo) {
                    mainViewModel.updateTodo(id, todo, desc, date, time)
                } else {
                    val newTodo = Todo(todo, desc, date, time)
                    mainViewModel.insertTodo(newTodo)
                    coroutineScope.launch {
                        if (backdropScaffoldState.isRevealed)
                            backdropScaffoldState.conceal()
                        listState.animateScrollToItem(0, 0)
                    }
                }
                updateState()
            } else {
                Toast.makeText(context, R.string.fill_todo_field, Toast.LENGTH_SHORT).show()
            }
        },
        onDismiss = {
            updateState()
        }
    )
}

@Composable
fun DeleteDialog(mainViewModel: MainViewModel) {
    SimpleDialog(
        title = stringResource(R.string.delete_all_title),
        desc = stringResource(R.string.delete_all_desc),
        confirmText = stringResource(R.string.yes),
        onConfirmClicked = {
            mainViewModel.apply {
                deleteAllTodos()
                onIsDeleteAllChange(false)
            }
        },
        onDismiss = { mainViewModel.onIsDeleteAllChange(false) }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeBackground(state: DismissState) {
    val color = when (state.dismissDirection) {
        DismissDirection.StartToEnd -> Color.Transparent
        DismissDirection.EndToStart -> Color.Red
        null -> Color.Transparent
    }

    Box(Modifier.fillMaxSize().background(color = color).padding(8.dp)){
        DeleteIcon(Modifier.align(Alignment.CenterEnd))
    }
}