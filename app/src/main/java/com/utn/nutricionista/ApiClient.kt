package com.utn.nutricionista

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.extensions.authentication
import com.github.kittinunf.fuel.gson.jsonBody
import com.github.kittinunf.fuel.gson.responseObject
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.utn.nutricionista.models.Diet
import com.utn.nutricionista.models.Message
import com.utn.nutricionista.models.User
import com.utn.nutricionista.models.WeightData
import java.util.concurrent.Callable
import java.util.concurrent.Executors

object ApiClient {
    private const val API_HOST = "https://us-central1-test-project-214218.cloudfunctions.net"

    fun url(path: String): String = "$API_HOST/api$path"

    fun <T> withIdToken(continuation: (String) -> T): Task<T> {
        return SessionManager.currentUser!!.getIdToken(true).onSuccessTask {
            Tasks.call(Executors.newCachedThreadPool(), Callable { continuation(it!!.token!!) })
        }
    }

    inline fun <reified T : Any> authenticatedRequest(crossinline method: (String, Parameters?) -> Request, path: String, payload : T? = null, queryParams: Parameters?): Task<T> {
        return withIdToken {
            method(url(path), queryParams)
                .authentication()
                .bearer(it)
                .jsonBody(payload ?: Unit)
                .responseObject<T>()
                .third
                .get()
        }
    }

    inline fun <reified T : Any> get(path: String, queryParams: Parameters? = null): Task<T> = authenticatedRequest(Fuel::get, path, queryParams = queryParams)

    inline fun <reified T : Any> post(path: String, payload: T, queryParams: Parameters? = null): Task<T> = authenticatedRequest(Fuel::post, path, payload, queryParams)

    inline fun <reified T : Any> put(path: String, payload: T, queryParams: Parameters? = null): Task<T> = authenticatedRequest(Fuel::put, path, payload, queryParams)

    inline fun <reified T : Any> delete(path: String, queryParams: Parameters? = null): Task<T> = authenticatedRequest(Fuel::delete, path, queryParams = queryParams)

    //TODO: move these to companion object methods of their respective classes
    fun getUser(): Task<User> = get("/user")

    fun getDiets(): Task<List<Diet>> = get("/diet")

    fun getDietsByDate(date: String): Task<List<Diet>> = get("/diet", listOf(Pair("date", date)))

    fun postDiet(diet: Diet): Task<Diet> = post("/diet", diet)

    fun getWeight(id: String): Task<WeightData> = get("/weight/$id")

    fun postWeight(payload: WeightData): Task<WeightData> = post("/weight", payload)

    fun putWeight(payload: WeightData): Task<WeightData> = put("/weight/${payload.id}", payload)

    fun deleteWeight(id: String): Task<WeightData> = delete("/weight/$id")
}
