package com.example.yanoshealth.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
private const val BASE_URL = "http://10.0.2.2:5000/"
interface LoginService {


    @GET("access-denied")
    fun accessDenied(): Deferred<Response<String>>

    @POST("login")
    fun getLogin(@Query("username") username:String, @Query("user_pin") password:Int): Deferred<Response<LoginProperty>>

    @POST("registerClient")
    fun registerClient(@Body user:RegisterProperty) : Deferred<Response<RegisterProperty>>


    companion object {
        fun getInstance(): LoginService {
            val client = OkHttpClient
                .Builder()
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
            return retrofit.create(LoginService::class.java)
        }
    }
}