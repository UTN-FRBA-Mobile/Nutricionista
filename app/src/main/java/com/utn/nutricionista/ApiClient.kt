package com.utn.nutricionista

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.*
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.result.Result
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.utn.nutricionista.models.Diet
import com.utn.nutricionista.models.User
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object ApiClient {
    private const val API_HOST = "https://us-central1-test-project-214218.cloudfunctions.net"

    fun url(path: String): String {
        return "$API_HOST/api$path"
    }

    fun <T> withIdToken(continuation: (String) -> T): Task<T> {
        return SessionManager.currentUser!!.getIdToken(true).onSuccessTask {
            Tasks.call(Executors.newCachedThreadPool(), Callable { continuation(it!!.token!!) })
        }
    }

    inline fun <reified T : Any> get(path: String): Task<T> {
        return withIdToken {
            Fuel.get(url(path))
                .authentication()
                .bearer(it)
                .responseObject<T>()
                .third
                .get()
        }
    }

    inline fun <reified T : Any> post(path: String, payload: T): Task<Unit> {
        return withIdToken {
            Fuel.post(url(path))
                .authentication()
                .bearer(it)
                .jsonBody(payload)
                .response()
                .third
                .get()
            Unit
        }
    }

    fun getUser(): Task<User> {
        return get("/user")
    }

    fun getDiets(): Task<List<Diet>> {
        return get("/diet")
    }

    fun postDiet(diet: Diet): Task<Unit> {
        return post("/diet", diet)
    }
}
