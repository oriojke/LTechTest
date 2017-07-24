package com.example.orioj.ltechtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orioj.ltechtest.api.Ltech;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by orioj on 24.07.2017.
 */

public class ListFragment extends Fragment {

    private static Ltech LtechApi;
    private Retrofit httpHandler;
    private List<JsonDataModel> dataList;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager lManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(lManager);

        CardsAdapter adapter = new CardsAdapter(dataList);
        mRecyclerView.setAdapter(adapter);

        // mText = (TextView) findViewById(R.id.textfield);

        LtechApi.getData().enqueue(new Callback<List<JsonDataModel>>() {
            @Override
            public void onResponse(Call<List<JsonDataModel>> call, Response<List<JsonDataModel>> response) {
                dataList.addAll(response.body());
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<JsonDataModel>> call, Throwable t) {
            }
        });

        return view;
    }
}
