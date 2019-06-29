package com.utn.nutricionista

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.utn.nutricionista.detalleComida.DetalleComidaActivity


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
