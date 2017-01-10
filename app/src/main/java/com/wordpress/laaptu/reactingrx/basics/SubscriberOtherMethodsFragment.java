package com.wordpress.laaptu.reactingrx.basics;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * What happens to the subscription, when we call onCompleted() and onError() We found that onNext()
 * is for passing the output, our subscriber is eagerly subscribed for onError() means notifying the
 * subscriber about error. Once error message is transmitted, the subscriber is unsubscribed For
 * onError() to work, a subscriber needs to have that method implemented
 * basicSubscribeTestWithLambda() subscriber1 doesn't have onError() implemented, it has only ,
 * onNext() method and onCompleted() implemented ( maybe in the main interface definition, need to
 * look later on) So if the producer or observable calls onError(), then the app will crash for
 * subscriber1, onError() will work only for subscriber2, So onError() means unsubscribe with error
 * message .. onCompleted() means simply unsubscribe() So if the observable or producer calls
 * onCompleted(), onError() , the subscriber will be unsubscribed i.e. it will now not receive any
 * onNext() calls
 */

public class SubscriberOtherMethodsFragment extends Fragment {
  public SubscriberOtherMethodsFragment() {

  }

  public static SubscriberOtherMethodsFragment getInstance(Bundle params) {
    SubscriberOtherMethodsFragment
        subscriberOtherMethodsFragment = new SubscriberOtherMethodsFragment();
    subscriberOtherMethodsFragment.setArguments(params);
    return subscriberOtherMethodsFragment;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    basicSubscribeTestWithLambda();
  }

  private void basicSubscribeTestWithLambda() {
    Action1<String> subscriber1 = s -> System.out.println("First subscriber onNext() = " + s);
    Subscriber<String> subscriber2 = new Subscriber<String>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(String s) {
        System.out.println("Second subscriber onNext() = " + s);
      }
    };
    Subscription subscription = stringObservable1.subscribe(subscriber1);
    Subscription subscription1 = stringObservable1.subscribe(subscriber2);
  }

  private Observable<String> stringObservable1 = Observable.create(s -> {
    s.onNext("Producer sending first message");
    //s.onError(null);
    s.onCompleted();
    /**
     * Once onError() or onCompleted() of subscriber is called
     * the observable can't call onNext() of the subsriber i.e.
     *  the subscriber will be unsubcribed*/
    new Handler().postDelayed(() -> {
      s.onNext("Producer sending second message");
    }, 1000);
  });
}
