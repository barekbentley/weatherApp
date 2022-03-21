package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    String CityName = "Canyon";

    String Request_Link = "https://api.openweathermap.org/data/2.5/weather?q="+CityName+"&appid=5ceca559e239a09a462e0e4d37e1b8e4";

    TextView textView;

    public class DownloadWeatherData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpsURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {
                    char currentChar = (char) data;

                    result = result+currentChar;
                    data = reader.read();
                }
                return result;

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Catch Error", e.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("WeatherInJSON", result);
            textView = findViewById(R.id.textViewWeather);
            textView.setText(result);
        }
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            DownloadWeatherData task = new DownloadWeatherData();
            task.execute(Request_Link);
        }
    }



