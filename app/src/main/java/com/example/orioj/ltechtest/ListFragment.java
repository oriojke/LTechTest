package com.example.orioj.ltechtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orioj.ltechtest.api.Ltech;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by orioj on 24.07.2017.
 */

public class ListFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {

    private static Ltech LtechApi;
    private Retrofit httpHandler;
    private List<JsonDataModel> dataList;
    private SwipeRefreshLayout mrefLayout;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private boolean isTimerRunning = false;
    private int mSecondsPassed = 0;

    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        dataList = new ArrayList<>();

        httpHandler = new Retrofit.Builder()
                .baseUrl("http://dev-exam.l-tech.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LtechApi = httpHandler.create(Ltech.class);

        mTimer = new Timer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager lManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(lManager);

        CardsAdapter adapter = new CardsAdapter(dataList);
        mRecyclerView.setAdapter(adapter);

        mrefLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mrefLayout.setOnRefreshListener(this);

        refreshList();
        startTimer();

        return view;
    }

    public void refreshList(){
        //if(isTimerRunning) mTimer.cancel();
        LtechApi.getData().enqueue(new Callback<List<JsonDataModel>>() {
            @Override
            public void onResponse(Call<List<JsonDataModel>> call, Response<List<JsonDataModel>> response) {
                dataList.clear();
                dataList.addAll(response.body());
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<JsonDataModel>> call, Throwable t) {
            }
        });
        //startTimer();
    }

    @Override
    public void onRefresh() {
        mSecondsPassed = 0;
        mrefLayout.setRefreshing(true);
        refreshList();
        mrefLayout.setRefreshing(false);
    }

    public void startTimer(){
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if(mSecondsPassed == 30){
                    refreshList();
                    mSecondsPassed = 0;
                }
                else
                    mSecondsPassed++;
            }
        };
        mSecondsPassed = 0;
        mTimer.scheduleAtFixedRate(mTimerTask, 0, 1000);
        isTimerRunning = true;
    }

}
