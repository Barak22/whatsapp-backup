package com.example.whatsapp.server.core.parsers

import java.io.BufferedReader
import java.util.stream.Collectors

import com.example.whatsapp.server.core.dto.{Conversation, Message}
import com.example.whatsapp.server.core.services.ConversationService
import jdk.nashorn.internal.parser.DateParser
import org.joda.time.DateTime

import scala.annotation.tailrec
import scala.collection.JavaConverters._

class ChatFileParser(conversationName: String) {
  val x = 3

  def parseChatFile(chatFile: BufferedReader): Conversation = {
    val messages: Seq[(DateTime, Message)] = parseChatFileHelper(
      chatFile.lines().collect(Collectors.toList()).asScala,
      Nil)

    if (messages.isEmpty) Conversation(conversationName, Message.empty(), Map.empty)
    else {
      val s = messages
        .groupBy(e => e._1.toLocalDate.toDate.getTime)
        .map(e => e._1 -> e._2.map(eMessage => eMessage._2))
      Conversation(conversationName, messages.last._2, s)
    }
  }

  @tailrec
  final def parseChatFileHelper(lines: Seq[String], result: Seq[(DateTime, Message)]): Seq[(DateTime, Message)] = {
    if (lines.isEmpty) result
    else {
      val messageOption = lines.head match {
        case line if ConversationService.dateRegex.findFirstMatchIn(line).nonEmpty => parseRegularLine(line)
      }

      if (messageOption.isEmpty) throw new RuntimeException("Couldn't parse line as message!")
      else parseChatFileHelper(lines.tail, result :+ messageOption.get)
    }
  }


  def parseRegularLine(line: String): Option[(DateTime, Message)] = {
    for {
      rawDate <- ConversationService.dateRegex.findFirstMatchIn(line)
        .map(_.toString())
        .map(_.replaceFirst("\\[", ""))
        .map(_.replaceFirst("\\]", ""))
      rawData <- ConversationService.dateRegex.findFirstMatchIn(line).map(_.after.toString.trim)
    } yield {
      val senderName = rawData.takeWhile(!_.equals(':'))
      val message = rawData.dropWhile(!_.equals(':')).replaceFirst(":", "").trim
      val date = DateParser.parseChatDateToDate(rawDate)
      date -> Message(date.getMillis, senderName, message)
    }
  }
}
