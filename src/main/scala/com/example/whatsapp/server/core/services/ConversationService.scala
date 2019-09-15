package com.example.whatsapp.server.core.services

import java.io.{BufferedReader, File, FileReader}

import com.example.whatsapp.server.core.dto.{Conversation, Message}
import com.example.whatsapp.server.core.mapper.DefaultObjectMapper
import com.example.whatsapp.server.core.parsers.ChatFileParser
import org.springframework.stereotype.Service

@Service
class ConversationService() {

  def extractConversation = {
    val chatFile = new BufferedReader(new FileReader(new File(ConversationService.chatFile)))
    // TODO: Get the name of the conversation not hardcoded but from file or cookie
    val chatFileParser = new ChatFileParser("Pini")
    val objectMapper = new DefaultObjectMapper()

    chatFileParser.parseChatFile(chatFile)


    val firstMessage = Message(
      1535703316000L,
      "Pini",
      "Hi")

    val secondMessage = Message(
      1535703376000L,
      "Barak",
      "Hi, how are you?")


    val thirdMessage = Message(
      1535703436000L,
      "Pini",
      "Fine!")

    val map = Map(
      1535703316000L ->
        Seq(firstMessage, secondMessage, thirdMessage))

    objectMapper.writeValueAsString(Seq(Conversation(null, null, map)))
  }

}

object ConversationService {
  val chatFile = "resources/_chat - Pini.txt"
  val dateRegex = "^\\[[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{2,4}, [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}]".r

  def apply(): ConversationService = new ConversationService()

}
