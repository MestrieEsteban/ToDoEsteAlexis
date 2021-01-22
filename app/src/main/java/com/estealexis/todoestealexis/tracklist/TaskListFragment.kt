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

class TaskListFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView?.layoutManager =  LinearLayoutManager(activity)
        recyclerView?.adapter =  TaskListAdapter(taskList)

        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }
    private val taskList = listOf("Task 1", "Task 2", "Task 3")

}


class TaskListAdapter(private val taskList: List<String>) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(taskTitle: String) {
            itemView.apply {
                val test = itemView.findViewById<TextView>(R.id.task_title)
                test.text = taskTitle
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
