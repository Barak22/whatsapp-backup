package com.example.whatsapp.server.web.controllers

import com.example.whatsapp.server.core.services.{ConversationService, UploaderService}
import org.springframework.web.bind.annotation._
import org.springframework.web.multipart.MultipartHttpServletRequest

@RestController
class ConversationController(conversationService: ConversationService,
                             uploaderService: UploaderService) {

  @CrossOrigin
  @GetMapping(Array("/conversation"))
  def conversation() = conversationService.extractConversation

  @CrossOrigin
  @PostMapping(path = Array("/upload"), produces = Array("application/zip"))
  def upload(request: MultipartHttpServletRequest) = uploaderService.upload(request)
}
