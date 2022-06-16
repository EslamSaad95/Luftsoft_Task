package com.example.luftsofttask.presentation.mainActivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.luftsofttask.R
import com.example.luftsofttask.databinding.ActivityMainBinding
import com.example.luftsofttask.presentation.extension.linearLayoutManager
import com.example.luftsofttask.presentation.mainActivity.adapter.TaskRvAdapter
import com.example.luftsofttask.presentation.mainActivity.adapter.TasksLogsRvAdapter
import com.example.luftsofttask.presentation.recycleview.GridSpacingItemDecoration

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tasksRvAdapter by lazy { TaskRvAdapter() }
    private val taskLogsRvAdapter by lazy { TasksLogsRvAdapter() }
    private val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbarOptions()
        initTasksRv()
        initTaskLogsRv()
        observerLiveData()
    }

    //regionTasksRv
    private fun fillTasksRv(): ArrayList<String> {
        val tasksList by lazy { ArrayList<String>() }
        for (i in 1 until 5) {
            tasksList.add("Task $i")
        }
        return tasksList
    }

    private fun initTasksRv() {
        binding.rvTask.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 4)
            adapter = tasksRvAdapter
            addItemDecoration(GridSpacingItemDecoration(4, 35, false))

        }
        tasksRvAdapter.apply {
            fill(fillTasksRv())
            setOnClickListener { _, item, _ ->
                viewModel.performLongTask(item)
            }
        }
    }
    //endregion

    private fun setToolbarOptions() {
        binding.toolbarTitle.text = getString(R.string.main_toolbar_title)
        setSupportActionBar(binding.toolbar)

        binding.toolbar.navigationIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_menu_navigation)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observerLiveData() {
        viewModel.taskInvokedLiveData.observe(this) {
            it?.let { taskLog ->
                taskLogsRvAdapter.addItem(taskLog)
                taskLogsRvAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initTaskLogsRv() {
        binding.rvTaskLogs.apply {
            linearLayoutManager()
            adapter = taskLogsRvAdapter

        }
    }


}