package com.utn.nutricionista.Messages

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.utn.nutricionista.ApiClient
import com.utn.nutricionista.R
import com.utn.nutricionista.SessionManager
import com.utn.nutricionista.models.Message
import com.utn.nutricionista.models.MessageStatus
import kotlinx.android.synthetic.main.activity_messages.*
import java.text.SimpleDateFormat
import java.util.*

class MessagesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapterMessages: MessagesRecyclerAdapter

    private lateinit var timer: Timer
    private val tenSeconds = 10000L

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
        timer.schedule(timerTask, tenSeconds, tenSeconds)
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
        getNewMessages()
    }

    private fun getNewMessages() {
        ApiClient.getMessages().addOnSuccessListener {
            val messages = it
            val newMessages = messages.filter { !adapterMessages.messages.contains(it) }
            if (newMessages.count() > 0) {
                val updatedMessages = adapterMessages.messages + newMessages
                updateMessages(updatedMessages)
            }
        }.addOnFailureListener { e ->
            Log.d("FAILURE", "GASP! SOMETHING WENT WRONG: ${e.message}")
        }
    }

    fun buttonPressed(view: View) {
        var newMessage = getNewMessage()
        val messages = adapterMessages.messages + newMessage
        updateMessages(messages)
        emptyTextField()
        ApiClient.postMessage(newMessage.getMessageForPost()).addOnSuccessListener {
            val postedMessage = it
            val removedWaitingMessage = adapterMessages.messages.filter {
                !it.equals(newMessage)
            }
            val messages = removedWaitingMessage + postedMessage
            updateMessages(messages)
        }.addOnFailureListener { e ->
            val removedWaitingMessage = adapterMessages.messages.filter {
                !it.equals(newMessage)
            }
            val messages = removedWaitingMessage + newMessage.getErrorMessage()
            updateMessages(messages)
        }
    }

    private fun getNewMessage(): Message {
        return Message(
            null,
            getCurrentUserName(),
            textfield.text.toString(),
            Date(),
            MessageStatus.SENDING,
            own = true
        )
    }

    private fun emptyTextField() {
        textfield.setText("")
    }

    private fun updateMessages(newMessages: List<Message>) {
        adapterMessages.messages = (newMessages).sortedBy { it.date }
        recyclerView.adapter = adapterMessages
        recyclerView.scrollToPosition(adapterMessages.messages.size - 1)
    }

    private fun getCurrentUserName(): String {
        val name = SessionManager.currentUser?.displayName ?: ""
        return name.take(20)
    }
}
