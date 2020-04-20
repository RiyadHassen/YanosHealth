package com.example.yanoshealth.ui.detailinst

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.yanoshealth.network.InstructionProperty


class DetailInstViewModel(instructionProperty: InstructionProperty, app: Application):AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<InstructionProperty>()

    // The external LiveData for the SelectedProperty
    val selectedProperty: LiveData<InstructionProperty>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = instructionProperty
    }

}