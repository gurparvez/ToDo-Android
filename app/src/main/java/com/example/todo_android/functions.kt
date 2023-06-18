package com.example.todo_android

import java.io.File
import java.io.FileWriter
import java.io.IOException

fun add(list: MutableList<Task>) {
    println("What is the task? :")
    val t = readLine()
    val ts = Task(t?: "")
    list.add(ts)
    println("Done! What's next :")
}
fun sort(list: MutableList<Task>) {
    list.sortBy { it.priority }
}
fun view(list: MutableList<Task>) {
    if (list.isEmpty()) {
        println("There is no task! Try adding some tasks by pressing 'n'")
        return
    }
    sort(list)
    println("ToDo:")
    for ((index, task) in list.withIndex()) {
        println("$index : ${task.description} : ${task.priority} : ${task.completed}")
    }
    println("Done! What's next :")
}
fun update(list: MutableList<Task>) {
    print("choose a task using its index : " )
    view(list)
    val change = readln().toIntOrNull()
    if (change != null && change in 0 until list.size) {
        println("What is the task ? :")
        val td = readLine()
        list[change] = Task(td ?: "")
    } else {
        println("Invalid task index!")
    }
}
fun delete(list : MutableList<Task>) {
    print("choose a task using its index : \n")
    view(list)
    val change = readln().toInt()
    println("Are you sure you want to delete ${list[change].description} ?! : (y/n)")
    if (readln() == "y"){
        list.removeAt(change)
    }
    println("Done! What's next :")
}
fun complete(list: MutableList<Task>) {
    println("Choose a task using its index :\n")
    view(list)
    val comp = readLine()?.toIntOrNull()
    if (comp!= null && comp in 0 until list.size) {
        list[comp].completed = true
    } else println("Invalid task index!")
}
fun priority(list: MutableList<Task>) {
    println("Choose a task using its index :\n")
    view(list)
    val comp = readLine()?.toIntOrNull()
    if (comp!= null && comp in 0 until list.size) {
        println("Choose priority : (0 = HIGH, 1 = MEDIUM, 2 = LOW)")
        val pr = readLine()?.toIntOrNull()
        if (pr in 0..2 && pr!=null) {
            val prior = when(pr) {
                0 -> Priority.HIGH
                1 -> Priority.MEDIUM
                2 -> Priority.LOW
                else -> null
            }
            if (prior!=null) {
                list[comp].priority = prior
                println("priority updated!")
            }
        } else {
            println("Invalid priority!")
            return
        }
    } else println("Invalid task index!")
    println("Done! What's next :")
}
fun view_comp(list: MutableList<Task>) {
    println("ToDo:")

    val incompleteTasks = mutableListOf<Task>()
    val completedTasks = mutableListOf<Task>()

    for ((index, task) in list.withIndex()) {
        if (task.completed) {
            completedTasks.add(task)
        } else {
            incompleteTasks.add(task)
        }
    }

    println("\nIncomplete:")
    for ((index, task) in incompleteTasks.withIndex()) {
        println("$index : ${task.description} : ${task.priority} : ${task.completed}")
    }

    println("\nCompleted:")
    for ((index, task) in completedTasks.withIndex()) {
        println("$index : ${task.description} : ${task.priority} : ${task.completed}")
    }
    println("Done! What's next :")
}
fun File.appendTextLine(text: String) {
    this.appendText("$text\n")
}
fun save (list: MutableList<Task>,file:String) {
    try {
        val f = File(file)
        for ((index,task) in list.withIndex()) {
            f.appendTextLine("${task.description} : ${task.priority} : ${task.completed}")
        }
    } catch (e:IOException) {
        println("An error occurred while saving tasks : ${e.message}")
    }
}
fun loadTasksFormFile (filename:String) : MutableList<Task> {
    val file = File(filename)
    val tasks = mutableListOf<Task>()

    if (file.exists()) {
        println("file found! Loading data...")
        file.forEachLine {line ->
            val taskData = line.split(" : ")
            if (taskData.size == 3) {
                val description = taskData[0]
                val priority = Priority.valueOf(taskData[1])
                val completed = taskData[2].toBoolean()
                val task = Task(description,completed,priority)
                tasks.add(task)
                println("Data loaded!")
            } else println("File '$filename' doesn't contain a valid ToDo list.")
        }
    } else println("File '$filename' doesn't exist.")
    return tasks
}