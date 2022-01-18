package com.example.mvvmtodoapp.Repositories

import com.example.mvvmtodoapp.Models.ResponseModel
import com.example.mvvmtodoapp.Models.TodoModel
import com.example.mvvmtodoapp.Services.TodoClient

class MainRepository {

    suspend fun getTodos(): ArrayList<TodoModel>?{
        val result = TodoClient.todoService.getTodos()
        if( result != null ){
            return result.body()
        }

        return null
    }

    suspend fun addTodos(todo: TodoModel): ResponseModel?{
        val result = TodoClient.todoService.addTodos(todo)
        if( result != null ){
            return result.body()
        }
        return null
    }

    suspend fun updateTodos(id: String, todo: TodoModel): ResponseModel?{
        val result = TodoClient.todoService.updateTodos(id, todo)
        if( result != null ){
            return result.body()
        }
        return null
    }

    suspend fun deleteTodos(id: String): ResponseModel?{
        val result = TodoClient.todoService.deleteTodos(id)
        if( result != null ){
            return result.body()
        }
        return null
    }

}