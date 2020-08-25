package com.yanos.health.ui.instructionlist


import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yanos.health.adapter.InstructionAdapter
import com.yanos.health.databinding.FragmentInstructionRecyclerBinding
import com.yanos.health.firebase.Instruction
import com.yanos.health.firebase.InstructionViewModel
import com.yanos.health.firebase.LoginViewModel
import com.yanos.health.firebase.User
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class InstructionRecycler : Fragment(){


    private lateinit var binding:FragmentInstructionRecyclerBinding
    private lateinit var adapter: InstructionAdapter
    private  val database : DatabaseReference by lazy{
        Firebase.database.reference
    }
    private val viewModel: InstructionViewModel by lazy {
        ViewModelProviders.of(this).get(InstructionViewModel::class.java)
    }




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentInstructionRecyclerBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        val args = arguments?.let { InstructionRecyclerArgs.fromBundle(it) }
        val myRef = database.child("instruction").orderByChild("week_lang").equalTo(args?.weekLanguage.toString())
        val options = FirebaseRecyclerOptions.Builder<Instruction>()
            .setLifecycleOwner(this)
            .setQuery(myRef, Instruction::class.java)
            .build()

        adapter = InstructionAdapter(InstructionAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it) },options)
        binding.hopsitalList.adapter =  adapter
        adapter.updateOptions(options)
        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if (null != it) {
                findNavController().navigate(
                    InstructionRecyclerDirections.actionShowDetailInstruction(
                        it
                    ))
                viewModel.displayPropertyDetailsComplete()
                }
            })
            return binding.root
        }
}
