package com.estealexis.todoestealexis.tracklist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.task.TaskActivity
import com.estealexis.todoestealexis.task.TaskActivity.Companion.ADD_TASK_REQUEST_CODE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

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
            print(task.getTaskTilte())
            taskList.remove(task)
            recyclerView.adapter?.notifyDataSetChanged()

        }

        val aaa = view.findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        aaa.setOnClickListener(){
            val intent = Intent(activity, TaskActivity::class.java)
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        taskList.add(Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}"))
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.adapter?.notifyDataSetChanged()
    }

    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3", description = "description 3")
    )
}


class TaskListAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    var onDeleteTask: ((Task) -> Unit)? = null

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: Task) {
            itemView.apply {
                val test = itemView.findViewById<TextView>(R.id.task_title)
                test.text = taskTitle.getTaskTilte()
                if(taskTitle.getTaskDescription() != ""){
                    test.text = "${test.text} \n ${taskTitle.getTaskDescription()}"
                }
                //afficher les données et attacher les listeners aux différentes vues de notre [itemView]
                var bb = itemView.findViewById<ImageButton>(R.id.imageButton)
                bb.setOnClickListener {
                    onDeleteTask?.invoke(taskTitle)
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
