package com.yanos.health.firebase

import com.google.firebase.database.IgnoreExtraProperties
import java.text.SimpleDateFormat
import java.util.*

@IgnoreExtraProperties
data class User(val phone:String="",
                val weekno:Int=0,
                val language:String="",
                val week_lang:String="",
                val regDate: String =SimpleDateFormat("dd/MM/yyyy").format(Date())
                )   {
}