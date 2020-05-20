package com.ad340.datingapp

import com.google.firebase.auth.FirebaseAuth
import java.io.Serializable


object FirebaseAuthGetter : Serializable {
    var firebaseAuth: FirebaseAuth? = null
        get() = if (field != null) {
            field
        } else FirebaseAuth.getInstance()
}