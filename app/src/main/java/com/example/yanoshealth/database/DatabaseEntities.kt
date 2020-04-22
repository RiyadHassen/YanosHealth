package com.example.yanoshealth.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yanoshealth.domain.Instruction

@Entity
data class DatabaseInstructions constructor(
    @PrimaryKey
    val id:Int,
    val hname: String,
    val pass:String,
    val phoneNumb:String,
    val relativeAddress:String
)


fun List<DatabaseInstructions>.asDomainModel():List<Instruction>{
    return map {
        Instruction(
            id = it.id,
            hname = it.hname,
            pass = it.pass,
            phoneNumb = it.phoneNumb,
            relativeAddress = it.relativeAddress)
    }
}