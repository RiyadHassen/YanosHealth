package com.yanos.health.firebase


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yanos.android.firebaseui_login_sample.FirebaseUserLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class LoginViewModel : ViewModel() {



    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val firebaseLiveData = FirebaseUserLiveData()
    val authenticationState= Transformations.map(firebaseLiveData) { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }
    private var _instruction = MutableLiveData<String>()

    val instruction: LiveData<String>
        get() = _instruction


    private var viewModelJob = Job()
    //to update the view immediately

    private val courutineScope = CoroutineScope(viewModelJob+ Dispatchers.Main)
    private fun getConfigs(){
          courutineScope.launch {
              //  firebaseLiveData.getConfig(instruction.value->Unit)
            }
    }
    init {
        getConfigs()
    }
}