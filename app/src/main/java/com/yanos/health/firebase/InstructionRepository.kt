package com.yanos.health.firebase

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class InstructionRepository {
    private  val database : DatabaseReference by lazy{
         Firebase.database.reference
    }
    init {
        getDataSet()
    }
    var data:   MutableList<Instruction?> = ArrayList<Instruction?>()

    fun getDataSet(){

        val myRef = database.child("instruction")
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val instructions = dataSnapshot.getValue(Instruction::class.java)

                data.add(instructions)


                Log.d("Repository",data.toString())
                Log.d("onChaged",instructions.toString())

            }
            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val newInstruction = dataSnapshot.getValue(Instruction::class.java)
                data.add(newInstruction)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.d(ContentValues.TAG, "onChildRemoved:" + dataSnapshot.key!!)
                val newInstruction = dataSnapshot.getValue(Instruction::class.java)
                data.remove(newInstruction)
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Log.d(ContentValues.TAG, "onChildMoved:" + dataSnapshot.key!!)
                val movedInstruction = dataSnapshot.getValue(Instruction::class.java)
                data.add(movedInstruction)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w( "post:onCancelled", databaseError.toException())
            }
        }
        myRef.addChildEventListener(childEventListener)
    }


}