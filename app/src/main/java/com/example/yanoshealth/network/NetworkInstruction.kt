
package  com.example.yanoshealth.network
import android.os.Parcelable
import com.example.yanoshealth.database.DatabaseInstructions
import com.example.yanoshealth.domain.Instruction
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize data class NetworkInstruction (
        val id :Int,
        val hname :String,
        val hpass:String,
        @Json(name = "phoneNumbe")val phoneNumb :String,
        @Json(name ="relativeAdress")val relativeAddress:String
): Parcelable {

}


data class NetworkInstructionContainer(val instructions:List<NetworkInstruction> )

/**
 *convert Network results to database objects
 */

fun NetworkInstructionContainer.asDomainModel():List<Instruction>{
        return instructions.map {
                Instruction(
                        id = it.id,
                        hname = it.hname,
                        pass = it.hpass,
                        phoneNumb = it.phoneNumb,
                        relativeAddress = it.relativeAddress
                )
        }
}

fun NetworkInstructionContainer.asDatabaseModel():Array<DatabaseInstructions>{
        return instructions.map {
                DatabaseInstructions(
                        id = it.id,
                        hname = it.hname,
                        pass = it.hpass,
                        phoneNumb = it.phoneNumb,
                        relativeAddress = it.relativeAddress
                )
        }.toTypedArray()
}