/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yanos.android.firebaseui_login_sample

import android.content.ContentValues
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.yanos.health.firebase.User
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * This class observes the current FirebaseUser. If there is no logged in user, FirebaseUser will
 * be null.
 *
 * Note that onActive() and onInactive() will get triggered when the configuration changes (for
 * example when the device is rotated). This may be undesirable or expensive depending on the
 * nature of your LiveData object, but is okay for this purpose since we are only adding and
 * removing the authStateListener.
 */
class FirebaseUserLiveData : LiveData<FirebaseUser?>() {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private var user:User = User()
    private  val database : DatabaseReference by lazy{
        Firebase.database.reference
    }
    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        // Use the FirebaseAuth instance instantiated at the beginning of the class to get an entry
        // point into the Firebase Authentication SDK the app is using.
        // With an instance of the FirebaseAuth class, you can now query for the current user.
        value = firebaseAuth.currentUser
    }

    // When this object has an active observer, start observing the FirebaseAuth state to see if
    // there is currently a logged in user.
    override fun onActive() {
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    // When this object no longer has an active observer, stop observing the FirebaseAuth state to
    // prevent memory leaks.
    override fun onInactive() {
        firebaseAuth.removeAuthStateListener(authStateListener)
    }

    fun getConfig(myCallback: (String) -> Unit){
            if (firebaseAuth.currentUser !=null){
                val phoneNumber =  firebaseAuth.currentUser!!.phoneNumber.toString()
                val userRef = database.child("users").child(phoneNumber)
                val postListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val post = dataSnapshot.getValue<User>()
                        if (post != null) {
                            user = post
                            val current = getDays(user.regDate) + user.weekno
                            val week_language = current.toString()+"_"+user.language
                            Log.d("UUUUUUUUWWWWW",week_language.toString())
                            myCallback(week_language)

                        }
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())

                    }
                }
                userRef.addValueEventListener(postListener)
            }
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

    //        val connectedRef = Firebase.database.getReference(".info/connected")
//        Log.d("TAG===", connectedRef.toString())
//        connectedRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val connected = snapshot.getValue(Boolean::class.java) ?: false
//                if (connected) {
//                    Log.d("TAG===", "connected")
//                } else {
//                    Log.d("TAG===", "not connected")
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("TAG", "Listener was cancelled")
//            }
//        })

}