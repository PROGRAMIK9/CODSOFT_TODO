package com.example.to_do_list

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Entity(tableName = "task_item_table")
class TaskItem(
    @ColumnInfo("name") var name:String,
    @ColumnInfo("desc") var desc:String,
    @ColumnInfo("startTime") var startTimeString:String?,
    @ColumnInfo("endTime") var endTimeString:String?,
    @ColumnInfo("date") var completedDateString:String?,
    @PrimaryKey(autoGenerate = true) var id:Int = 0
) {
    @RequiresApi(Build.VERSION_CODES.O)
    private fun completedDate(): LocalDate? = if(completedDateString == null) null else LocalDate.parse(completedDateString,dateFormatter)

    @RequiresApi(Build.VERSION_CODES.O)
    fun endTime(): LocalTime? = if(endTimeString == null) null else LocalTime.parse(endTimeString,timeFormatter)

    @RequiresApi(Build.VERSION_CODES.O)
    fun startTime(): LocalTime? = if(startTimeString == null) null else LocalTime.parse(startTimeString,timeFormatter)

    @RequiresApi(Build.VERSION_CODES.O)
    fun iscompleted() = completedDate()!=null

    @RequiresApi(Build.VERSION_CODES.O)
    fun imageResource():Int = if(iscompleted()) R.drawable.baseline_check_circle_outline_24 else R.drawable.round_adjust_24

    @RequiresApi(Build.VERSION_CODES.O)
    fun imageColor(context: Context):Int = if(iscompleted()) purple(context) else black(context)

    private fun purple(context:Context) =  ContextCompat.getColor(context,R.color.purple_500)

    private fun black(context:Context) = ContextCompat.getColor(context,R.color.black)

    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        val timeFormatter:DateTimeFormatter = DateTimeFormatter.ISO_TIME
        @RequiresApi(Build.VERSION_CODES.O)
        val dateFormatter:DateTimeFormatter = DateTimeFormatter.ISO_DATE
    }

}