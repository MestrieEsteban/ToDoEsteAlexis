package com.estealexis.todoestealexis.task

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.databinding.TaskFragmentBinding
import com.estealexis.todoestealexis.tasklist.Task
import java.util.*

class TaskFragment: Fragment(){
    private lateinit var binding: TaskFragmentBinding

    companion object {
        const val ADD_TASK_REQUEST_CODE = 666
        const val TASK_KEY = "task"
        const val TASK_KEY2 = "editedTask"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View {
        binding = TaskFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editedTask = arguments?.getSerializable(TASK_KEY2) as? Task
        val myTitle = if (editedTask?.title == "") "" else editedTask?.title
        val myDescription = if (editedTask?.description == "") "" else editedTask?.description
        if (editedTask?.title != null ) binding.button.text="UPDATE" else binding.button.text="ADD"
        binding.editTitle.setText(myTitle)
        binding.editDescription.setText(myDescription)
        binding.button.setOnClickListener {
           // val resultIntent = Intent()
            val taskId = if (editedTask?.id == "") UUID.randomUUID().toString() else editedTask?.id
            val task = Task(id = "$taskId", title = "${binding.editTitle.text}", description = "${binding.editDescription.text}")
            val isUpdate = if (editedTask?.title == "") "" else editedTask?.title
            val bundle = bundleOf( "task" to task,"isUpdate" to isUpdate)
            view.findNavController().navigate(R.id.taskFragment, bundle.apply {
                putInt("fragment:resultCode", RESULT_OK)
            })            //setResult(RESULT_OK, resultIntent)
            //finish()
        }
    }

}
