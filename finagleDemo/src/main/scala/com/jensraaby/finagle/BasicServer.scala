package com.jensraaby.finagle

import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.{Await, Future}


object BasicServer extends App {

  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] = {

      val resp = http.Response(req.version, http.Status.Ok)
      resp.contentString = "hello"

      Future.value(resp)
    }
  }

  val server = Http.server.serve(":8080", service)
  Await.ready(server)

}
