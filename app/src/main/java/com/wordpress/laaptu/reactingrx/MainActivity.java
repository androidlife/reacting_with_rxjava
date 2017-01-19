package com.wordpress.laaptu.reactingrx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.wordpress.laaptu.reactingrx.basics.ProducerConsumerFragment;
import com.wordpress.laaptu.reactingrx.basics.SubscribeObserveFragment;
import com.wordpress.laaptu.reactingrx.basics.SubscriberOtherMethodsFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //addBasicFragment();
    //subscriberOtherMethodTest();
    subscribeObserveTest();
  }

  private void addBasicFragment() {
    getSupportFragmentManager().beginTransaction().replace(R.id.container,
        ProducerConsumerFragment.getInstance(null)).commit();
  }

  private void subscriberOtherMethodTest() {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, SubscriberOtherMethodsFragment.getInstance(null))
        .commit();
  }

  private void subscribeObserveTest() {
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.container, SubscribeObserveFragment.getInstance(null))
        .commit();
  }
}
