package com.estealexis.todoestealexis.tracklist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.estealexis.todoestealexis.R

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

                    val bb = itemView.findViewById<ImageButton>(R.id.imageButton)
                    bb.setOnClickListener {
                        println("dqsqsdqsdqsd")
                        onDeleteTask?.invoke(task)
                    }

                    val editButton = itemView.findViewById<ImageButton>(R.id.imageButton3)
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
