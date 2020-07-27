package com.example.yanoshealth.ui.detailinst

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yanoshealth.firebase.Instruction

import com.example.yanoshealth.network.InstructionProperty


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