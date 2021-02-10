
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.estealexis.todoestealexis.network.Api
import com.estealexis.todoestealexis.tasklist.Task

class TasksRepository {
    private val tasksWebService = Api.INSTANCE.tasksWebService
    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    suspend fun loadTasks(): List<Task>? {
        val response = tasksWebService.getTasks()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateTask(task: Task): Boolean {
        val response = tasksWebService.updateTask(task, task.id)
        return response.isSuccessful
    }

    suspend fun createTask(task: Task): Boolean{
        val response = tasksWebService.createTask(task)
        return response.isSuccessful
    }

    suspend fun deleteTask(task: Task): Boolean{
        val response = tasksWebService.deleteTask(task.id)
        return response.isSuccessful
    }

}