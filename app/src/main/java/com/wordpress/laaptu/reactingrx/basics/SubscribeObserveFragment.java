package com.wordpress.laaptu.reactingrx.basics;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wordpress.laaptu.reactingrx.R;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * So far what I found is that
 * subscribeOn() is only once, no matter how many you add
 * Need to find out which one gets called
 * subscribeOn() doesn't mean that Observable() action
 *  like fetching data will one the defined thread
 *  Instead it defines in which thread the result needs to be passed
 *  or simply, the thread for the interface
 *  call(), of the OnSubscribe, any function called from
 *  call() method will be on that thread, until and unless
 *  observeOn() is called
 */

public class SubscribeObserveFragment extends Fragment {

  TextView txtInfo;

  public SubscribeObserveFragment() {

  }

  public static SubscribeObserveFragment getInstance(Bundle params) {
    SubscribeObserveFragment
        subscribeObserveFragment = new SubscribeObserveFragment();
    subscribeObserveFragment.setArguments(params);
    return subscribeObserveFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_subscribeobserve, container, false);
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    txtInfo = (TextView) getView().findViewById(R.id.txt_info);
    txtInfo.setText("This is basic test of reactive");
    basicSubscribeTestWithLambda();
    //subscribeTest();
  }

  private void subscribeTest() {
    someObservable.subscribeOn(Schedulers.io()).subscribe(subscriber);
    new Handler().postDelayed(()-> customOnSubscribe.doSomeTask(),3000);
  }

  private CustomOnSubscribe customOnSubscribe = new CustomOnSubscribe();

  private Observable<String> someObservable = Observable.create(customOnSubscribe);

  Subscriber<String> subscriber = new Subscriber<String>() {
    @Override public void onCompleted() {

    }

    @Override public void onError(Throwable e) {
      System.out.println("Error encountered " + e.getMessage());
    }

    @Override public void onNext(String s) {
      System.out.println("Subscriber onNext() = " + s);
      txtInfo.setText(s);
    }
  };

  private void basicSubscribeTestWithLambda() {
    Action1<String> subscriber1 = s -> System.out.println("First subscriber onNext() = " + s);
    Subscriber<String> subscriber2 = new Subscriber<String>() {
      @Override public void onCompleted() {

      }

      @Override public void onError(Throwable e) {
        System.out.println("Error encountered " + e.getMessage());
      }

      @Override public void onNext(String s) {
        System.out.println("Second subscriber onNext() = " + s);
        txtInfo.setText(s);
      }
    };
    Subscription subscription1 =
        stringObservable1.subscribeOn(Schedulers.io()).subscribe(subscriber2);
  }

  private Observable<String> stringObservable1 = Observable.create(s -> {
    s.onNext("Producer sending first message");
    //new Handler().postDelayed(() -> {
    //  s.onNext("Producer sending second message");
    //}, 1000);
  });

  private class CustomOnSubscribe implements Observable.OnSubscribe<String> {

    private List<Subscriber<? super String>> subscribers;

    public CustomOnSubscribe() {
      subscribers = new ArrayList<>();
    }

    public void doSomeTask() {
      System.out.println("Hello there");
      for (Subscriber s : subscribers)
        s.onNext("Hello how are you");
    }

    @Override public void call(Subscriber<? super String> subscriber) {
      subscribers.add(subscriber);
    }
  }
}
