## What is Finagle?


## An RPC Library

- Protocol agnostic
  - there are implementations for HTTP, MySQL, Memcached, Thrift....
- Most important primitive is the Service
- Services are just functions:

```
trait Service[-Req, +Rep] extends (Req => Future[Rep])
```


## Minimum viable Service

```
val service = new Service[http.Request, http.Response] {
  def apply(req: http.Request): Future[http.Response] =
    Future.value(
      http.Response(req.version, http.Status.Ok)
    )
}
```
- This returns a 200 response whatever request is received


## Exporting our service

- We expose our service on the HTTP protocol on port 8080:
```
val server = Http.serve(":8080", service)
Await.ready(server)
```


## Creating and using clients
```
val client: Service[http.Request, http.Response] =
                        Http.newService("localhost:8080")

val request = http.Request(http.Method.Get, "/")
request.host = "localhost"

val response: Future[http.Response] = client(request)
```


## Demo
