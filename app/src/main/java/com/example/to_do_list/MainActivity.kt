package com.example.to_do_list

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do_list.databinding.ActivityMainBinding
import com.example.to_do_list.databinding.TaskCreatorBinding

class MainActivity : AppCompatActivity(),TaskListener
{
    private lateinit var binding: TaskCreatorBinding
    private val taskViewModel: TaskViewModel by viewModels{
        TaskItemModelFactory((application as TodoApp).rep)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TaskCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.NewTask.setOnClickListener{
            BlankFragment(null).show(supportFragmentManager,"newTask")
        }
        setTaskView()
    }

    private fun setTaskView() {
        val mainActivity = this
        taskViewModel.taskItem.observe(this){
            val adapter = TaskItemAdapter(it,mainActivity)
            binding.TaskList.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                this.adapter = adapter
            }
            adapter.startUpdating()
            if(adapter.itemCount==0){
                binding.Welcome.visibility = VISIBLE
            }
            else{
                binding.Welcome.visibility = INVISIBLE
            }
        }
    }

    override fun editTask(taskItem: TaskItem) {
        BlankFragment(taskItem).show(supportFragmentManager,"newTaskTag")
    }

    override fun completeTask(taskItem: TaskItem) {
        if (taskItem.iscompleted()){taskViewModel.remDate(taskItem)}
        else{taskViewModel.setDate(taskItem)}
    }

    override fun deleteTask(taskItem: TaskItem) {
        taskViewModel.deleteTask(taskItem) // Call ViewModel function
    }

}