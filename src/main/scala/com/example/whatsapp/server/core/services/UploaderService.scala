package com.example.whatsapp.server.core.services

import java.util.zip.{ZipEntry, ZipInputStream}

import org.springframework.stereotype.Service
import org.springframework.web.multipart.{MultipartFile, MultipartHttpServletRequest}

import scala.annotation.tailrec

@Service
class UploaderService {
  def upload(request: MultipartHttpServletRequest) = {
    @tailrec
    def goOverZipFile(zipFile: ZipInputStream): Unit = {
      val e = zipFile.getNextEntry
      if (e == null) Unit
      else {
        handleZipEntry(e)
        goOverZipFile(zipFile)
      }
    }

    val file: MultipartFile = request.getFile(request.getFileNames.next)
    val zipFile: ZipInputStream = new ZipInputStream(file.getInputStream)
    goOverZipFile(zipFile)
  }

  def handleZipEntry(zipEntry: ZipEntry): Unit =
  // TODO: Upload the files to a cloud storage
    if (zipEntry.getName.equals("_chat.txt")) "upload text file"
    else "upload attachmentFile"
}
