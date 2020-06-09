package  com.example.yanoshealth.network



import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://10.0.2.2:5000/api/"

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

/**
 * A public interface that exposes the [getProperties] method
 */
interface InstructionService {
    /**
     * Returns a Coroutine [Deferred] [List] of [InstructionList] which can be fetched with await() if
     * in a Coroutine scope.
     */
    @GET("instructions")
    fun getProperties(): Deferred<List<InstructionProperty>>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object InstructionApi {
    val RETROFIT_SERVICE : InstructionService by lazy {
        retrofit.create(InstructionService::class.java)
    }
}
