package com.jensraaby.finagle

import com.twitter.finagle.Http
import com.twitter.finagle.http.{RequestBuilder, Response}
import com.twitter.util.{Await, Future}

object BasicClient extends App {

  val client = Http.client.newService("localhost:8080")

  val request = RequestBuilder().url("http://localhost/").buildGet()
  val response: Future[Response] = client(request)

  Await.result {
    response.map(resp => println("\n\nRESPONSE: " + resp.contentString + "\n\n"))
  }

}
