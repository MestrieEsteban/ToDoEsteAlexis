package com.estealexis.todoestealexis.tracklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.network.Api
import com.estealexis.todoestealexis.task.TaskActivity
import com.estealexis.todoestealexis.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.estealexis.todoestealexis.userinfo.UserInfoActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch



class TaskListFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    private val viewModel: TaskListViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val userInfo = Api.userService.getInfo().body()!!
            val infoUser = view?.findViewById<TextView>(R.id.userInfoText)
            val profil = view?.findViewById<ImageView>(R.id.profile_image)
            infoUser?.text = "${userInfo.firstName} ${userInfo.lastName}"
            viewModel.loadTasks()
            profil?.load(userInfo.avatar)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val profil = view?.findViewById<ImageView>(R.id.profile_image)

        recyclerView.layoutManager =  LinearLayoutManager(activity)
        recyclerView.adapter =  TaskListAdapter(taskList)

        viewModel.taskList.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter =  TaskListAdapter(it as MutableList<Task>)

            (recyclerView.adapter as TaskListAdapter).onDeleteTask = { task ->
                lifecycleScope.launch {
                    viewModel.deleteTask(task)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            (recyclerView.adapter as TaskListAdapter).onEditTask = { task ->
                val intent = Intent(activity, TaskActivity::class.java)
                intent.putExtra("editedTask", task)
                startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
            }
        })

        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        fab.setOnClickListener(){
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        profil.setOnClickListener({
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