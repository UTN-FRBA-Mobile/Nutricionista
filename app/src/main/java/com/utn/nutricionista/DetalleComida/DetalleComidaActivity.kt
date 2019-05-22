package com.utn.nutricionista.DetalleComida

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.utn.nutricionista.R

class DetalleComidaActivity : AppCompatActivity(),
    DetalleComidaFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_comida)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.dietaPropuestaFragmentContainer,
                DetalleComidaFragment.newPreDefDietaInstance()
            )
            .commit()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.dietaExtraFragmentContainer,
                DetalleComidaFragment.newExtraDietaInstance()
            )
            .commit()

    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.dietaPropuestaFragmentContainer, fragment)
            .commit()
    }

}
