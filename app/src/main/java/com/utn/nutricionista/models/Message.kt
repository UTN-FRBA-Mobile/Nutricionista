package com.utn.nutricionista.models

import java.time.LocalDateTime

data class Message(val sender: String, val text: String, val date: LocalDateTime, val status: String, val own: Boolean)
