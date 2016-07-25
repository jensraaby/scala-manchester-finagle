## Advanced features

- Finagle is designed for large scale distributed systems
- It does things on the session level (OSI layer 5)
- You have to determine semantics of HTTP 500 responses


## Clients

- A lot of the 'magic' is in clients
- Timeouts and retries (with exponential backoff)
- Load balancing (P2C, EWMA)
- Builder API:

```
val client = Http.client
  .withTransport.tls(hostname)
  .withRequestTimeout(2.seconds)
  .withLabel("searchAPI")
  .withSessionQualifier.noFailFast
  .newService("search.eu-west-1.mycompany.com:443,search.eu-central-1.mycompany.com:443")
```


## Servers

- Optimised for high throughput
- You can limit load
- Also has a Builder API:

```
val server = Http.server
         .withStreaming(true)
         .withAdmissionControl
            .concurrencyLimit(maxConcurrentRequests = 20,
                              maxWaiters = 10)
         .serve(":8080", httpService)
```
