package com.example.yanoshealth.ui.instructionlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.yanoshealth.database.getDatabase

import com.example.yanoshealth.network.InstructionApi
import com.example.yanoshealth.network.NetworkInstruction
import com.example.yanoshealth.repository.InstructionRepository
import kotlinx.coroutines.*

import java.lang.Exception

enum class YanosStatus { LOADING, ERROR, DONE }

class InstructionViewModel(application: Application):AndroidViewModel(application){
    private val _status = MutableLiveData<YanosStatus>()
    val status: LiveData<YanosStatus>
        get() = _status

    private val viewModelJob = SupervisorJob()
    //to update the view immediately
    private val courutineScope = CoroutineScope(viewModelJob+Dispatchers.Main)

    private val database = getDatabase(application)
    private val instructionRepository = InstructionRepository(database)

    init {
        courutineScope.launch {
            instructionRepository.updateDatabase()
        }
    }
    val properties = instructionRepository.instructions

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    class Factory(val app:Application):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InstructionViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InstructionViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}