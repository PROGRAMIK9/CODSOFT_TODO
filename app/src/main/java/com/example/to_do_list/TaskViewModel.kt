package com.example.to_do_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import java.time.LocalDate
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class TaskViewModel(private val rep: TaskItemRep) : ViewModel(){
    var taskItem:LiveData<List<TaskItem>> = rep.allTaskItems.asLiveData()

    fun add(newTaskItem: TaskItem) = viewModelScope.launch{
        rep.insertTask(newTaskItem)
    }

    fun update(taskItems: TaskItem) = viewModelScope.launch{
        rep.updateTask(taskItems)
    }

    fun deleteTask(taskItems: TaskItem) = viewModelScope.launch {
        rep.deleteTask(taskItems)
    }

    fun setDate(taskItems: TaskItem)=viewModelScope.launch {
        if (!taskItems.iscompleted()) taskItems.completedDateString =
            TaskItem.dateFormatter.format(LocalDate.now())
        rep.updateTask(taskItems)
    }

    fun remDate(taskItems: TaskItem)=viewModelScope.launch {
        if (taskItems.iscompleted()) taskItems.completedDateString = null
        rep.updateTask(taskItems)
    }
}

class TaskItemModelFactory(private val repository: TaskItemRep) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}