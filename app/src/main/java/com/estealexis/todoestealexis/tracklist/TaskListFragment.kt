package com.estealexis.todoestealexis.tracklist

import TasksRepository
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.network.Api
import com.estealexis.todoestealexis.task.TaskActivity
import com.estealexis.todoestealexis.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
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

    private val tasksRepository = TasksRepository()


    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            val userInfo = Api.userService.getInfo().body()!!
            val infoUser = view?.findViewById<TextView>(R.id.userInfoText)
            infoUser?.text = "${userInfo.firstName} ${userInfo.lastName}"
            tasksRepository.refresh()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager =  LinearLayoutManager(activity)
        recyclerView.adapter =  TaskListAdapter(taskList)

        tasksRepository.taskList.observe(viewLifecycleOwner, Observer {
            recyclerView.adapter =  TaskListAdapter(it as MutableList<Task>)

            (recyclerView.adapter as TaskListAdapter).onDeleteTask = { task ->
                lifecycleScope.launch {
                    tasksRepository.deleteTask(task)
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
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val task = data?.getSerializableExtra(TaskActivity.TASK_KEY) as Task
        val isUpdate = data?.getSerializableExtra("isUpdate")
        lifecycleScope.launch {
            if(isUpdate === null){ tasksRepository.addTask(task) } else { tasksRepository.updateTask(task) }
        }
    }

    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3", description = "description 3")
    )
}


class TaskListAdapter(private val taskList: MutableList<Task> ) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    var onDeleteTask: ((Task) -> Unit)? = null
    var onEditTask: ((Task) -> Unit)? = null

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            itemView.apply {
                val test = itemView.findViewById<TextView>(R.id.task_title)
                test.text = task.title
                if(task.description != ""){
                    test.text = "${test.text} \n ${task.description}"
                }

                var bb = itemView.findViewById<ImageButton>(R.id.imageButton)
                bb.setOnClickListener {
                    println("dqsqsdqsdqsd")
                    onDeleteTask?.invoke(task)
                }

                var editButton = itemView.findViewById<ImageButton>(R.id.imageButton3)
                editButton.setOnClickListener{
                    onEditTask?.invoke(task)
                }
            }

        }

    }



    override fun getItemCount(): Int {
        return this.taskList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListAdapter.TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }


}
