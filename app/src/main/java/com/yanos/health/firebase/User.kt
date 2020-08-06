package com.yanos.health.firebase

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class User(val phone:String,
                val weekno:Int,
                val language:String,
                val regDate: Date= Date()
                )   {
}