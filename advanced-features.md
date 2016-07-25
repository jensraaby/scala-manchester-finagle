## Advanced features

- Finagle is designed for large scale distributed systems
- It does things on the session level (OSI layer 5)
- You have to determine semantics of responses (e.g. HTTP 500)


## Clients

- A lot of the 'magic' is in clients
- Timeouts and retries (with exponential backoff)
- Load balancing (P2C, EWMA)
- Builder API:

```
val client = Http.client
  .withTransport.tls("search.mycompany.com")
  .withRequestTimeout(2.seconds)
  .withLabel("searchAPI")
  .withSessionQualifier.noFailFast
  .newService("search.eu-west-1.mycompany.com:443,
               search.eu-central-1.mycompany.com:443")
```


## Aside: Naming
- At Twitter, logical names or paths are usually provided rather than hostnames
- Zookeeper is used to register hosts and to resolve replica sets for clients


## Servers

- Optimised for high throughput
- You can limit load
- Also has a Builder API:

```
val server = Http.server
         .withAdmissionControl
            .concurrencyLimit(maxConcurrentRequests = 20,
                              maxWaiters = 10)
         .serve(":8080", httpService)
```
