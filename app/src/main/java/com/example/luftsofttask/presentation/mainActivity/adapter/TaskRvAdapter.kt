package com.example.luftsofttask.presentation.mainActivity.adapter

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import com.example.luftsofttask.presentation.base.BaseAdapter
import com.example.luftsofttask.databinding.ItemTaskBinding
import com.example.luftsofttask.R

class TaskRvAdapter:BaseAdapter<ItemTaskBinding,String>() {
private var selectedPosition=-1

    @SuppressLint("NotifyDataSetChanged")
    override fun setContent(binding: ItemTaskBinding, item: String, position: Int) {
        binding.apply {
            tvTask.text=item
            root.setOnClickListener {
                selectedPosition = position
                onViewClicked(it, item, position)
                notifyDataSetChanged()
            }
            if (selectedPosition == position) {
                tvTask.apply {
                    setTextColor(ContextCompat.getColor(root.context,R.color.white))
                    background=ContextCompat.getDrawable(root.context,R.drawable.tv_task_clicked)
                }
            } else {
               tvTask.apply {
                   setTextColor(ContextCompat.getColor(root.context,R.color.black))
                   background=null
               }
            }
        }
        }
    }
