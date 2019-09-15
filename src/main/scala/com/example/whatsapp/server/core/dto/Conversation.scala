package com.example.whatsapp.server.core.dto

case class Conversation(name: String, lastMessage: Message, messages: Map[Long, Seq[Message]])
