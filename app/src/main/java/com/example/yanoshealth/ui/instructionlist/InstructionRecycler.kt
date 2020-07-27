package com.example.yanoshealth.ui.instructionlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.yanoshealth.adapter.InstructionAdapter

import com.example.yanoshealth.databinding.FragmentInstructionRecyclerBinding
import com.example.yanoshealth.firebase.InstructionViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class InstructionRecycler : Fragment() {

    private val viewModel: InstructionViewModel by lazy {
        ViewModelProviders.of(this).get(InstructionViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentInstructionRecyclerBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        binding.viewModel = viewModel
        Log.i("STATUS", binding.toString())
     binding.hopsitalList.adapter = InstructionAdapter(InstructionAdapter.OnClickListener {
                viewModel.displayPropertyDetails(it)
     })
        viewModel.navigateToSelectedProperty.observe(this, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
               findNavController().navigate(
                    InstructionRecyclerDirections.actionShowDetailInstruction(
                        it
                    )
               )
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        })
        val connectedRef = Firebase.database.getReference(".info/connected")
        Log.d("TAG===", connectedRef.toString())
        connectedRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val connected = snapshot.getValue(Boolean::class.java) ?: false
                if (connected) {
                    Log.d("TAG===", "connected")
                } else {
                    Log.d("TAG===", "not connected")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Listener was cancelled")
            }
        })

        return binding.root
    }

}
