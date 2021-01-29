package com.estealexis.todoestealexis.task

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.tracklist.Task
import java.util.*

class TaskActivity: AppCompatActivity(){
    companion object {
        const val ADD_TASK_REQUEST_CODE = 666
        const val TASK_KEY = "task"
        const val TASK_KEY2 = "editedTask"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ass_task)
        val editedTask = intent.getSerializableExtra(TASK_KEY2) as? Task
        val title = findViewById<EditText>(R.id.editTitle)
        val description = findViewById<EditText>(R.id.editDescription)
        val myTitle = if (editedTask?.title == "") "" else editedTask?.title
        val myDescription = if (editedTask?.description == "") "" else editedTask?.description
        title.setText(myTitle)
        description.setText(myDescription)
        var valid = findViewById<Button>(R.id.button)
        valid.setOnClickListener {
            val resultIntent = Intent()
            val taskId = if (editedTask?.id == "") UUID.randomUUID().toString() else editedTask?.id
            val task = Task(id = "$taskId", title = "${title.text}", description = "${description.text}")
            resultIntent.putExtra("task", task)
            setResult(RESULT_OK, resultIntent)
            finish()
        }


    }
}