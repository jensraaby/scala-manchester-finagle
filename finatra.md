## Finatra
- Finagle alone is very powerful
- But leaves a lot of work for the application developer
- Finatra is a simple framework based on Finagle
 - Controllers
 - Default filters for logging, adding response headers etc.
 - Templating with Mustache
 - Guice for Dependency Injection


## Finch
- Finch is a purely functional library for building APIs
- https://github.com/finagle/finch

```
import io.finch._
import com.twitter.finagle.Http

val api: Endpoint[String] = get("hello") { Ok("Hello, World!") }

Http.server.serve(":8080", api.toServiceAs[Text.Plain])
```
