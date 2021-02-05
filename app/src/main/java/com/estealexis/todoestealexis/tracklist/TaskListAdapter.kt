package com.estealexis.todoestealexis.tracklist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.estealexis.todoestealexis.R
import com.estealexis.todoestealexis.databinding.ItemTaskBinding

class TaskListAdapter(private val taskList: MutableList<Task>):
        ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TasksDiffCallback) {
        var onDeleteTask: ((Task) -> Unit)? = null
        var onEditTask: ((Task) -> Unit)? = null

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(task: Task) {
                    binding.taskTitle.text = task.title
                    if(task.description != ""){
                        binding.taskTitle.text = "${binding.taskTitle.text} \n ${task.description}"
                    }

                    val bb = itemView.findViewById<ImageButton>(R.id.imageButton)
                    bb.setOnClickListener {
                        onDeleteTask?.invoke(task)
                    }

                    val editButton = itemView.findViewById<ImageButton>(R.id.imageButton3)
                    editButton.setOnClickListener{
                        onEditTask?.invoke(task)
                }
            }
        }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TaskListAdapter.TaskViewHolder {
        val binding =
                ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    object TasksDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }

}
