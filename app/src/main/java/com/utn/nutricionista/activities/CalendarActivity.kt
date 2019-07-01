package com.utn.nutricionista.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.utn.nutricionista.Messages.MessagesActivity
import com.utn.nutricionista.R

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
    }

    fun buttonPressed(view: View) {
        val intent = Intent(this, MessagesActivity::class.java)
        startActivity(intent)
    }
}
