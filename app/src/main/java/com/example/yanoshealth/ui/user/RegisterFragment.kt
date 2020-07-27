package com.example.yanoshealth.ui.user

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


import com.example.yanoshealth.R
import com.example.yanoshealth.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment(), AdapterView.OnItemSelectedListener {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegisterBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        val spinner: Spinner = binding.root.findViewById(R.id.gender)

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.gender,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var items:String = parent?.getItemAtPosition(position) as String
    }

}
