package com.example.yanoshealth.ui.instructionlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.yanoshealth.network.InstructionApi
import com.example.yanoshealth.network.InstructionProperty

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

enum class YanosStatus { LOADING, ERROR, DONE }

class InstructionViewModel:ViewModel(){
    private val _status = MutableLiveData<YanosStatus>()

    val status: LiveData<YanosStatus>
        get() = _status


    private val _properties = MutableLiveData<List<InstructionProperty>>()

    val property:LiveData<List<InstructionProperty>>
        get() = _properties


    private var viewModelJob = Job()
    //to update the view immediately
    private val courutineScope = CoroutineScope(viewModelJob+Dispatchers.Main)


    private fun getInstructions(){
        courutineScope.launch {
            //get teh deffered object for our retrofit request
            var getProptetyDefered = InstructionApi.RETROFIT_SERVICE.getProperties()
            try {
                _status.value = YanosStatus.LOADING
                //this will run on a thread managed by retrofit
                val listResult = getProptetyDefered.await()
                _status.value = YanosStatus.DONE
                _properties.value = listResult
                Log.i("DATA",_properties.value.toString())
            }catch (e : Exception){
                _status.value = YanosStatus.ERROR
                Log.i("=================","no data fetched from the rest api")
                _properties.value=ArrayList()
            }
        }
    }

    init {
        getInstructions()
        Log.d("==============status==", property.toString())
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<InstructionProperty>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<InstructionProperty>
        get() = _navigateToSelectedProperty

    fun displayPropertyDetails(instructionProperty: InstructionProperty) {
        _navigateToSelectedProperty.value = instructionProperty
    }
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}