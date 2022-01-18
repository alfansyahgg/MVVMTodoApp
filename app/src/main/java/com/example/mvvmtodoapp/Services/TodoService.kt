package com.example.mvvmtodoapp.Services

import com.example.mvvmtodoapp.Models.ResponseModel
import com.example.mvvmtodoapp.Models.TodoModel
import retrofit2.Response
import retrofit2.http.*

interface TodoService {

    @GET("todo")
    suspend fun getTodos(): Response<ArrayList<TodoModel>>

    @POST("todo")
    suspend fun addTodos(@Body todo: TodoModel): Response<ResponseModel>

    @PUT("todo/{id}")
    suspend fun updateTodos(
        @Path("id") id: String,
        @Body todo: TodoModel
    ): Response<ResponseModel>

    @DELETE("todo/{id}")
    suspend fun deleteTodos(
        @Path("id") id: String
    ): Response<ResponseModel>

}