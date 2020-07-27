package com.example.yanoshealth.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize data class RegisterProperty(
    @Json(name = "user_name") val username : String,
    @Json(name = "phone_number")val phone_number: String,
    @Json(name = "actual_age")val actual_age:Int,
    @Json(name = "current_week")val current_week:Int,
    @Json(name = "language")val laguage:String,
    @Json(name = "martial_status")val martial_status:String,
    @Json(name = "user_pin")val user_pin:Int
):Parcelable  {
}