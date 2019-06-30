package com.utn.nutricionista.Messages

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.utn.nutricionista.R
import com.utn.nutricionista.inflate
import com.utn.nutricionista.models.Message
import com.utn.nutricionista.models.MessageStatus
import kotlinx.android.synthetic.main.recyclerview_own_message.view.*

class MessagesRecyclerAdapter(var messages: List<Message>):  RecyclerView.Adapter<MessagesRecyclerAdapter.MessageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        if (viewType == 1) {
            val inflatedView = parent.inflate(R.layout.recyclerview_own_message, false)
            return MessageHolder(inflatedView)
        } else {
            val inflatedView = parent.inflate(R.layout.recyclerview_others_message, false)
            return MessageHolder(inflatedView)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        if (messages[position].own) {
            return 1
        } else {
            return 2
        }
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val itemMessage = messages[position]
        holder.bindMessage(itemMessage)
    }

    class MessageHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var message: Message? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {

        }

        fun bindMessage(message: Message) {
            this.message = message
            view.itemUserName.text = message.sender + ":"
            view.itemUserName.setTypeface(null, Typeface.BOLD)
            view.itemText.text = message.text
            view.itemDate.text = message.getFormattedDate()
            when(message.status) {
                MessageStatus.SENT -> view.itemStatusImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        view.context, // Context
                        R.drawable.icon_checkmark // Drawable
                    )
                )
                MessageStatus.SENDING -> view.itemStatusImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        view.context, // Context
                        R.drawable.ic_clock // Drawable
                    )
                )
                MessageStatus.ERROR -> view.itemStatusImage.setImageDrawable(
                    ContextCompat.getDrawable(
                        view.context, // Context
                        R.drawable.ic_cross // Drawable
                    )
                )
            }

            if (!message.own) {
                view.itemStatusImage.imageAlpha = 0
            }

        }

    }
}
