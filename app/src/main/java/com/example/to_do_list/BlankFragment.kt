package com.example.to_do_list

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.to_do_list.databinding.FragmentBlankBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime


class BlankFragment(private var taskItem: TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBlankBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null
    private var startTime: LocalTime? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(taskItem!=null){
            binding.TaskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)
            if(taskItem!!.endTime() != null){
                dueTime = taskItem!!.endTime()!!
                updateDueTimeButtonText()
            }
            if(taskItem!!.startTime() != null){
                startTime = taskItem!!.startTime()!!
                updateStartTimeButtonText()
            }
        }else{
            binding.TaskTitle.text="New Task"
        }
        val activity = requireActivity()
        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]
        binding.Save.setOnClickListener{
            saveAction()
        }
        binding.StartTime.setOnClickListener{
            openStartTime()
        }
        binding.EndTime.setOnClickListener{
            openEndTime()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openEndTime() {
        if(dueTime==null) dueTime=LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener {_,selectedHour,selectedMinute->
            dueTime = LocalTime.of(selectedHour,selectedMinute)
            updateDueTimeButtonText()
        }
        val dialog=TimePickerDialog(activity,listener,dueTime!!.hour,dueTime!!.minute,false)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openStartTime() {
        if(startTime==null) startTime=LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener {_,selectedHour,selectedMinute->
            startTime = LocalTime.of(selectedHour,selectedMinute)
            updateStartTimeButtonText()
        }
        val dialog=TimePickerDialog(activity,listener,startTime!!.hour,startTime!!.minute,false)
        dialog.setTitle("Task Start")
        dialog.show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAction() {
        val name = binding.name.text.toString()
        val desc = binding.desc.text.toString()
        val dueTimeString = if(dueTime ==null) null else TaskItem.timeFormatter.format(dueTime)
        val startTimeString = if(startTime ==null) null else TaskItem.timeFormatter.format(startTime)
        if(taskItem == null)
        {
            val newTask = TaskItem(name,desc,startTimeString,dueTimeString,null)
            taskViewModel.add(newTask)
        }
        else
        {
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.startTimeString = startTimeString
            taskItem!!.endTimeString = dueTimeString
            taskViewModel.update(taskItem!!)
        }
        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDueTimeButtonText() {
        binding.EndTime.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateStartTimeButtonText() {
        binding.StartTime.text = String.format("%02d:%02d",startTime!!.hour,startTime!!.minute)
    }

}