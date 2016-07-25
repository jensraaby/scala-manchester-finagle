package com.jensraaby.finagle

import com.twitter.finagle.http.{Request, RequestBuilder, Response}
import com.twitter.finagle.{Http, Service, SimpleFilter}
import com.twitter.util.{Await, Future}


object FiltersDemo extends App {

  val localFinatraClient = Http.client.newService("localhost:8888")
  val googleClient = Http.client.newService("www.google.co.uk:80")


  val loggingFilter = new SimpleFilter[Request, Response] {
    override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
      println("REQUEST: " + request.encodeString())
      val resp = service(request)
      resp.onSuccess(response => println("RESPONSE: " + response.encodeString()))
      resp
    }
  }


  def hostFilter(hostname: String) = new SimpleFilter[Request, Response] {
    override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
      request.host = hostname
      println(request.encodeString())
      service(request)
    }
  }

  val googleClientWithHostHeader = hostFilter("www.google.co.uk") andThen googleClient
  val finatraClientWithLogging = loggingFilter andThen localFinatraClient


  def printResponse(url: String, service: Service[Request, Response]) = {

    val request = RequestBuilder().url(url).buildGet()
    val response: Future[Response] = service(request)

    Await.result {
      response.map(resp => println("\n\nRESPONSE: " + resp.contentString.take(200) + "\n\n"))
    }
  }


  printResponse("http://localhost/hello/FooBar", finatraClientWithLogging)
  printResponse("http://www.google.co.uk/?s=finagle", googleClientWithHostHeader)

}
