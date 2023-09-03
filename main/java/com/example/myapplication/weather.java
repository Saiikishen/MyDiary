package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class weather extends AppCompatActivity {

    EditText city;
    TextView weatherinfo;
    String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    String api_key = "2722a0b0425e64695817fb9807b924c9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        city = findViewById(R.id.cityname);
        weatherinfo = findViewById((R.id.weatherinfo));
        
    }

    public void getweather (View v){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherapi myapi = retrofit.create(weatherapi.class);
        Call<Getmain> getmainCall = myapi.getweather(city.getText().toString().trim(),api_key);
        getmainCall.enqueue(new Callback<Getmain>() {
            @Override
            public void onResponse(Call<Getmain> call, Response<Getmain> response) {
                if (response.code()==404){
                    Toast.makeText(weather.this,"Enter a valid City",Toast.LENGTH_LONG).show();

                } else if (response.isSuccessful()) {
                    Toast.makeText(weather.this,"Successful",Toast.LENGTH_LONG).show();

                }

                Getmain mydata = response.body();
                Main main = mydata.getMain();
                Double temp=main.getTemp();
                Double temperature = temp - 273.15;
                weatherinfo.setText(String.valueOf(temperature)+ "C");



            }

            @Override
            public void onFailure(Call<Getmain> call, Throwable t) {
                Toast.makeText(weather.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}