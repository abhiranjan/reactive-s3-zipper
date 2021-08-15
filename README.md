# reactive-s3-zipper
Library to Zip files located in an s3 bucket and upload it to a path




`

      implicit val system: ActorSystem = ...
      implicit val materializer: ActorMaterializer = ...
      implicit val sampleAttributes: Attributes = ...
      
      val bucketRegion = <bucket-region>
      val bucketName = <my-bucket>
      val sourceFolder = <path-to-files-to-be-zipped>
      
      val zipPath = s"destination/<file-name>.zip"
      
      val s3Client = AmazonS3ClientBuilder.standard().withRegion(bucketRegion).build()
      val s3ObjectKeys = S3Objects.withPrefix(s3Client, bucketName, sourceFolder).asScala.toSeq.map(_.getKey)
      
      // we have files list now, let's create a source from these
      val s3FileSource = Source.fromIterator(() =>
        s3ObjectKeys.map { objKey =>
        
          // Get file name from key 
          val fName = objKey.substring(key.lastIndexOf("/") + 1)
          ZipToStreamFlow.ZipSource(fName, { () =>
            val obj = s3Client.getObject(bucketName, objKey)
            obj.getObjectContent
          })
        }.toIterator
    )
    
    // zipSink supports multi-part file upload uses Alpakka reactive s3 [[@link: https://doc.akka.io/docs/alpakka/current/s3.html#]]
    // but, you can write your own upload logic and create a Sink out of it.
    val zipSink: Sink[ByteString, Future[String]] = {
          akka.stream.alpakka.s3.scaladsl.S3
            .multipartUpload(bucketName, zipPath)
            .withAttributes(s3Attributes)
            .mapMaterializedValue(_.map(_ => zipPath))
    
    // we have Source and Sink let's run the graph now
    s3FileSource
     .via(ZipToStreamFlow(8192))
     // .via(call-monitoring-api-to-monitoring-the-progress-of-zipping)
     .runWith(zipSink)
     
                  
`
