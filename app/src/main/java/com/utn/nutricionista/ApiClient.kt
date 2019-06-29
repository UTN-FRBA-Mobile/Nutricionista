package com.utn.nutricionista

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.utn.nutricionista.models.Diet
import com.utn.nutricionista.models.Message
import com.utn.nutricionista.models.User
import com.utn.nutricionista.models.Weight
import java.util.concurrent.Callable
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

    inline fun <reified T : Any> post(path: String, payload: T): Task<T> {
        return withIdToken {
            Log.d("SUCCESS", it)
            Fuel.post(url(path))
                .authentication()
                .bearer(it)
                .jsonBody(payload)
                .responseObject<T>()
                .third
                .get()
        }
    }

    inline fun delete(path: String, id : String) {
        withIdToken {
            Log.d("SUCCESS", it)
            Fuel.delete(url(path))
                .authentication()
                .bearer(it)
                .parameters = List<Pair<String,Any?>>(1) { Pair("id", id) }
        }
    }

    fun getUser(): Task<User> {
        return get("/user")
    }

    fun getDiets(): Task<List<Diet>> {
        return get("/diet")
    }

    fun postDiet(diet: Diet): Task<Diet> {
        return post("/diet", diet)
    }

    fun postMessage(message: Message): Task<Message> {
        return post("/message", message)
    }

    fun getMessages(): Task<List<Message>> {
        return get("/message")
    }

    fun getWeights(): Task<List<Weight>> {
        return get("/weight")
    }

    fun getWeight(id: String): Task<Weight> {
        return get("/weight/$id")
    }

    fun postWeight(payload: Weight): Task<Weight> {
        return post("/weight", payload)
    }

    fun deleteWeight(id: String) {
        delete("/weight/delete", id)
    }
}
