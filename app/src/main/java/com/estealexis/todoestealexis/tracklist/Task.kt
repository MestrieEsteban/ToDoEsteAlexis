package com.estealexis.todoestealexis.tracklist

import java.io.Serializable

class Task(id: String,title: String, description: String = "") : Serializable {

    val id: String = id
    val title: String = title
    val description: String = description

    fun getTaskTitle(): String {
        return this.title
    }

    fun getTaskId(): String {
        return this.id
    }
    fun getTaskDescription(): String {
        return this.description
    }

}