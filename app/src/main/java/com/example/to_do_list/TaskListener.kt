package com.example.to_do_list

interface TaskListener {
    fun editTask(taskItem: TaskItem)
    fun completeTask(taskItem: TaskItem)
}