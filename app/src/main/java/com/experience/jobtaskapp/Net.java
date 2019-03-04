package com.experience.jobtaskapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Net {

    private static final String LOG_TAG = Net.class.getSimpleName();

    public static void signUpRequest(Context context, String nickname, String email, String password,
                                     OnResponseListener<String> stringResponse) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            final String url = "http://start.webpower.cf/test/register/";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nickname", nickname);
            jsonObject.put("e-mail", email);
            jsonObject.put("password", password);

            StringRequest stringRequest = new StringRequest
                    (Request.Method.POST, url, response -> stringResponse.onResponse(response), error -> Log.d(LOG_TAG, error.toString())) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void signInRequest(Context context, String nickname, String password,
                                     OnResponseListener<JSONObject> jsonResponse) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            final String url = "http://start.webpower.cf/test/auth/";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nickname", nickname);
            jsonObject.put("password", password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObject,
                            response -> {
                                jsonResponse.onResponse(response);
                                Log.d(LOG_TAG, response.toString());
                            },
                            error -> Log.d(LOG_TAG, error.toString()));
            queue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void requestUserData(Context context, OnResponseListener<User> onResponseListener) {

        User user = new User();
        String url = "http://start.webpower.cf/test/data/";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        user.fromJson(response);
                        onResponseListener.onResponse(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        queue.add(jsonObjectRequest);
    }
}
