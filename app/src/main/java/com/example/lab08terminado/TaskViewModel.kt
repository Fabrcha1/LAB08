package com.example.lab08terminado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class TaskViewModel(private val dao: TaskDao) : ViewModel() {


    // Estado para la lista de tareas
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks


    init {
        // Al inicializar, cargamos las tareas de la base de datos
        viewModelScope.launch {
            _tasks.value = dao.getAllTasks()
        }
    }


    // Función para añadir una nueva tarea
    fun addTask(description: String) {
        val newTask = Task(description = description)
        viewModelScope.launch {
            dao.insertTask(newTask)
            _tasks.value = dao.getAllTasks() // Recargamos la lista
        }
    }


    // Función para alternar el estado de completado de una tarea
    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            dao.updateTask(updatedTask)
            _tasks.value = dao.getAllTasks() // Recargamos la lista
        }
    }


    // Función para eliminar todas las tareas
    fun deleteAllTasks() {
        viewModelScope.launch {
            dao.deleteAllTasks()
            _tasks.value = emptyList() // Vaciamos la lista en el estado
        }
    }

    fun editTask(task: Task, newDescription: String) {
        viewModelScope.launch {
            val updatedTask = task.copy(description = newDescription)
            dao.updateTask(updatedTask)
            _tasks.value = dao.getAllTasks() // Recargamos la lista de tareas
        }
    }

    // Función para eliminar una tarea individual
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.deleteTask(task)
            _tasks.value = dao.getAllTasks() // Recargamos la lista
        }
    }

    // Función para buscar tareas por descripción
    fun searchTasks(query: String) {
        viewModelScope.launch {
            _tasks.value = dao.searchTasks("%$query%")
        }
    }

    fun getCompletedTasks() {
        TODO("Not yet implemented")
    }

    fun getPendingTasks() {
        TODO("Not yet implemented")
    }

    fun getAllTasks() {
        TODO("Not yet implemented")
    }
}
