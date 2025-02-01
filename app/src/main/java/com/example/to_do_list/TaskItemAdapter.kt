package com.example.to_do_list

import android.annotation.SuppressLint
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.ToDoCellBinding

class TaskItemAdapter(
    private val taskItem:List<TaskItem>,
    private var taskListener: TaskListener
):RecyclerView.Adapter<TaskViewHolder>() {
    private val handler = Handler(Looper.getMainLooper())
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ToDoCellBinding.inflate(from,parent,false)
        return TaskViewHolder(parent.context,binding,taskListener)
    }

    override fun getItemCount(): Int = taskItem.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindData(taskItem[position])
    }
    fun startUpdating() {
        handler.post(object : Runnable {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                notifyDataSetChanged() // Refresh all tasks
                handler.postDelayed(this, 60000) // Repeat every 1 minute
            }
        })
    }
}