package com.example.yanoshealth.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.yanoshealth.database.InstructionDatabase
import com.example.yanoshealth.database.asDomainModel
import com.example.yanoshealth.domain.Instruction
import com.example.yanoshealth.network.InstructionApi
import com.example.yanoshealth.network.InstructionService
import com.example.yanoshealth.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InstructionRepository(private val instructionDatabase: InstructionDatabase){


    val instructions:LiveData<List<Instruction>> = Transformations.map(instructionDatabase.instructionDao.getInstructions()){
        it.asDomainModel()
    }

    /*
    to get data from network and insert to the database
     */
    suspend fun updateDatabase(){
        withContext(Dispatchers.IO){
            val networkdata = InstructionApi.RETROFIT_SERVICE.getProperties().await().body()
            if (networkdata != null) {
                instructionDatabase.instructionDao.insertAll(*networkdata)
            }
        }
    }
}