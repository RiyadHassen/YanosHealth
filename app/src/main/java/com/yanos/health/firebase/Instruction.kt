package com.yanos.health.firebase

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Instruction(val language:String="",
                        val weekno:Int=1,
                        val content:String="",
                        val imageurl:String=""):Parcelable {
}