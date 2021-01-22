package com.estealexis.todoestealexis.tracklist

class Task(id: String,title: String, description: String = "") {

    val id: String = id
    val title: String = title
    val description: String = description

    fun getTaskTilte(): String {
        return this.title
    }

    fun getTaskId(): String {
        return this.id
    }
    fun getTaskDescription(): String {
        return this.description
    }

}