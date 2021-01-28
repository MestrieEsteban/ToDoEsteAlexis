package com.estealexis.todoestealexis.task

import android.app.Activity
import android.content.Intent
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
        const val TASK_KEY = "task"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ass_task)
        var valid = findViewById<Button>(R.id.button)
        valid.setOnClickListener({
            val resultIntent = Intent()
            val task= Task(id = UUID.randomUUID().toString(), title = "New Task !")
            resultIntent.putExtra("task", task)
            setResult(RESULT_OK, resultIntent)
            finish()
        })
    }
}