package com.example.whatsapp.server.core.parsers

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object DateParser {
  def parseChatDateToMillis(date: String): Long =
    parseChatDateToDate(date).getMillis

  def parseChatDateToDate(date: String): DateTime =
    DateTime.parse(date, DateTimeFormat.forPattern("dd/MM/yyyy, HH:mm:ss"))

}
