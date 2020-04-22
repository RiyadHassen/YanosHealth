package com.example.yanoshealth.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import java.security.AccessControlContext


interface InstructionDao{
    @Query("select * from databaseinstructions")
    fun getInstructions():LiveData<List<DatabaseInstructions>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg instructions:DatabaseInstructions)
}

abstract class InstructionDatabase:RoomDatabase(){
    abstract val instructionDao:InstructionDao
}

private lateinit var INSTANCE:InstructionDatabase

fun getDatabase(context: Context):InstructionDatabase{
    synchronized(InstructionDatabase::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            InstructionDatabase::class.java,"instructions").build()
        }
    }
    return INSTANCE
}
