package com.yanos.health.firebase


import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.yanos.android.firebaseui_login_sample.FirebaseUserLiveData


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

    /**
     * Gets a fact to display based on the user's set preference of which type of fact they want
     * to see (Android fact or California fact). If there is no logged in user or if the user has
     * not set a preference, defaults to showing Android facts.
     */

}