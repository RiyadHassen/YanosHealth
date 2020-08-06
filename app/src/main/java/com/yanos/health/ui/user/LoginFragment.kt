package com.yanos.health.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.yanos.health.R
import com.yanos.health.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var signupButton:Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding:FragmentLoginBinding = DataBindingUtil.inflate(
           inflater,R.layout.fragment_login,container,false
       )
        binding.setLifecycleOwner(viewLifecycleOwner)

        signupButton = binding.signupbuttonId

        return binding.root
    }

}
