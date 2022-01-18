package com.example.mvvmtodoapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtodoapp.MainActivity
import com.example.mvvmtodoapp.Models.TodoModel
import com.example.mvvmtodoapp.R
import com.example.mvvmtodoapp.databinding.MainItemsBinding

class MainAdapter(private val todos: ArrayList<TodoModel>, private val activity: MainActivity):RecyclerView.Adapter<MainAdapter.MainViewHolder>(){

    class MainViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding by lazy {
            MainItemsBinding.bind(view)
        }
        val cvMain = binding.cvMain
        val tvActivity = binding.tvActivity
        val tvNumber = binding.tvNumber

        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.main_items,parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = todos[position]

        holder.tvActivity.text = data.aktivitas
        holder.tvNumber.text = (position + 1).toString()
        holder.cvMain.setOnClickListener {
            activity.editTodos(
                TodoModel(
                    data.id,
                    data.aktivitas,
                    data.deskripsi,
                    data.prioritas
                )
            )
        }
        holder.cvMain.setOnLongClickListener {
            activity.deleteTodos( data.id.toString() )
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = todos.size

    fun setData( data: ArrayList<TodoModel> ){
        todos.clear()
        todos.addAll( data )
        notifyDataSetChanged()
    }
}