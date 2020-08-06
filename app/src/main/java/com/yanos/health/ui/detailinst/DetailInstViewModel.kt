package com.yanos.health.ui.detailinst

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yanos.health.firebase.Instruction




class DetailInstViewModel(instruction: Instruction, app: Application):AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<Instruction>()

    // The external LiveData for the SelectedProperty
    val selectedProperty: LiveData<Instruction>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = instruction
    }

}