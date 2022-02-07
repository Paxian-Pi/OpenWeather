package com.example.weatherapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherAPI {

    @GET("{city}")
    Call<ResponseBody> getCity(@Path("city") String city);
}
