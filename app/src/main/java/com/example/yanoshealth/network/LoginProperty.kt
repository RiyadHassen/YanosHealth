package com.example.yanoshealth.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize data class LoginProperty(
    @Json(name = "user_name") val username : String,
    @Json(name = "user_pin")val user_pin:Int
):Parcelable  {
}