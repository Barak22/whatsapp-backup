package com.example.whatsapp.server.core.dto

import com.example.whatsapp.server.core.domains.Domains.Day

case class Message(date: Day, from: String, content: String)

object Message {
  def empty() = Message(0L, "", "")
}
