package com.brunonavarro.smiledu.ui.detailTask

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brunonavarro.smiledu.R
import com.brunonavarro.smiledu.data.entity.Comment
import com.brunonavarro.smiledu.data.entity.Task
import com.brunonavarro.smiledu.databinding.ActivityDetailTaskBinding
import com.brunonavarro.smiledu.ui.detailTask.adapter.CommentAdapter
import com.brunonavarro.smiledu.ui.detailTask.adapter.CommentListener
import com.brunonavarro.smiledu.util.Constants
import com.brunonavarro.smiledu.util.datePicker.DatePickerFragment
import com.brunonavarro.smiledu.util.validDateInput
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class DetailTaskActivity : AppCompatActivity() , KodeinAware,
        DetailTaskListener, CommentListener {

    override val kodein by kodein()
    lateinit var mainViewModel: DetailTaskViewModel
    private val factory: DetailTaskViewModelFactory by instance()

    lateinit var binding: ActivityDetailTaskBinding

    var commentAdapterView =  MutableLiveData<CommentAdapter>()

    var taskId: Int? = null

    lateinit var currentTask: Task
    var isEditComment = MutableLiveData<Boolean>(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail_task)
        mainViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_task)
        binding.lifecycleOwner = this
        mainViewModel.listener = this

        setSupportActionBar(findViewById(R.id.topAppBarDetail))

        if (intent.extras != null){
            taskId = intent.getIntExtra("id", 0)
            mainViewModel.taskId = taskId
            mainViewModel.getTask()
            mainViewModel.getComments()

            configAdapter()
            observables()
            clickable()
        }
    }

    private fun clickable() {
        addActionDoneListener(binding.commentEditText)
    }

    private fun addActionDoneListener(editText: EditText) {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.commentEditText.text.isNotEmpty()) {
                    Toast.makeText(this, "Send Comment", Toast.LENGTH_LONG).show()
                    val comment = Comment()
                    comment.message = binding.commentEditText.text.toString().trim()
                    comment.taskId = taskId
                    comment.id = commentAdapterView.value?.itemList!!.size + 1

                    if (!isEditComment.value!!) {
                        mainViewModel.addComment(comment)
                        commentAdapterView.value?.itemList?.add(comment)
                        commentAdapterView.value?.notifyDataSetChanged()
                        updateCountComment(commentAdapterView.value?.itemList!!.size)
                        isEditComment.value = false
                    }else{
                        mainViewModel.comments.value?.firstOrNull { it.id == comment.id }?.let {
                            it.id = comment.id
                            it.message = comment.message
                            it.taskId = comment.taskId
                        }
                        updateCountComment(commentAdapterView.value?.itemList!!.size)
                        isEditComment.value = false
                    }
                    binding.commentEditText.setText("")
                }
            }
            true
        }
    }

    private fun observables() {
        mainViewModel.task.observeForever {
            if (it != null){
                currentTask = it
                showData(it)
            }
        }

        mainViewModel.comments.observeForever {
            if (!it.isNullOrEmpty()){
                commentAdapterView.value?.itemList = it
                commentAdapterView.value?.notifyDataSetChanged()
            }
            updateCountComment(it.size)
        }
    }

    private fun updateCountComment(size: Int) {
        binding.commentLabel.text = getString(R.string.comment_label_value, size.toString())
    }

    fun configAdapter(){
        binding.commentRecyclerView.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(this@DetailTaskActivity, LinearLayoutManager.VERTICAL, false)
        }
        commentAdapterView.value = CommentAdapter(this, this)
        binding.commentRecyclerView.adapter = commentAdapterView.value
        commentAdapterView.value?.notifyDataSetChanged()
    }

    private fun showData(it: Task) {
        binding.titleToolBar.text = it.title
        binding.activityCreate.text = getString(R.string.activity_create_value, it.createDate)
        binding.activityFinish.text = getString(R.string.activity_finish_value, it.finishDate)
        binding.activityBody.text = it.body.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_detail_task, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId){
            R.id.action_remove->{
                showDialogDeleteTask()
                true
            }
            R.id.action_edit ->{
                showDialogUpdateTask()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDialogUpdateTask(){
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

        titleTask.setText(currentTask.title.toString())
        bodyTask.setText(currentTask.body.toString())
        finishTask.setText(currentTask.finishDate.toString())

        finishTask.setOnClickListener {
            showPicketDate(finishTask)
        }

        dialogTitle.text = getString(R.string.dialog_title, getString(R.string.edit_task))

        cancelButton.setOnClickListener { dialogTask?.dismiss() }

        acceptButton.setOnClickListener {
            val task = Task()
            if (validForm(titleTask, bodyTask, finishTask)){
                task.title = titleTask.text.toString().trim()
                task.body = bodyTask.text.toString().trim()
                task.finishDate = finishTask.text.toString().trim()
                task.createDate = currentTask.createDate
                task.id = currentTask.id
                task.isComplete = currentTask.isComplete

                currentTask = task
                mainViewModel.task.value = currentTask
                mainViewModel.updateTask(task)
                dialogTask?.dismiss()
            }
        }

        builder.setView(showView)

        dialogTask = builder.create()
        dialogTask.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogTask.show()
    }

    fun showDialogDeleteTask(){
        var dialogTask: AlertDialog? = null
        val builder = AlertDialog.Builder(this)

        val showView: View =
            LayoutInflater.from(this).inflate(R.layout.view_dialog_delete_activity, null)


        val cancelButton: Button = showView.findViewById(R.id.cancelButton)
        val acceptButton: Button = showView.findViewById(R.id.acceptButton)
        val dialogTitle: TextView = showView.findViewById(R.id.dialogTitle)

        dialogTitle.text = getString(R.string.dialog_delete_title_value, mainViewModel.task.value?.title)

        cancelButton.setOnClickListener { dialogTask?.dismiss() }

        acceptButton.setOnClickListener {
            mainViewModel.deleteTask(currentTask)
            dialogTask?.dismiss()
            finish()
        }

        builder.setView(showView)

        dialogTask = builder.create()
        dialogTask.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogTask.show()
    }

    private fun showPicketDate(finishTask: EditText) {
        val datePicker = DatePickerFragment{day, month, year -> onDateSelected(day, month, year, finishTask) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun onDateSelected(day:Int, month: Int, year:Int, finishTask: EditText){
        finishTask.setText(getString(R.string.format_date_finish_task,
            if (day < 10) "0$day" else day.toString(),
            if (month+1 < 10 ) "0${month+1}" else (month+1).toString(),
            year.toString() ))
    }

    fun validForm(titleTask: EditText, bodyTask: EditText, finishTask: EditText): Boolean{
        if (titleTask.text.isNullOrEmpty()){
            titleTask.error = getString(R.string.error_empty_field)
            return false
        }else if (bodyTask.text.isNullOrEmpty()){
            bodyTask.error = getString(R.string.error_empty_field)
            return false
        }else if (finishTask.text.isNullOrEmpty() && !validDateInput(finishTask.text.toString())){
            finishTask.error = getString(R.string.error_empty_field)
            return false
        }
        return true
    }

    fun showDialogDeleteComment(comment: Comment){
        var dialogTask: AlertDialog? = null
        val builder = AlertDialog.Builder(this)

        val showView: View =
                LayoutInflater.from(this).inflate(R.layout.view_dialog_delete_activity, null)


        val cancelButton: Button = showView.findViewById(R.id.cancelButton)
        val acceptButton: Button = showView.findViewById(R.id.acceptButton)
        val dialogTitle: TextView = showView.findViewById(R.id.dialogTitle)
        val dialogMessage: TextView = showView.findViewById(R.id.dialogMessage)

        dialogTitle.text = getString(R.string.dialog_delete_title_value, comment.message)
        dialogMessage.text = getString(R.string.dialog_delete_comment_message)

        cancelButton.setOnClickListener { dialogTask?.dismiss() }

        acceptButton.setOnClickListener {
            mainViewModel.comments.value?.remove(comment)
            mainViewModel.deleteComment(comment)
            dialogTask?.dismiss()
            finish()
        }

        builder.setView(showView)

        dialogTask = builder.create()
        dialogTask.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogTask.show()
    }

    override fun onClickEdit(comment: Comment) {
        isEditComment.value = true
        binding.commentEditText.setText(comment.message)
        binding.commentEditText.selectAll()
        binding.commentEditText.requestFocus()
    }

    override fun onClickDelete(comment: Comment) {
        showDialogDeleteComment(comment)
    }

    override fun showProgressBar(isShow: Boolean) {
        if(isShow){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun createTaskSuccess() {
        errorMessage(null, getString(R.string.create_comment_success))
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
            Constants.ERROR_EMPTY_LIST ->{
                Toast.makeText(this, getString(R.string.error_empty_list), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}