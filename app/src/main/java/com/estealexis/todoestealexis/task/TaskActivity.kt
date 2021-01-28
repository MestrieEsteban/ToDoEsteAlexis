package com.estealexis.todoestealexis.task

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.tracklist.Task
import java.util.*

class TaskActivity: AppCompatActivity(){
    companion object {
        const val ADD_TASK_REQUEST_CODE = 666
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ass_task)
        var valid = findViewById<Button>(R.id.button)
        valid.setOnClickListener({
            val newTask = Task(id = UUID.randomUUID().toString(), title = "New Task !")
        })
    }
}