package com.estealexis.todoestealexis.tasklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.databinding.FragmentTaskListBinding
import com.estealexis.todoestealexis.task.TaskFragment
import com.estealexis.todoestealexis.task.TaskFragment.Companion.ADD_TASK_REQUEST_CODE
import com.estealexis.todoestealexis.userinfo.UserInfoActivity
import com.estealexis.todoestealexis.userinfo.UserInfoViewModel
import kotlinx.coroutines.launch
import java.sql.ResultSet


class TaskListFragment : Fragment(){
    private val taskviewModel: TaskListViewModel by viewModels()
    private val userViewModel: UserInfoViewModel by viewModels()
    private lateinit var binding: FragmentTaskListBinding
    private lateinit var taskListAdapter: TaskListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            userViewModel.loadInfo()
            taskviewModel.loadTasks()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskListAdapter = TaskListAdapter()
        binding.recyclerView.layoutManager =  LinearLayoutManager(activity)
        binding.recyclerView.adapter =  taskListAdapter

        taskviewModel.taskList.observe(viewLifecycleOwner, Observer {
            taskListAdapter.submitList(it)
            binding.recyclerView.adapter =  taskListAdapter
            if(arguments?.getSerializable("isUpdate") != null)
            {
                val task = arguments?.getSerializable(TaskFragment.TASK_KEY) as Task
                val isUpdate = arguments?.getSerializable("isUpdate")
                lifecycleScope.launch {
                    if(isUpdate === "test"){ taskviewModel.addTask(task) } else { taskviewModel.updateTask(task) }
                }
                arguments?.remove("isUpdate")
            }
        })

        userViewModel.login_user.observe(viewLifecycleOwner, { userinfo ->
            binding.userInfoText.text = "${userinfo.firstName} ${userinfo.lastName}"
            binding.profileImage?.load(userinfo.avatar)
        })

        taskListAdapter.onDeleteTask = {
            taskviewModel.deleteTask(it)
        }

        taskListAdapter.onEditTask = {
            val bundle = bundleOf("editedTask" to it)
            view.findNavController().navigate(R.id.taskListFragment, bundle)
        }

        binding.floatingActionButton2.setOnClickListener(){
            view.findNavController().navigate(R.id.taskFragment)
        }

        binding.profileImage.setOnClickListener({
            val intent = Intent(activity, UserInfoActivity::class.java)
            startActivity(intent)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task = data?.getSerializableExtra(TaskFragment.TASK_KEY) as Task
        val isUpdate = data?.getSerializableExtra("isUpdate")
        lifecycleScope.launch {
            if (isUpdate === null) {
                taskviewModel.addTask(task)
            } else {
                taskviewModel.updateTask(task)
            }
        }
    }

}
