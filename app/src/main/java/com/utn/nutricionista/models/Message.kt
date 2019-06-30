package com.utn.nutricionista.models

import java.text.SimpleDateFormat
import java.util.*

data class Message(val id: String?, val sender: String, val text: String, val date: Date, val status: MessageStatus, val own: Boolean) {
    fun getMessageForPost(): Message {
        return Message(null, this.sender, this.text, this.date, MessageStatus.SENT, this.own)
    }

    fun getErrorMessage(): Message {
        return Message(null, this.sender, this.text, this.date, MessageStatus.ERROR, this.own)
    }

    fun getFormattedDate(): String {
        val format = SimpleDateFormat("yyyy/MM/dd hh:mm")
        return format.format(date)
    }
}

enum class MessageStatus(val status: String) {
    SENT("sent"),
    SENDING("sending"),
    ERROR("error")
}