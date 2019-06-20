package com.utn.nutricionista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utn.nutricionista.models.Message
import kotlinx.android.synthetic.main.activity_my_progress.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MyProgressActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_progress)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val messages = ArrayList<Message>()
        adapter = RecyclerAdapter(messages)
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(adapter.messages.size - 1)
        ApiClient.getMessages().addOnSuccessListener {
            Log.d("SUCCESS", "SWEET, SWEET SUCCESS!")
            val messages = it
            adapter = RecyclerAdapter(messages)
            recyclerView.adapter = adapter
            recyclerView.scrollToPosition(adapter.messages.size - 1)
        }.addOnFailureListener { e ->
            Log.d("FAILURE", "GASP! SOMETHING WENT WRONG: ${e.message}")
        }
    }

    fun buttonPressed(view: View) {
        adapter.messages = adapter.messages + Message("Diego", // change to user name when profile is ready
            textfield.text.toString(),
            LocalDateTime.now(),
            "sent", own = true)
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(adapter.messages.size - 1)
        textfield.setText("")
    }

    private fun getCurrentDateFormatted(): String {
        val current = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(current)
    }
}
