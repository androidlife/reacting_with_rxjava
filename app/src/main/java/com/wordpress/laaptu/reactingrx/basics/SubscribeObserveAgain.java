package com.wordpress.laaptu.reactingrx.basics;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.wordpress.laaptu.reactingrx.R;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * There is only one subscribeOn(), though you can write many subscribeOn(), the only one written
 * first will be used If no subscribeOn and observeOn(), all the methods will be called in
 * mainthread( actually it will be called in the thread, where that statement is written)
 * observeOn() means the method of observers should be called on which thread
 * observeOn(), onCompleted(), onError(), onNext()
 * or simply contains onNext(),  then the thread will run on those methods respectively
 */
public class SubscribeObserveAgain extends Fragment {

  TextView txtInfo;

  public SubscribeObserveAgain() {
  }

  public static SubscribeObserveAgain getInstance(Bundle params) {
    SubscribeObserveAgain fragment = new SubscribeObserveAgain();
    fragment.setArguments(params);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_subscribeobserve, container, false);
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    txtInfo = (TextView) getView().findViewById(R.id.txt_info);
    txtInfo.setText("This is basic test of reactive");
    subscribeObserveTest();
  }

  private void subscribeObserveTest() {
    Subscriber<String> subscriber = new Subscriber<String>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        System.out.println("Error from observable " + e.getMessage());
        //printMessageFromSubscribe("Error");
      }

      @Override public void onNext(String s) {
        printMessageFromSubscribe(s);
      }
    };
    Subscription subscription = firstObservable.subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(Schedulers.io())
        .doOnError(throwable -> {
          System.out.println("Error e = " + throwable.getMessage());
        })
        .subscribe(subscriber);
    subscription.unsubscribe();
  }

  private void printMessageFromSubscribe(String s) {
    System.out.println("Got message from observable: " + s);
    txtInfo.setText(s);
    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
  }

  private Observable<String> firstObservable = Observable.create(subscriber -> {
    subscriber.onNext("Hello subscriber");
    subscriber.onNext("Hi again subscriber");
    subscriber.onError(new Throwable("This is error"));
  });
  private Observable<String> secondObservable = Observable.fromCallable(() -> {
    return "";
  });
}
