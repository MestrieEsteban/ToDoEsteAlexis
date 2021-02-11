package com.estealexis.todoestealexis.task

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.estealexis.todoestealexis.databinding.AssTaskBinding
import com.estealexis.todoestealexis.tasklist.Task
import java.util.*

class TaskFragment: FragmentActivity(){
    private lateinit var binding: AssTaskBinding

    companion object {
        const val ADD_TASK_REQUEST_CODE = 666
        const val TASK_KEY = "task"
        const val TASK_KEY2 = "editedTask"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AssTaskBinding.inflate(layoutInflater);
        setContentView(binding.root)
        val editedTask = intent.getSerializableExtra(TASK_KEY2) as? Task
        val myTitle = if (editedTask?.title == "") "" else editedTask?.title
        val myDescription = if (editedTask?.description == "") "" else editedTask?.description
        if (editedTask?.title != null ) binding.button.text="UPDATE" else binding.button.text="ADD"
        binding.editTitle.setText(myTitle)
        binding.editDescription.setText(myDescription)
        binding.button.setOnClickListener {
            val resultIntent = Intent()
            val taskId = if (editedTask?.id == "") UUID.randomUUID().toString() else editedTask?.id
            val task = Task(id = "$taskId", title = "${binding.editTitle.text}", description = "${binding.editDescription.text}")
            val isUpdate = if (editedTask?.title == "") "" else editedTask?.title
            resultIntent.putExtra("task", task)
            resultIntent.putExtra("isUpdate", isUpdate)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
