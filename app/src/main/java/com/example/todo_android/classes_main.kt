package com.example.todo_android
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

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
fun main() {
    var list = mutableListOf<Task>()
    var check = true
    println("To load an already existing ToDo list, press 'l'")
    println("To add a new task in the list, press 'n'")
    println("To view all the tasks in the list, press 'v'")
    println("To update a task, press 'u'")
    println("To mark a task as completed, press 'c'")
    println("To change the priority of a task, press 'p'")
    println("To delete a task, press 'd'")
    println("To exit the app press 'e'")
    while (check) {
        when (readln()) {
            "e" -> {
                check = false
            }
            "n" -> add(list)
            "v" -> {
                view(list)
                println("To view the list based on the completion of tasks press 'c', else press 'ce':")
                val inp = readLine()
                if (inp!=null) {
                    when(inp) {
                        "c" -> view_comp(list)
                        "ce" -> {
                            println("Done! What's next :")
                            continue
                        }
                    }
                }
            }
            "u" -> update(list)
            "d" -> delete(list)
            "c" -> complete(list)
            "p" -> priority(list)
            "l" -> {
                println("Enter the name of the file along with extension (eg : demo.txt)")
                list = loadTasksFormFile(readln())
                view(list)
            }
            else -> println("Enter a valid command :")
        }
    }
    save(list,"Demo.txt")
}