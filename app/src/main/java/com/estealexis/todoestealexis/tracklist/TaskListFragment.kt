package com.estealexis.todoestealexis.tracklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.estealexis.todoestealexis.databinding.FragmentTaskListBinding
import com.estealexis.todoestealexis.network.Api
import com.estealexis.todoestealexis.task.TaskActivity
import com.estealexis.todoestealexis.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.estealexis.todoestealexis.userinfo.UserInfoActivity
import com.estealexis.todoestealexis.userinfo.UserInfoViewModel
import kotlinx.coroutines.launch


class TaskListFragment : Fragment(){
    private val viewModel: TaskListViewModel by viewModels()
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
            val userInfo = Api.userWebService.getInfo().body()
            binding.userInfoText?.text = "${userInfo?.firstName} ${userInfo?.lastName}"
            viewModel.loadTasks()
            binding.profileImage?.load(userInfo?.avatar)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =  LinearLayoutManager(activity)
        binding.recyclerView.adapter =  taskListAdapter

        viewModel.taskList.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.adapter =  TaskListAdapter(it as MutableList<Task>)

            (binding.recyclerView.adapter as TaskListAdapter).onDeleteTask = { task ->
                lifecycleScope.launch {
                    viewModel.deleteTask(task)
                }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }

            (binding.recyclerView.adapter as TaskListAdapter).onEditTask = { task ->
                val intent = Intent(activity, TaskActivity::class.java)
                intent.putExtra("editedTask", task)
                startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
            }
        })

        binding.floatingActionButton2.setOnClickListener(){
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        binding.profileImage.setOnClickListener({
            val intent = Intent(activity, UserInfoActivity::class.java)
            startActivity(intent)
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task = data?.getSerializableExtra(TaskActivity.TASK_KEY) as Task
        val isUpdate = data?.getSerializableExtra("isUpdate")
        lifecycleScope.launch {
            if(isUpdate === null){ viewModel.addTask(task) } else { viewModel.updateTask(task) }
        }
    }

    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3", description = "description 3")
    )
}