package com.utn.nutricionista

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utn.nutricionista.models.Message
import com.utn.nutricionista.models.MessageStatus
import kotlinx.android.synthetic.main.activity_my_progress.*
import java.text.SimpleDateFormat
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
            adapter = RecyclerAdapter(messages.sortedBy { it.date })
            recyclerView.adapter = adapter
            recyclerView.scrollToPosition(adapter.messages.size - 1)
        }.addOnFailureListener { e ->
            Log.d("FAILURE", "GASP! SOMETHING WENT WRONG: ${e.message}")
        }
    }

    fun buttonPressed(view: View) {
        var newMessage = Message(null,
        "Diego", // change to user name when profile is ready
        textfield.text.toString(),
        Date(),
        MessageStatus.SENDING,
        own = true)
        adapter.messages = (adapter.messages + newMessage).sortedBy { it.date }
        recyclerView.adapter = adapter
        recyclerView.scrollToPosition(adapter.messages.size - 1)
        textfield.setText("")
        ApiClient.postMessage(newMessage.getMessageForPost()).addOnSuccessListener {
            val postedMessage = it
            val removedWaitingMessage = adapter.messages.filter {
                !it.equals(newMessage)
            }
            adapter.messages = (removedWaitingMessage + postedMessage).sortedBy { it.date }
            recyclerView.adapter = adapter
            recyclerView.scrollToPosition(adapter.messages.size - 1)
        }.addOnFailureListener { e ->
            val removedWaitingMessage = adapter.messages.filter {
                !it.equals(newMessage)
            }
            adapter.messages = (removedWaitingMessage + newMessage.getErrorMessage()).sortedBy { it.date }
            recyclerView.adapter = adapter
            recyclerView.scrollToPosition(adapter.messages.size - 1)
        }
    }

    private fun getCurrentDateFormatted(): String {
        val current = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(current)
    }
}
