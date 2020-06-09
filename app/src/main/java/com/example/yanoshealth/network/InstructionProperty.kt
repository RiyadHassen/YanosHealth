
package  com.example.yanoshealth.network
import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize data class InstructionProperty (
        @Json(name = "id")val id :Int,
        @Json(name = "age_group_name")val hname :String,
        @Json(name = "education_name")val hpass:String,
        @Json(name = "language_name")val phoneNumb :String,
        @Json(name ="location_name")val relativeAddress:String,
        @Json(name ="martial_status_name")val martialStatus:String,
        @Json(name ="instruction")val instruction:String,
        @Json(name ="week_number_name")val weekno:String
): Parcelable {

}