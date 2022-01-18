package com.example.mvvmtodoapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmtodoapp.Adapters.MainAdapter
import com.example.mvvmtodoapp.Models.TodoModel
import com.example.mvvmtodoapp.ViewModels.MainViewModel
import com.example.mvvmtodoapp.databinding.ActivityMainBinding
import com.example.mvvmtodoapp.databinding.MainAddtodosDialogBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.nsvMain.isSmoothScrollingEnabled = true
        initRv()
        getTodos()
        binding.fab.setOnClickListener {
            addTodos()
        }
    }

    fun getTodos(){
        mainViewModel.todos.observe(this, Observer {
            setDataToAdapter(it)
            binding.srlMain.setOnRefreshListener {
                Handler().postDelayed({
                    mainViewModel.loadTodos()
                    binding.rvMain.visibility = View.VISIBLE
                    binding.srlMain.isRefreshing = false
                }, 2000)
            }
        })

        mainViewModel.response.observe(this, Observer {
            if(it.success){
                Toast.makeText(this, ""+it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun addTodos(){
        val dialogView = layoutInflater.inflate(R.layout.main_addtodos_dialog,null)
        val dialogBinding: MainAddtodosDialogBinding = MainAddtodosDialogBinding.bind(dialogView)
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(dialogView)
        alertDialog.setPositiveButton("Add", DialogInterface.OnClickListener { dialogInterface, i ->
            val todo = dialogBinding.etTodos.text.toString()
            val desc = dialogBinding.etDeskripsi.text.toString()
            mainViewModel.addTodos(TodoModel(null,todo,desc,1))
        })
        alertDialog.create().show()
    }

    fun editTodos(todo: TodoModel){
        val dialogView = layoutInflater.inflate(R.layout.main_addtodos_dialog,null)
        val dialogBinding: MainAddtodosDialogBinding = MainAddtodosDialogBinding.bind(dialogView)

        dialogBinding.etTodos.setText( todo.aktivitas )
        dialogBinding.etDeskripsi.setText( todo.deskripsi )
        val id  = todo.id.toString()

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setView(dialogView)
        alertDialog.setPositiveButton("Update", DialogInterface.OnClickListener { dialogInterface, i ->
            val todo = dialogBinding.etTodos.text.toString()
            val desc = dialogBinding.etDeskripsi.text.toString()
            mainViewModel.updateTodos(
                id,
                TodoModel(
                    null,
                    todo,
                    desc,
                    1
                )
            )
        })
        alertDialog.create().show()
    }

    fun deleteTodos(id: String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setMessage("Hapus?")
        alertDialog.setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
            mainViewModel.deleteTodos( id )
        })
        alertDialog.create().show()
    }

    private fun initRv(){
        mainAdapter = MainAdapter(arrayListOf(),this)
        with(binding.rvMain){
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }

    private fun setDataToAdapter(data: ArrayList<TodoModel>){
        mainAdapter.setData(data)
    }
}