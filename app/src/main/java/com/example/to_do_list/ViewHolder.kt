package com.example.to_do_list

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_list.databinding.ToDoCellBinding
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Date
import java.util.Locale

class TaskViewHolder(
    private val context: Context,
    private var binding: ToDoCellBinding,
    private var taskListener: TaskListener,
):RecyclerView.ViewHolder(binding.root) {

    fun bindData(taskItem: TaskItem){
        binding.CardTitle.text = taskItem.name
        if(taskItem.iscompleted()){
            binding.CardTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        binding.CardDesc.text = taskItem.desc
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
        val currentTime = SimpleDateFormat("HH:mm:00", Locale.getDefault()).format(Date())
        Log.i("Aarya",currentTime)
        Log.i("Aarya","${taskItem.startTimeString}")
        if (taskItem.startTimeString == currentTime) {
            binding.Cell.setBackgroundColor(Color.GRAY) // Highlight
        }
        if(taskItem.endTimeString == currentTime){
            binding.Cell.setBackgroundColor(Color.WHITE)
        }
        binding.Delete.setOnClickListener{
            taskListener.deleteTask(taskItem)
        }
    }
}