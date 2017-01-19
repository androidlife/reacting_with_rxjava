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
 * Created by laaptu on 1/9/17.
 */

public class ProducerConsumerFragment extends Fragment {
  public ProducerConsumerFragment() {

  }

  public static ProducerConsumerFragment getInstance(Bundle params) {
    ProducerConsumerFragment producerConsumerFragment = new ProducerConsumerFragment();
    producerConsumerFragment.setArguments(params);
    return producerConsumerFragment;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    basicSubscribeTest();
  }

  private void basicSubscribeTest() {

    Action1<String> subscriber1 = new Action1<String>() {
      @Override public void call(String s) {
        System.out.println("First subscriber onNext() = " + s);
      }
    };
    Subscriber<String> subscriber2 = new Subscriber<String>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(String s) {
        System.out.println("Second subscriber onNext() = " + s);
      }
    };
    Subscription subscription = stringObservable.subscribe(subscriber1);
    Subscription subscription1 = stringObservable.subscribe(subscriber2);
  }

  private void basicSubscribeTestWithLambda() {
    Action1<String> subscriber1 = s -> System.out.println("First subscriber onNext() = " + s);
    Subscriber<String> me = new Subscriber<String>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {

      }

      @Override public void onNext(String s) {
        System.out.println("New message from MKBHD =  " + s);
      }
    };
    Subscription subscription = channelMKBHD.subscribe(subscriber1);
    Subscription subscription1 = channelMKBHD.subscribe(me);
  }

  /**
   * This is just an Observable creation RxJava says not to create Observable by own i.e. rely on
   * the libraries and if you have to do it, you have to manage it. Need to test this as well
   */
  /**
   * Main Player: Observable = producer, which produces something So its task may be doing something
   * like fetching data,performing calculations If some work is done and if it has subscribers to it
   * it can call its subscriber with any of the methods like onCompleted(), onError(), onNext()
   * Subscriber will receive the message from Observable and to receive the message, subscriber has
   * 3 interfaces onCompleted(),onError(), onNext() New messages or outputs are passed from
   * observable(producer) to consumer( subscriber) from onNext()
   */
  private Observable<String> stringObservable = Observable.create(
      /**
       * Does this mean, this Observable will immediately call the
       * subscribers, once they are subscribed*/
      new Observable.OnSubscribe<String>() {
        @Override public void call(Subscriber<? super String> subscriber) {
          subscriber.onNext("Observable sending first message");
          new Handler().postDelayed(new Runnable() {
            @Override public void run() {
              subscriber.onNext("Observable sending second message");
            }
          }, 1000);
        }
      });

  private Observable<String> channelMKBHD = Observable.create(s -> {
    s.onNext("I have a new video on my channel. Please watch it");
    new Handler().postDelayed(() -> {
      s.onNext("Producer sending second message");
    }, 1000);
  });
}
