package com.utn.nutricionista

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object SessionManager {
    private val auth = FirebaseAuth.getInstance()
    val currentUser : FirebaseUser?
        get() = auth.currentUser
}
