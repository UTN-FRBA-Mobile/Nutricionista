package com.utn.nutricionista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseHandler
import com.utn.nutricionista.detalleComida.DetalleComidaActivity
import com.utn.nutricionista.models.Diet
import com.utn.nutricionista.models.User


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (SessionManager.currentUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

     /*   ApiClient.postDiet(Diet(fecha = "10/13")).addOnSuccessListener {
            Log.d("SUCCESS", "SWEET, SWEET SUCCESS!")
        }.addOnFailureListener { e ->
            Log.d("FAILURE", "GASP! SOMETHING WENT WRONG: ${e.message}")
        }*/

    }

    fun buttonPressed(view: View) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun redirectToDetalle(view: View) {
        //val intent = Intent(this, DetalleComidaActivity::class.java)
        val intent = Intent(this, DetalleComidaActivity::class.java)
        startActivity(intent)
    }

    fun redirectToWeight(view: View) {
        val intent = Intent(this, WeightActivity::class.java)
        startActivity(intent)
    }
}
