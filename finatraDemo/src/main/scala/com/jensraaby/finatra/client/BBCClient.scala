package com.jensraaby.finatra.client


import com.twitter.finagle.Http
import com.twitter.finatra.httpclient.RequestBuilder
import com.twitter.util.Future
import com.twitter.conversions.time._

class BBCClient {
  private val client = Http.client
    .withRequestTimeout(10.seconds)
    .newService("feeds.bbci.co.uk:80")

  def news: Future[String] = {
    val request = RequestBuilder.get("http://feeds.bbci.co.uk/news/rss.xml")
    client(request).map(_.contentString)
  }
}
