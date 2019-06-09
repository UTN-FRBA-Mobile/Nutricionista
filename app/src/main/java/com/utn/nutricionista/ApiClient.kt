package com.utn.nutricionista

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.serialization.responseObject
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.utn.nutricionista.Models.User
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json

object ApiClient {
    private const val API_HOST = "https://us-central1-test-project-214218.cloudfunctions.net"

    fun url(path: String): String {
        return "$API_HOST/api$path"
    }

    fun <T> withIdToken(continuation: (String) -> T): Task<T> {
        return SessionManager.currentUser!!.getIdToken(true).onSuccessTask {
            Tasks.forResult(continuation(it!!.token!!))
        }
    }

    @UseExperimental(ImplicitReflectionSerializer::class)
    inline fun <reified T : Any> get(path: String, crossinline continuation: (T) -> Unit) {
        withIdToken {
            Fuel.get(url(path))
                .authentication()
                .bearer(it)
                .responseObject<T>(Json.nonstrict) { _, _, result ->
                    continuation(result.get())
                }
        }
    }

    fun getUser(continuation: (User) -> Unit) {
        get("/user", continuation)
    }
}
