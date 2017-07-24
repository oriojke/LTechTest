package com.example.orioj.ltechtest;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListFragment list = new ListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, list).commit();

    }

    public void onCardClicked(JsonDataModel data){
        DetailsFragment details = new DetailsFragment();
        details.setData(data);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragmentContainer, details);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
