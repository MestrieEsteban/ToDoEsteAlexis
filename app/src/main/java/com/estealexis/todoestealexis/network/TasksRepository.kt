
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.estealexis.todoestealexis.network.Api
import com.estealexis.todoestealexis.tracklist.Task

class TasksRepository {
    private val tasksWebService = Api.tasksWebService
    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> = _taskList

    suspend fun refresh() {
        val tasksResponse = tasksWebService.getTasks()
        if (tasksResponse.isSuccessful) {
            val fetchedTasks = tasksResponse.body()!!
            _taskList.value = fetchedTasks
        }
    }

    suspend fun updateTask(task: Task) {
        val tasksResponse = tasksWebService.updateTask(task, task.id)
        if (tasksResponse.isSuccessful) {
            val tasks = _taskList.value.orEmpty().toMutableList()
            val position = tasks.indexOfFirst { it.id == task.id }
            Log.d("TASK_EDIT position", position.toString())
            Log.d("TASK_EDIT ", task.toString())

            tasks[position] = task
            _taskList.value = tasks
        }
    }

    suspend fun addTask(task: Task) {
        val tasksResponse = tasksWebService.createTask(task)
        if (tasksResponse.isSuccessful) {
            val tasks = _taskList.value.orEmpty().toMutableList()
            _taskList.value = tasks
        }
    }

    suspend fun deleteTask(task: Task) {
        val tasksResponse = tasksWebService.deleteTask(task.id)
        if (tasksResponse.isSuccessful) {
            val tasks = _taskList.value.orEmpty().toMutableList()
            tasks.remove(task)
            _taskList.value = tasks
        }
    }
}