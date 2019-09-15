package com.example.whatsapp.server.core.mapper

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

class DefaultObjectMapper extends ObjectMapper {

  registerModule(new DefaultScalaModule)
  configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}
