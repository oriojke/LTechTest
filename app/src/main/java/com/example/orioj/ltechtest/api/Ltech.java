package com.example.orioj.ltechtest.api;

/**
 * Created by orioj on 22.07.2017.
 */

import retrofit2.http.GET;
import retrofit2.Call;

import com.example.orioj.ltechtest.JsonDataModel;

import java.util.List;

public interface Ltech {

    @GET("/")
    Call<List<JsonDataModel>> getData();
}
