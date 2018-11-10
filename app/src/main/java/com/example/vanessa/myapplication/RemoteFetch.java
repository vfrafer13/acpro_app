package com.example.vanessa.myapplication;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vanessa on 24/02/18.
 */

public class RemoteFetch {

    private static final String OPEN_WEATHER_MAP_API = "http://api.openweathermap.org/data/2.5/weather?lat=20.97&lon=-89.62&units=metric";
    private static final String APP_ID = "29b298a6d1586ab8c4ecfcefdc806ece";

    public static JSONObject getJSON(Context context){
        try {
            URL url = new URL(String.format(OPEN_WEATHER_MAP_API));
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", APP_ID);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
}