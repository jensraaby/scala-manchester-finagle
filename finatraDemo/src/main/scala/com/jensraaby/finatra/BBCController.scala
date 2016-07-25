package com.jensraaby.finatra

import javax.inject.Inject

import com.jensraaby.finatra.client.BBCClient
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

import scala.xml.XML



class BBCController @Inject()(bbcClient: BBCClient) extends Controller {

  get("/news") { request: Request =>
    bbcClient.news.map { body =>
      val xml = XML.loadString(body)
      val items = xml \\ "item"

      response.ok.html(
        "<ul>" +
        items.map(item => <li>{(item \ "title").text}</li>)
        + "</ul>"
      )
    }
  }

}
