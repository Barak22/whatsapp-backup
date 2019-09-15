package com.example.whatsapp.server.web.serverboot

import com.example.whatsapp.server.core.mapper.DefaultObjectMapper
import com.example.whatsapp.server.core.services.{ConversationService, UploaderService}
import com.example.whatsapp.server.web.controllers.ConversationController
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders._
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class ConversationControllerTest extends Specification with Mockito {


  trait Context extends Scope {
    val uploadService = mock[UploaderService]
    val conversationService = mock[ConversationService]
    val mapper = new DefaultObjectMapper()
    val controller = new ConversationController(conversationService, uploadService)
    val mockMvc = MockMvcBuilders.standaloneSetup(controller)
      .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper)).build
    val mockFile = new MockMultipartFile(
      "file",
      "content.txt",
      "application/txt",
      Array[Byte]())

  }


  "ConversationController" should {
    "upload file succeeded" in new Context {

      val builder = multipart("/upload")
        .file(mockFile)
        .contentType("application/zip")
      mockMvc.perform(builder).andExpect(status.is(200))
    }
  }


}
