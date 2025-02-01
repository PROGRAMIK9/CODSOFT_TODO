package com.example.to_do_list

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskItemRep(
    private val taskItemDao: TaskItemDao
) {
    val allTaskItems: Flow<List<TaskItem>> = taskItemDao.select()

    @WorkerThread
    suspend fun insertTask(taskItem: TaskItem){
        taskItemDao.insertTask(taskItem)
    }

    @WorkerThread
    suspend fun updateTask(taskItem: TaskItem){
        taskItemDao.updateTask(taskItem)
    }

    @WorkerThread
    suspend fun  deleteTask(taskItem: TaskItem){
        taskItemDao.deleteTask(taskItem)
    }
}