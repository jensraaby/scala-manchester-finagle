## What is Finagle?


## An RPC Library

- Protocol agnostic
  - there are implementations for HTTP, MySQL, Memcached, Thrift....
- Most important primitive is the Service
- Services are just functions:

```
trait Service[-Req, +Rep] extends (Req => Future[Rep])
```


## Minimum Viable Service

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

- We can expose our service on the HTTP protocol on port 8080:
```
val server = Http.serve(":8080", service)
Await.ready(server)
```


## Creating and using clients

- Clients maintain a session with one or more replicas
- Making a request:

```
val client: Service[http.Request, http.Response] =
                        Http.newService("localhost:8080")

val request = http.Request(http.Method.Get, "/")
request.host = "localhost"

val response: Future[http.Response] = client(request)

```


## Filters
- You can decorate a service with a Filter
- Filters can act on requests or responses, independently of the service
```
abstract class Filter[-ReqIn, +RepOut, +ReqOut, -RepIn]
  extends ((ReqIn, Service[ReqOut, RepIn]) => Future[RepOut])
```


## Composing
- You create a filtered service using the "andThen" combinator:

```
val timeoutFilter = new Filter[http.Request, http.Response,
                               http.Request, http.Response](...)

val serviceWithTimeout: Service[http.Request, http.Response] =
  timeoutFilter andThen service

```
