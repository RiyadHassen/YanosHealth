package com.example.yanoshealth.ui.detailinst

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.yanoshealth.network.NetworkInstruction


class DetailInstViewModel(networkInstruction: NetworkInstruction, app: Application):AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<NetworkInstruction>()

    // The external LiveData for the SelectedProperty
    val selectedNetwork: LiveData<NetworkInstruction>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = networkInstruction
    }

}