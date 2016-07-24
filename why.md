## Why?


## Does anyone remember this?

<figure>
<img src="http://www.yiyinglu.com/wp-content/uploads/2013/11/lifting-a-dreamer-2009.jpg" />
<figcaption>Source: [http://www.yiyinglu.com/?portfolio=lifting-a-dreamer-aka-twitter-fail-whale](http://www.yiyinglu.com/?portfolio=lifting-a-dreamer-aka-twitter-fail-whale)</figcaption>
</figure>


## Twitter was a Rails monolith

- millions of users
- Ruby not the best at that scale
- (Micro)services were the way forward


## Enter Finagle
- Simplicity
- Composability
- Separation of Concerns


## Example: Filters
- You can decorate a service with a Filter
- Filters can act on requests or responses, independently of the service
```
abstract class Filter[-ReqIn, +RepOut, +ReqOut, -RepIn]
  extends ((ReqIn, Service[ReqOut, RepIn]) => Future[RepOut])
```


## Filter
- You create a filtered service using the "andThen" combinator:

```
val timeoutFilter = new Filter[http.Request, http.Response,
                               http.Request, http.Response](...)

val serviceWithTimeout: Service[http.Request, http.Response] =
  timeoutFilter andThen service

```
