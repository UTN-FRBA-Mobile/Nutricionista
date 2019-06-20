package com.utn.nutricionista

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.ResponseHandler
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.gson.responseObject
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.utn.nutricionista.Models.Diet
import com.utn.nutricionista.Models.User

object ApiClient {
    private const val API_HOST = "https://us-central1-test-project-214218.cloudfunctions.net"

    fun url(path: String): String {
        return "$API_HOST/api$path"
    }

    // TODO: at the time this completely disregards the possibility of failure when fetching id token. Ideally I'd like to that on error handler too
    fun <T> withIdToken(continuation: (String) -> T): Task<T> {
        return SessionManager.currentUser!!.getIdToken(true).onSuccessTask {
            Tasks.forResult(continuation(it!!.token!!))
        }
    }

    inline fun <reified T : Any> get(path: String, handler: ResponseHandler<T>) {
        withIdToken {
            Fuel.get(url(path))
                .authentication()
                .bearer(it)
                .responseObject(handler)
        }
    }

    // TODO: I really dislike having to pass the handler as a parameter; also the way of defining response handlers seems awkward too. I'd rather handle it in a promise-style of code
    fun getUser(handler: ResponseHandler<User>) {
        get("/user", handler)
    }

    fun getDietas(handler: ResponseHandler<List<Diet>>) {
        get("/diet", handler)
    }
}
