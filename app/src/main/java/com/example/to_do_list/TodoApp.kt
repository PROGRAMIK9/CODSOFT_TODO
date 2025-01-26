package com.example.to_do_list

import android.app.Application

class TodoApp:Application() {
    private val database by lazy{TaskItemDatabase.getDatabase(this)}
    val rep by lazy{TaskItemRep(database.taskItemDao())}
}