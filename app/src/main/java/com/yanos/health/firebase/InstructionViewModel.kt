package com.yanos.health.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.google.firebase.database.*

import kotlinx.coroutines.*

class InstructionViewModel: ViewModel() {

    private var _instruction = MutableLiveData<List<Instruction?>>()
    private lateinit var database : DatabaseReference
    private  var repository: InstructionRepository = InstructionRepository()
    val instruction:LiveData<List<Instruction?>>
        get() = _instruction


    private var viewModelJob = Job()
    //to update the view immediately

    private val courutineScope = CoroutineScope(viewModelJob+ Dispatchers.Main)
    private fun getInstructions(){
            courutineScope.launch {
                _instruction.value = repository.getDataSet()
                Log.d("==============status1==", _instruction.value.toString())
            }
    }
    init {
        getInstructions()
        Log.d("==============status2==", _instruction.value.toString())

    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<Instruction>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<Instruction>
        get() = _navigateToSelectedProperty

    fun displayPropertyDetails(instruction: Instruction) {
        _navigateToSelectedProperty.value = instruction
    }
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}