package com.estealexis.todoestealexis.tracklist

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.task.TaskActivity
import com.estealexis.todoestealexis.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager =  LinearLayoutManager(activity)
        recyclerView.adapter =  TaskListAdapter(taskList)


        (recyclerView.adapter as TaskListAdapter).onDeleteTask = { task ->
            taskList.remove(task)
            recyclerView.adapter?.notifyDataSetChanged()

        }
        (recyclerView.adapter as TaskListAdapter).onEditTask = { task ->
            val intent = Intent(activity, TaskActivity::class.java)
            intent.putExtra("editedTask", task)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }

        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        fab.setOnClickListener(){
            startForResult.launch(Intent(activity, TaskActivity::class.java))
        }
    }
    private val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        val task = result.data?.getSerializableExtra(TaskActivity.TASK_KEY) as Task
                        val position = taskList.indexOfFirst { it.id == task.id}
                        if(position != -1){
                            taskList[position] = task
                        }else{
                            taskList.add(task)
                        }
                        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
                        recyclerView?.adapter?.notifyDataSetChanged()
                    }
                    RESULT_CANCELED -> {

                    } else -> {
                } }
            }


    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3", description = "description 3")
    )
}


class TaskListAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    var onDeleteTask: ((Task) -> Unit)? = null
    var onEditTask: ((Task) -> Unit)? = null

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: Task) {
            itemView.apply {
                val test = itemView.findViewById<TextView>(R.id.task_title)
                test.text = taskTitle.getTaskTitle()
                if(taskTitle.getTaskDescription() != ""){
                    test.text = "${test.text} \n ${taskTitle.getTaskDescription()}"
                }
                //afficher les données et attacher les listeners aux différentes vues de notre [itemView]
                var bb = itemView.findViewById<ImageButton>(R.id.imageButton)
                bb.setOnClickListener {
                    onDeleteTask?.invoke(taskTitle)
                }

                var editButton = itemView.findViewById<ImageButton>(R.id.imageButton3)
                editButton.setOnClickListener{
                    onEditTask?.invoke(taskTitle)
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
