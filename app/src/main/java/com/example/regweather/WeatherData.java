package com.example.regweather;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class WeatherData {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String appid = "55a3af89290138723eaaefa9572f8bc7";
    private static final String ICON_URL = "http://openweathermap.org/img/w/%s.png";

    private RequestQueue requestQueue;



    public WeatherData(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getWeather(String city, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "?q=" + city + "&appid=" + appid + "&units=metric&lang=ru";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        requestQueue.add(request);
    }
    public String getIconUrl(JSONObject json) throws JSONException {
        JSONArray weatherArray = json.getJSONArray("weather");
        JSONObject weatherObject = weatherArray.getJSONObject(0);
        String iconCode = weatherObject.getString("icon");
        return String.format(ICON_URL, iconCode);
    }


}