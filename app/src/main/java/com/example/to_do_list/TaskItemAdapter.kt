package com.example.to_do_list

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.ToDoCellBinding

class TaskItemAdapter(
    private val taskItem:List<TaskItem>,
    private var taskListener: TaskListener
):RecyclerView.Adapter<TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ToDoCellBinding.inflate(from,parent,false)
        return TaskViewHolder(parent.context,binding,taskListener)
    }

    override fun getItemCount(): Int = taskItem.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bindData(taskItem[position])
    }
}