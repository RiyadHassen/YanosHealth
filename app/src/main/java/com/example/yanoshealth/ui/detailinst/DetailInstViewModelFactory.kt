package com.example.yanoshealth.ui.detailinst

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yanoshealth.network.NetworkInstruction

class DetailInstViewModelFactory(
    private val networkInstruction: NetworkInstruction,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailInstViewModel::class.java)) {
            return DetailInstViewModel(networkInstruction, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
