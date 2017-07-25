package com.example.orioj.ltechtest;

import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentSwitcher{


    ListFragment list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(list == null) list = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, list).commit();
    }

    @Override
    public void switchFragment(android.app.Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
