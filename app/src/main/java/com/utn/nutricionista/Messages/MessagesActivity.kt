package com.utn.nutricionista.Messages

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utn.nutricionista.ApiClient
import com.utn.nutricionista.R
import com.utn.nutricionista.models.Message
import com.utn.nutricionista.models.MessageStatus
import kotlinx.android.synthetic.main.activity_messages.*
import java.text.SimpleDateFormat
import java.util.*

class MessagesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapterMessages: MessagesRecyclerAdapter

    private lateinit var timer: Timer
    private val fiveSeconds = 10000L

    override fun onResume() {
        super.onResume()

        val timerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    getNewMessages()
                }
            }
        }

        timer = Timer()
        timer.schedule(timerTask, fiveSeconds, fiveSeconds)
    }

    override fun onPause() {
        super.onPause()

        timer.cancel()
        timer.purge()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val messages = ArrayList<Message>()
        adapterMessages = MessagesRecyclerAdapter(messages)
        recyclerView.adapter = adapterMessages
        recyclerView.scrollToPosition(adapterMessages.messages.size - 1)
        getMessages()
    }

    private fun getNewMessages() {
        ApiClient.getMessages().addOnSuccessListener {
            Log.d("SUCCESS", "SWEET, SWEET SUCCESS!")
            val messages = it
            val newMessages = messages.filter { !adapterMessages.messages.contains(it) }
            if (newMessages.count() > 0) {
                adapterMessages.messages = (adapterMessages.messages + newMessages).sortedBy { it.date }
                recyclerView.scrollToPosition(adapterMessages.messages.size - 1)
            }
        }.addOnFailureListener { e ->
            Log.d("FAILURE", "GASP! SOMETHING WENT WRONG: ${e.message}")
        }
    }

    private fun getMessages() {
        ApiClient.getMessages().addOnSuccessListener {
            Log.d("SUCCESS", "SWEET, SWEET SUCCESS!")
            val messages = it
            adapterMessages = MessagesRecyclerAdapter(messages.sortedBy { it.date })
            recyclerView.adapter = adapterMessages
            recyclerView.scrollToPosition(adapterMessages.messages.size - 1)
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
        adapterMessages.messages = (adapterMessages.messages + newMessage).sortedBy { it.date }
        recyclerView.adapter = adapterMessages
        recyclerView.scrollToPosition(adapterMessages.messages.size - 1)
        textfield.setText("")
        ApiClient.postMessage(newMessage.getMessageForPost()).addOnSuccessListener {
            val postedMessage = it
            val removedWaitingMessage = adapterMessages.messages.filter {
                !it.equals(newMessage)
            }
            adapterMessages.messages = (removedWaitingMessage + postedMessage).sortedBy { it.date }
            recyclerView.adapter = adapterMessages
            recyclerView.scrollToPosition(adapterMessages.messages.size - 1)
        }.addOnFailureListener { e ->
            val removedWaitingMessage = adapterMessages.messages.filter {
                !it.equals(newMessage)
            }
            adapterMessages.messages = (removedWaitingMessage + newMessage.getErrorMessage()).sortedBy { it.date }
            recyclerView.adapter = adapterMessages
            recyclerView.scrollToPosition(adapterMessages.messages.size - 1)
        }
    }

    private fun getCurrentDateFormatted(): String {
        val current = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(current)
    }
}
