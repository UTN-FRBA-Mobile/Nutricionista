package com.utn.nutricionista

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_message.view.*

class RecyclerAdapter(val messages: ArrayList<Message>):  RecyclerView.Adapter<RecyclerAdapter.MessageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.MessageHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_message, false)
        return MessageHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.MessageHolder, position: Int) {
        val itemPhoto = messages[position]
        holder.bindPhoto(itemPhoto)
    }

    class MessageHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var message: Message? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {

        }

        fun bindPhoto(message: Message) {
            this.message = message
            view.itemUserName.text = message.sender + ":"
            view.itemUserName.setTypeface(null, Typeface.BOLD)
            view.itemText.text = message.text
            view.itemDate.text = message.date
        }

    }
}

data class Message(val sender: String, val text: String, val date: String, val status: String)