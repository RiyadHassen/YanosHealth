package com.yanos.health.ui.detailinst

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.yanos.health.databinding.FragmentDetailInstructionBinding


class DetailInstruction : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentDetailInstructionBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val instructionProperty = DetailInstructionArgs.fromBundle(arguments!!).property
        val viewModelFactory = DetailInstViewModelFactory(instructionProperty,application)
        binding.viewmodel = ViewModelProviders.of(
           this, viewModelFactory).get(DetailInstViewModel::class.java)

        return binding.root
    }

}
