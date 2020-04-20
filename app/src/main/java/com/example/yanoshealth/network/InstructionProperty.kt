
package  com.example.yanoshealth.network
import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize data class InstructionProperty (
        val id :Int,
        val hname :String,
        val hpass:String,
        @Json(name = "phoneNumbe")val phoneNumb :String,
        @Json(name ="relativeAdress")val relativeAddress:String
): Parcelable {

}