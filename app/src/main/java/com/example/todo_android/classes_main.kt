package com.example.todo_android
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


enum class Priority {
    HIGH,
    MEDIUM,
    LOW
}
data class Task (
    var description: String,
    var completed : Boolean = false,
    var priority : Priority = Priority.MEDIUM
)

fun MainScreen() {
    val taskList = remember { mutableStateListOf<Task>() }
    Column {
        Text("Task List:")
        taskList.forEach { task ->
            Text(task.description)
        }
        AddTaskScreen {

        }
    }
}

fun AddTaskScreen(onTaskAdded: (Task) -> Unit) {
    val description = remember { mutableStateOf("") }
    TextField(
        value = description.value,
        onValueChange = {value ->
            description.value = value
        },
        label = { Text("Text Description") }
    )

    Button(
        onClick = {
            val newTask = Task(description.value)
            onTaskAdded(newTask)
            description.value = ""
        }
    ) {
        Text("Add Task")
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen() }
    }
}
fun main() {
    MyApp()
}