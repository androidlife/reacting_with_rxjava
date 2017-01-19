Q: What is RxJava?

A: RxJava is simply a Java library based upon reactive programming and reactive streams. So, this library is based upon some specifications and some architecture defined by reactive programming and streams.

Q: Then what is reactive programming?

A: As per this [youtube video on reactive programming](https://www.youtube.com/watch?v=dwP1TNXE6fc), it is combination of [Observer Pattern](https://en.wikipedia.org/wiki/Observer_pattern) and [Iterator Pattern](https://en.wikipedia.org/wiki/Iterator_pattern). 

Q: What is reactive streams?

A: It is simply a [standard specification](http://www.reactive-streams.org/) to be followed for asynchronous stream processing.


This was all enough for me to not to continue into RxJava. Understanding the terminologies is in itself so boring, how to implement it.

#### Let's focus on simplicity

Observer pattern understanding:

```
I watch a lot of youtube videos
I subscribe to a channel of my liking like MKBHD or LinusTechTips. 
Whenever a new video is published by that channel, I get notification.
Later, if I don't like the channel, I will unsubscribe
it.
Then I won't receive any notifications from that channel.
``` 

So for an Observer pattern to work, there needs to be

```
Youtube channels = Producers
Me = Subscriber
I can subscribe = fn(subscribe)
I can unsubscrbe = fn(unsubcribe)
I get notifications = fn(notifyme)
```

So, just like me,there are lot of other subscribers to a single channel and that channel needs to notify all the subscribers.

Let's map this to RxJava

```
Observable = ( Youtube Channel)
Subscriber = ( Me )
Observable.subscribe = Subscription = fn(subscribe)
Subscription.unsubscribe = fn(unsubscribe)
Observer callbacks onNext(),onError(),onCompleted() = notifications = fn(notify me)
```

Let's look all those in a single code,then

```
private Observable<String> channelMKBHD = Observable.create(
s->{
    s.onNext("I have a new video on my channel.
     Please watch it");
  });
Subscriber<String> me = new Subscriber<String>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(String s) {
        System.out.println("New message from MKBHD =  " + s);
      }
    };  
Subscription mySubscription = channelMKBHD.subscribe(me);    
```
     

