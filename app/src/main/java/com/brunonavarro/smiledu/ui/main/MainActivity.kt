package com.brunonavarro.smiledu.ui.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brunonavarro.smiledu.R
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.databinding.ActivityMainBinding
import com.brunonavarro.smiledu.ui.main.adapter.TaskAdapter
import com.brunonavarro.smiledu.ui.main.adapter.TaskListener
import com.brunonavarro.smiledu.util.Constants
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() , KodeinAware, MainListener, TaskListener {

    override val kodein by kodein()
    lateinit var mainViewModel: MainViewModel
    private val factory: MainViewModelFactory by instance()

    lateinit var binding: ActivityMainBinding

    var taskAdapterView =  MutableLiveData<TaskAdapter>()
    var isCompleteFilter = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        mainViewModel.mainListener = this

        setSupportActionBar(findViewById(R.id.topAppBar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            showDialogAddTask()
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        configAdapter()
        observable()
    }

    fun observable(){
        mainViewModel.taskList.observeForever {
            if (!it.isNullOrEmpty()){
                it.sortByDescending { it.id }
                taskAdapterView.value?.itemList = it
                taskAdapterView.value?.notifyDataSetChanged()
            }
        }
    }

    fun configAdapter(){
        binding.taskRecyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
        taskAdapterView.value = TaskAdapter(this)
        binding.taskRecyclerView.adapter = taskAdapterView.value
        taskAdapterView.value?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId){
            R.id.action_filter->{
                item.icon = if (isCompleteFilter)
                    ContextCompat.getDrawable(this, R.drawable.ic_is_task_complete)
                else
                    ContextCompat.getDrawable(this, R.drawable.ic_visibility_off_24)
                filter()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun filter(){
        val itemsFilter = mutableListOf<Task>()
        itemsFilter.clear()
        mainViewModel.taskList.value?.forEach {
            if (!isCompleteFilter) {
                if (it.isComplete != null && it.isComplete!!) {
                    itemsFilter.add(it)
                }
            }else{
                if (it.isComplete != null && !it.isComplete!!) {
                    itemsFilter.add(it)
                }
            }
        }
        taskAdapterView.value?.itemList = itemsFilter
        taskAdapterView.value?.itemList?.sortByDescending { it.id }
        taskAdapterView.value?.notifyDataSetChanged()
        isCompleteFilter = true
    }

    fun showDialogAddTask(){
        var dialogTask: AlertDialog? = null
        val builder = AlertDialog.Builder(this)

        val showView: View =
            LayoutInflater.from(this).inflate(R.layout.view_dialog_activity, null)

        val cancelButton: Button = showView.findViewById(R.id.cancelButton)
        val acceptButton: Button = showView.findViewById(R.id.acceptButton)
        val titleTask: EditText = showView.findViewById(R.id.activityTitle)
        val bodyTask: EditText = showView.findViewById(R.id.activityBody)
        val finishTask: EditText = showView.findViewById(R.id.activityFinish)
        val dialogTitle: TextView = showView.findViewById(R.id.dialogTitle)

        dialogTitle.text = getString(R.string.dialog_title, getString(R.string.new_task))

        cancelButton.setOnClickListener { dialogTask?.dismiss() }

        acceptButton.setOnClickListener {
            val task = Task()
            if (validForm(titleTask, bodyTask, finishTask)){
                val formatDate = SimpleDateFormat("dd/mm/yyyy", Locale.getDefault())

                task.title = titleTask.text.toString().trim()
                task.body = bodyTask.text.toString().trim()
                task.finishDate = finishTask.text.toString().trim()
                task.createDate = formatDate.format(Date()).toString()
                task.id = taskAdapterView.value?.itemList!!.size + 1

                mainViewModel.addTask(task)
                taskAdapterView.value?.itemList?.add(task)
                val items = taskAdapterView.value?.itemList
                items?.sortByDescending { it.id }
                taskAdapterView.value?.itemList = items!!.toMutableList()
                taskAdapterView.value?.notifyDataSetChanged()

                dialogTask?.dismiss()
            }
        }

        builder.setView(showView)

        dialogTask = builder.create()
        dialogTask.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogTask.show()
    }

    fun validForm(titleTask: EditText, bodyTask: EditText, finishTask: EditText): Boolean{
        if (titleTask.text.isNullOrEmpty()){
            titleTask.error = getString(R.string.error_empty_field)
            return false
        }else if (bodyTask.text.isNullOrEmpty()){
            bodyTask.error = getString(R.string.error_empty_field)
            return false
        }else if (finishTask.text.isNullOrEmpty()){
            finishTask.error = getString(R.string.error_empty_field)
            return false
        }
        return true
    }

    override fun onIsCompleteTask(task: Task, isComplete: Boolean) {
        task.isComplete = isComplete
        mainViewModel.updateTask(task)
    }

    override fun showProgressBar(isShow: Boolean) {
        if(isShow){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun createTaskSuccess() {
        errorMessage(null, getString(R.string.create_task_success))
    }

    override fun errorMessage(codeError: Int?, message: String?) {
        when(codeError){
            Constants.ERROR_GET_TASK_LIST -> {
                Toast.makeText(this, getString(R.string.error_get_task_list), Toast.LENGTH_SHORT).show()
            }
            Constants.ERROR_GET_TASK ->{
                Toast.makeText(this, getString(R.string.error_get_task), Toast.LENGTH_SHORT).show()
            }
            Constants.ERROR_DELETE_TASK ->{
                Toast.makeText(this, getString(R.string.error_delete_task), Toast.LENGTH_SHORT).show()
            }
            Constants.ERROR_UPDATE_TASK ->{
                Toast.makeText(this, getString(R.string.error_update_task), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}