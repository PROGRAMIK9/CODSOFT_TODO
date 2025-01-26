package com.example.to_do_list

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.ToDoCellBinding
import java.time.format.DateTimeFormatter

class TaskViewHolder(
    private val context: Context,
    private var binding: ToDoCellBinding,
    private var taskListener: TaskListener
):RecyclerView.ViewHolder(binding.root) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun bindData(taskItem: TaskItem){
        binding.CardTitle.text = taskItem.name
        if(taskItem.iscompleted()){
            binding.CardTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        binding.complete.setImageResource(taskItem.imageResource())
        binding.complete.setColorFilter(taskItem.imageColor(context))
        binding.complete.setOnClickListener{
            taskListener.completeTask(taskItem)
        }
        binding.Cell.setOnClickListener{
            taskListener.editTask(taskItem)
        }
        if(taskItem.endTimeString!=null){
            binding.Due.text = taskItem.endTimeString
        }else{
            binding.Due.text = ""
        }
        if(taskItem.startTime()!=null){
            binding.start.text = taskItem.startTimeString
        }else{
            binding.start.text = ""
        }
    }
}