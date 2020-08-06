package com.yanos.health.ui.user



import android.opengl.Visibility
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException


import com.yanos.health.R
import com.yanos.health.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yanos.health.firebase.InstructionViewModel
import com.yanos.health.firebase.User
import java.util.concurrent.TimeUnit


class RegisterFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var phone:EditText
    private lateinit var weekNo:EditText
    private lateinit var regButton:Button
    private lateinit var spinner: Spinner
    private lateinit var language:String
    private lateinit var progressBar:ProgressBar

    private val database: DatabaseReference by lazy {
      Firebase.database.reference
    }
    private val viewModel: InstructionViewModel by lazy {
        ViewModelProviders.of(this).get(InstructionViewModel::class.java)
    }


    private lateinit var  auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationInProgress = false

    private lateinit var binding:FragmentRegisterBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        if (savedInstanceState != null){
            onActivityCreated(savedInstanceState)
        }
        spinner =binding.language
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.language,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
        regButton = binding.registerButton
        phone = binding.phoneEditText
        weekNo = binding.weekNoEditText
        progressBar = binding.progressBar
        progressBar.visibility = View.INVISIBLE

        auth = FirebaseAuth.getInstance()






        regButton.setOnClickListener {
            val actualPhone = "+251"+phone.text.toString()
            Log.d("==================PHONE",actualPhone)
            progressBar.visibility = View.VISIBLE
            startPhoneNumberVerification(actualPhone)

        }




        return binding.root
    }



    //-------------verificaion after restart progress
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, verificationInProgress)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState != null){
            verificationInProgress = savedInstanceState!!.getBoolean(KEY_VERIFY_IN_PROGRESS)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null){
            view?.findNavController()?.navigate(R.id.action_registerFragment_to_instructionRecycler)
        }
        //-----------updateUI(currentUser)
        if (verificationInProgress && emptyValidation()) {
            startPhoneNumberVerification(binding.phoneEditText.text.toString())
        }
    }
    //----------still verifiaction

    private fun onVerificationCallBack(){
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                verificationInProgress = false
                progressBar.visibility =View.INVISIBLE

                signInWithPhoneAuthCredential(credential)
            }
            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)
                verificationInProgress = false
                if (e is FirebaseAuthInvalidCredentialsException) {
                    binding.phoneEditText.error = "Invalid phone number."
                    Toast.makeText(requireContext(),"Try  Again",Toast.LENGTH_LONG)
                    progressBar.visibility =View.INVISIBLE
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
//  ---                  Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
//   --                   Snackbar.LENGTH_SHORT).show()

                }
                // Show a message and update the UI
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(),"Try Again",Toast.LENGTH_LONG).show()

            }
        }

    }

    //--- spinner handler
    override fun onNothingSelected(parent: AdapterView<*>?) {
        language = parent?.getItemAtPosition(0) as String
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // var items:String = parent?.getItemAtPosition(position) as String
        language = parent?.getItemAtPosition(position)  as String
    }
    private fun emptyValidation():Boolean
    {
        if (TextUtils.isEmpty(phone.text.toString())|| TextUtils.isEmpty(weekNo.text.toString()))
        {
            return true
        }
        return false
    }
    //-------------------------------------

    private fun startPhoneNumberVerification(phoneNumber: String) {
        onVerificationCallBack()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this.requireActivity(), // Activity (for callback binding)
            callbacks) // OnVerificationStateChangedCallbacks

        verificationInProgress = true
    }



    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
            auth.signInWithCredential(credential)
                .addOnCompleteListener(this.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")

                        val user = task.result?.user
                        Toast.makeText(requireContext(),"SUCESSFULLY SIGEN IN",Toast.LENGTH_LONG)
                        val client :User = User(phone.text.toString(),weekNo.text.toString().toInt(),language)
                        database.child("users").child(phone.text.toString()).setValue(client)
                        view?.findNavController()?.navigate(R.id.action_registerFragment_to_instructionRecycler)

                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        binding.phoneEditText.error = "ERROR phone number."
                        Toast.makeText(requireContext(),"FAILED TO SIGN IN TRY AGAIN",Toast.LENGTH_LONG)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            binding.phoneEditText.error = "ERROR AUTHEN phone number."
                        }
                    }
                }
    }
    private fun signOut() {
        auth.signOut()
//--            updateUI(STATE_INITIALIZED)
    }
    companion object {
        private const val TAG = "PhoneAuthActivity"
        private const val KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress"
    }

}

