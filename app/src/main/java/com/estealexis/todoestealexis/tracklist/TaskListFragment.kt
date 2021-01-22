package com.estealexis.todoestealexis.tracklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.estealexis.todoestealexis.R
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

        val aaa = view.findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        aaa.setOnClickListener(){
            taskList.add(Task(id = UUID.randomUUID().toString(), title = "Task ${taskList.size + 1}"))
            recyclerView.adapter =  TaskListAdapter(taskList)

        }


    }
    private val taskList = mutableListOf(
        Task(id = "id_1", title = "Task 1", description = "description 1"),
        Task(id = "id_2", title = "Task 2"),
        Task(id = "id_3", title = "Task 3", description = "description 3")
    )
}


class TaskListAdapter(private val taskList: List<Task>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: Task) {
            itemView.apply {
                val test = itemView.findViewById<TextView>(R.id.task_title)
                test.text = taskTitle.getTaskTilte()
                if(taskTitle.getTaskDescription() != ""){
                    test.text = "${test.text} \n ${taskTitle.getTaskDescription()}"
                }
                //afficher les données et attacher les listeners aux différentes vues de notre [itemView]
            }
        }
    }


    override fun getItemCount(): Int {
        return this.taskList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }


}
