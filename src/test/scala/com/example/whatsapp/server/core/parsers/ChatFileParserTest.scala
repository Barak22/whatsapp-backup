package com.example.whatsapp.server.core.parsers

import java.io.{BufferedReader, File, FileReader}

import com.example.whatsapp.server.core.dto.{Conversation, Message}
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class ChatFileParserTest extends Specification {

  trait Context extends Scope {
    val chatFileParser = new ChatFileParser("Pini")
  }

  "ChatFileParser" should {
    "parse empty file" in new Context {
      val emptyFile = new BufferedReader(
        new FileReader(
          new File("src/test/resources/emptyFile - Pini.txt")
            .getAbsolutePath))

      chatFileParser.parseChatFile(emptyFile) must beEqualTo(Conversation("Pini", Message.empty(), Map.empty))

      emptyFile.close()
    }

    "parse one chat with one line" in new Context {
      val regularChatFile = new BufferedReader(
        new FileReader(
          new File("src/test/resources/regularChatFile - Pini.txt")
            .getAbsolutePath))

      chatFileParser.parseChatFile(regularChatFile) must
        beEqualTo(
          Conversation(
            "Pini",
            Message(
              DateParser.parseChatDateToMillis("31/08/2018, 8:33:38"),
              "Barak " +
                "Marziano", "תבוא תבוא " +
                "יהיה " +
                "בסדר"),
            Map(
              DateParser.parseChatDateToMillis("30/08/2018, 00:00:00") -> Seq(
                Message(
                  DateParser.parseChatDateToMillis(
                    "30/08/2018, 22:42:39"),
                  "Pini",
                  "https://devrant.com/rants/1684566/some-empty-headed-helpdesk-girl-skipped-into-our-office" +
                    "-yesterday-afternoon-desp"),
                Message(
                  DateParser.parseChatDateToMillis("30/08/2018, 22:59:00"), "Barak Marziano", "חחח")
              ),
              DateParser.parseChatDateToMillis("31/08/2018, 00:00:00") -> Seq(
                Message(
                  DateParser.parseChatDateToMillis("31/08/2018, 8:33:22"), "Pini", "סבבה :)"),
                Message(
                  DateParser.parseChatDateToMillis("31/08/2018, 8:33:38"),
                  "Barak Marziano",
                  "תבוא תבוא יהיה בסדר")))))

      regularChatFile.close()
    }
  }

}
