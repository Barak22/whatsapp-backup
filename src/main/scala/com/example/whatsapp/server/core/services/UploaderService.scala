package com.example.whatsapp.server.core.services

import java.io.File
import java.util.zip.{ZipEntry, ZipInputStream}

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.{ObjectMetadata, PutObjectRequest}
import com.example.whatsapp.server.core.services.UploaderService.{bucketName, s3Client}
import org.springframework.stereotype.Service
import org.springframework.web.multipart.{MultipartFile, MultipartHttpServletRequest}

import scala.annotation.tailrec

@Service
class UploaderService {
  def upload(request: MultipartHttpServletRequest): Unit = {
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
    das
    goOverZipFile(zipFile)
  }

  def handleZipEntry(zipEntry: ZipEntry): Unit =
  // TODO: Upload the files to a cloud storage
    if (zipEntry.getName.equals("_chat.txt")) /*uploadTextFile(zipEntry)*/ ???
    else "upload attachmentFile"

  def uploadTextFile(zipEntry: ZipEntry): Unit = {
    val file = new File(zipEntry.getName)
    val req = new PutObjectRequest(bucketName, zipEntry.getName, file)
    val metadata = new ObjectMetadata
    metadata.setContentType("plain/text")
    metadata.addUserMetadata("x-amz-meta-title", "someTitle")
    req.setMetadata(metadata)
    s3Client.putObject(req)
  }

}

object UploaderService {
  val clientRegion = Regions.DEFAULT_REGION
  val bucketName = "whatsapp-backup";

  val s3Client = AmazonS3ClientBuilder.standard()
    .withCredentials(new ProfileCredentialsProvider())
    .withRegion(clientRegion)
    .build()

  //  val listRequest = new ListObjectsRequest().withBucketName(bucketName).withMaxKeys(2)
  //  val file = new File("/Users/barakm/java_error_in_idea_52682.log")
  //  val req = new PutObjectRequest(bucketName, "firstDir/firstFile", file)

  //  val metadata = new ObjectMetadata
  //  metadata.setContentType("plain/text")
  //  metadata.addUserMetadata("x-amz-meta-title", "someTitle")
  //  req.setMetadata(metadata)
  //  s3Client.putObject(req)
  //  var objects = s3Client.listObjects(listRequest)


  //  while (!objects.isTruncated) {
  //    val summaries = objects.getObjectSummaries;
  //    summaries.forEach {
  //      summary =>
  //        println(s"Object '${summary.getKey}' retrieved with size ${summary.getSize}\n");
  //    }
  //    if (!objects.isTruncated) {
  //      objects = s3Client.listNextBatchOfObjects(objects)
  //    }
  //  }
}
