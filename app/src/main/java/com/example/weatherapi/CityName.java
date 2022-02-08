package com.example.weatherapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityName extends AppCompatActivity {

    String temperature;
    String windSpeed;
    String description;
    String forecast;
    String cityName;

    String day1, temp1, speed1, day2, temp2, speed2, day3, temp3, speed3;

    TextView temp, speed, desc;
    TextView forecast1, mTemp1, mSpeed1;
    TextView forecast2, mTemp2, mSpeed2;
    TextView forecast3, mTemp3, mSpeed3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_name);

        // Current info
        temp = findViewById(R.id.temp);
        speed = findViewById(R.id.speed);
        desc = findViewById(R.id.desc);

        // Day 1
        forecast1 = findViewById(R.id.forecast1);
        mTemp1 = findViewById(R.id.temp1);
        mSpeed1 = findViewById(R.id.speed1);

        // Day 2
        forecast2 = findViewById(R.id.forecas2);
        mTemp2 = findViewById(R.id.temp2);
        mSpeed2 = findViewById(R.id.speed2);

        // Day 3
        forecast3 = findViewById(R.id.forecast3);
        mTemp3 = findViewById(R.id.temp3);
        mSpeed3 = findViewById(R.id.speed3);

        cityName = getIntent().getStringExtra("cityName"); // TODO: Pass this value to the function
        getWeatherForecast();

        Log.d("REz", "" + cityName);
    }

    private void getWeatherForecast() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        weatherAPI.getCity(cityName).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
//                        Log.d("REz", "\n"+jsonObject);

                        temperature = "Temperature: " + jsonObject.getString("temperature");
                        windSpeed = "Wind Speed: " + jsonObject.getString("wind");
                        description = "Description: " + jsonObject.getString("description");
                        forecast = jsonObject.getString("forecast");
//                        Log.d("REz", "\n"+temperature+"\n"+windSpeed+"\n"+description);

                        JSONArray jsonArray = new JSONArray(forecast);
//                        Log.d("REz", "\n"+jsonArray.getJSONObject(0));


                        day1 = "Day: " + jsonArray.getJSONObject(0).getString("day");
                        temp1 = "Temperature: " + jsonArray.getJSONObject(0).getString("temperature");
                        speed1 = "Wind Speed: " + jsonArray.getJSONObject(0).getString("wind");

                        day2 = "Day: " + jsonArray.getJSONObject(1).getString("day");
                        temp2 = "Temperature: " + jsonArray.getJSONObject(1).getString("temperature");
                        speed2 = "Wind Speed: " + jsonArray.getJSONObject(1).getString("wind");

                        day3 = "Day: " + jsonArray.getJSONObject(2).getString("day");
                        temp3 = "Temperature: " + jsonArray.getJSONObject(2).getString("temperature");
                        speed3 = "Wind Speed: " + jsonArray.getJSONObject(2).getString("wind");

//                        Log.d("REz", "\n"+speed1);

                        setValue();

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(CityName.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setValue() {
        // Current info
        temp.setText(temperature);
        speed.setText(windSpeed);
        desc.setText(description);

        // Day 1
        forecast1.setText(day1);
        mTemp1.setText(temp1);
        mSpeed1.setText(speed1);

        // Day 2
        forecast2.setText(day2);
        mTemp2.setText(temp2);
        mSpeed2.setText(speed2);

        // Day 3
        forecast3.setText(day3);
        mTemp3.setText(temp3);
        mSpeed3.setText(speed3);
    }
}