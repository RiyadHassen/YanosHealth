package com.yanos.health.ui.gallery

import android.content.ContentValues

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yanos.health.R
import com.yanos.health.databinding.FragmentGalleryBinding
import com.yanos.health.firebase.User
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GalleryFragment : Fragment(),AdapterView.OnItemSelectedListener,View.OnClickListener {

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private  val database : DatabaseReference by lazy{
        Firebase.database.reference
    }
    private lateinit var progressBar: ProgressBar
    private lateinit var button:Button
    private lateinit var language:String
    private lateinit var currentText: TextView
    private lateinit var spinner:Spinner
    private lateinit var weeklang:String


    override fun onCreateView(
        inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGalleryBinding.inflate(inflater)
        progressBar = binding.progressBar
        button = binding.countinue
        button.visibility = View.INVISIBLE
        currentText = binding.currentWeek
        spinner = binding.language
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.language,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this

        if (firebaseAuth.currentUser !=null){
            val phoneNumber =  firebaseAuth.currentUser!!.phoneNumber.toString()
            val userRef = database.child("users").child(phoneNumber)

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val post = dataSnapshot.getValue<User>()
                    if (post != null) {
                        val user = post
                        val current = getDays(user.regDate) + user.weekno
                        currentText.text = current.toString()
                        language=user.language
                        weeklang = current.toString()+"_"+user.language
//                        textView.text = week_language
                        progressBar.visibility = View.INVISIBLE
                        button.visibility  = View.VISIBLE
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                    Toast.makeText(context,"Try Again",Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.INVISIBLE
                }
            }
            userRef.addValueEventListener(postListener)
        }
        button.setOnClickListener(this)
        return binding.root
    }
    @Throws(ParseException::class)
    fun getDays(regDate: String?): Int {
        val MILLIS_PER_DAY = 24 * 60 * 60 * 1000.toLong()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val begin = dateFormat.parse(regDate).time
        val end = Date().time // 2nd date want to compare
        val diff = (end - begin) / MILLIS_PER_DAY
        return Math.ceil((diff.toInt()/7).toDouble()).toInt()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        language = parent?.getItemAtPosition(0) as String
        language.toLowerCase()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        language = parent?.getItemAtPosition(position)  as String
        language.toLowerCase()
    }

    override fun onClick(v: View?) {
        if (TextUtils.isEmpty(currentText.text) ){
            if (weeklang !=null){

                view?.findNavController()?.navigate(GalleryFragmentDirections.actionNavGalleryToInstructionRecycler(weeklang))
            }
        }else{
            weeklang = currentText.text.toString() +"_"+language
            view?.findNavController()?.navigate(GalleryFragmentDirections.actionNavGalleryToInstructionRecycler(weeklang))
        }
    }
}
