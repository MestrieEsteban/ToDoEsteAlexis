package com.estealexis.todoestealexis.tasklist

import TasksRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    private val repository = TasksRepository()
    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList


    fun loadTasks() {
        viewModelScope.launch {
            _taskList.value = repository.loadTasks()
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            val tasksResponse = repository.updateTask(task)
            if (tasksResponse) {
                val tasks = _taskList.value.orEmpty().toMutableList()
                val position = tasks.indexOfFirst { it.id == task.id }
                tasks[position] = task
                _taskList.value = tasks
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            val tasksResponse = repository.createTask(task)
            if (tasksResponse) {
                val tasks = _taskList.value.orEmpty().toMutableList()
                _taskList.value = tasks
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            val tasksResponse = repository.deleteTask(task)
            if (tasksResponse) {
                val tasks = _taskList.value.orEmpty().toMutableList()
                tasks.remove(task)
                _taskList.value = tasks
            }
        }
    }
}