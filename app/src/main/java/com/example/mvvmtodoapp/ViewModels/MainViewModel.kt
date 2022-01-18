package com.example.mvvmtodoapp.ViewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtodoapp.Models.ResponseModel
import com.example.mvvmtodoapp.Models.TodoModel
import com.example.mvvmtodoapp.Repositories.MainRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel: ViewModel() {
    private var mainRepository: MainRepository

    private val _todos = MutableLiveData<ArrayList<TodoModel>>().apply {
        value = arrayListOf()
    }
    val todos: LiveData<ArrayList<TodoModel>>
    get() = _todos

    private val _response = MutableLiveData<ResponseModel>()
    val response: LiveData<ResponseModel>
    get() = _response

    init {
        mainRepository = MainRepository()
        loadTodos()
    }

    fun loadTodos() {
        viewModelScope.launch {
            try {
                _todos.postValue(mainRepository.getTodos())
            }catch (e: Exception){
                println("load data : ${e.message}")
            }
        }
    }

    fun addTodos(todo: TodoModel){
        viewModelScope.launch {
            try{
                val add = mainRepository.addTodos(todo)
                if(add != null){
                    _response.postValue(add!!)
                }
                loadTodos()
            }catch (e: Exception){
                println("add data : ${e.message}")
            }
        }
    }

    fun updateTodos(id: String,todo: TodoModel){
        viewModelScope.launch {
            try{
                val add = mainRepository.updateTodos(id, todo)
                if(add != null){
                    _response.postValue(add!!)
                }
                loadTodos()
            }catch (e: Exception){
                println("refresh data : ${e.message}")
            }
        }
    }

    fun deleteTodos(id: String){
        viewModelScope.launch {
            try{
                val add = mainRepository.deleteTodos(id)
                if(add != null){
                    _response.postValue(add!!)
                }
                loadTodos()
            }catch (e: Exception){
                println("delete data : ${e.message}")
            }
        }
    }
}