package com.utn.nutricionista

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_my_progress.*

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
            "sent"))
        messages.add(Message("Nutricionista",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent"))
        messages.add(Message("Nutricionista",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent"))
        messages.add(Message("diego",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent"))
        messages.add(Message("diego",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ipsum iorem ipsum ",
            "22/05/19",
            "sent"))
        messages.add(Message("diego",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent"))
        messages.add(Message("Nutricionista",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent"))
        messages.add(Message("Nutricionista",
            "iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum iorem ipsum ",
            "22/05/19",
            "sent"))
        adapter = RecyclerAdapter(messages)
        recyclerView.adapter = adapter
    }
}
