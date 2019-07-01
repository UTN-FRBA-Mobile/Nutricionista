package com.utn.nutricionista.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.utn.nutricionista.R

class DietActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet)
    }

    fun buttonPressed(view: View) {
        val intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }
}
