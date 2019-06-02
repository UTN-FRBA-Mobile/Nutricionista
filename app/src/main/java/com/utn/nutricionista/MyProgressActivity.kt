package com.utn.nutricionista

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_progress.*
import java.text.SimpleDateFormat
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
        messages.add(Message("diego",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent", own = true))
        messages.add(Message("Nutricionista",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent", own = false))
        messages.add(Message("Nutricionista",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent", own = false))
        messages.add(Message("diego",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent", own = true))
        messages.add(Message("diego",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ipsum iorem ipsum ",
            "22/05/19",
            "sent", own = true))
        messages.add(Message("diego",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent", own = true))
        messages.add(Message("Nutricionista",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent", own = false))
        messages.add(Message("Nutricionista",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent", own = false))
        adapter = RecyclerAdapter(messages)
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(adapter.messages.size - 1)
    }

    fun buttonPressed(view: View) {
        adapter.messages += Message("Diego", // change to user name when profile is ready
            textfield.text.toString(),
            getCurrentDateFormatted(),
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
