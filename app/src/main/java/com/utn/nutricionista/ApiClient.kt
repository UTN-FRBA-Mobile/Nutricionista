package com.utn.nutricionista

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.serialization.responseObject
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GetTokenResult
import com.utn.nutricionista.Models.User

object ApiClient {
    private const val API_HOST = "https://us-central1-test-project-214218.cloudfunctions.net"

    private fun url(path: String): String {
        return "$API_HOST/api$path"
    }

    fun get(path: String) {
//        SessionManager.currentUser!!.getIdToken(true). { result: GetTokenResult ->
//            result.token
//        }
    }

    fun getUser() {
        SessionManager.currentUser!!.getIdToken(true).addOnCompleteListener { task ->
            val idToken = task.result!!.token

            Fuel.get(url("/messages"))
                .header(Headers.AUTHORIZATION, "Bearer $idToken")
                .responseObject(User.serializer()) { _, _, user ->
                    user
                }
        }
    }
}
