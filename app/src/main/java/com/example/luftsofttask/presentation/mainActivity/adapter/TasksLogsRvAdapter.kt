package com.example.luftsofttask.presentation.mainActivity.adapter

import com.example.luftsofttask.presentation.base.BaseAdapter
import com.example.luftsofttask.databinding.ItemTaskLogBinding
import com.example.luftsofttask.presentation.mainActivity.TaskLogEntity

class TasksLogsRvAdapter:BaseAdapter<ItemTaskLogBinding,TaskLogEntity>() {
    override fun setContent(binding: ItemTaskLogBinding, item: TaskLogEntity, position: Int) {
        binding.apply {
            tvTaskLogDate.text=item.taskDate
            tvTaskLogName.text=item.taskName
        }
    }
}