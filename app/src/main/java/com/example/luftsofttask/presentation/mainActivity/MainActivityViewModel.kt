package com.example.luftsofttask.presentation.mainActivity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainActivityViewModel:ViewModel() {

    private val _taskInvoked by lazy { MutableLiveData<TaskLogEntity>() }
    val taskInvokedLiveData get() = _taskInvoked
    fun performLongTask(taskName: String)
    {
        _taskInvoked.value= TaskLogEntity(getCurrentTime(),taskName)
        viewModelScope.launch(Dispatchers.IO) {
            for(i in 0 until 30000)
            {
                Log.d("timer",i.toString())
            }
        }
    }
    private fun getCurrentTime():String
    {
        return SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(Date())
    }
}