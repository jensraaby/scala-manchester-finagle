package com.jensraaby.finatra

import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.{Header, QueryParam}


case class FancyRequest(@QueryParam name: String, @Header accept: String)

case class FancyResponse(name: String, accept: String)


class FancyController extends Controller {

  get("/fancy") { f: FancyRequest =>
    FancyResponse(f.name, f.accept)
  }

  get("/fancy/optional") { f: FancyRequest =>
    if (f.name.startsWith("w"))
      Some(FancyResponse(f.name, f.accept))
    else
      None
  }
}
