package com.yanos.health.ui.detailinst

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yanos.health.firebase.Instruction


class DetailInstViewModelFactory(
    private val instruction: Instruction,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailInstViewModel::class.java)) {
            return DetailInstViewModel(instruction, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}