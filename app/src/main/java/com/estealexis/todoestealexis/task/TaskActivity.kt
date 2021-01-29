package com.estealexis.todoestealexis.task

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val valid = findViewById<Button>(R.id.button)
        val title = findViewById<EditText>(R.id.editTitle)
        val description = findViewById<EditText>(R.id.editDescription)
        valid.setOnClickListener({
            val resultIntent = Intent()
            val task= Task(id = UUID.randomUUID().toString(), title = title.getText().toString(), description = description.getText().toString())
            resultIntent.putExtra("task", task)
            setResult(RESULT_OK, resultIntent)
            finish()
        })
    }
}