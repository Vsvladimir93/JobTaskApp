package com.experience.jobtaskapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Net {

    private static final String LOG_TAG = Net.class.getSimpleName();
    private static Net net;
    private Context context;
    private RequestQueue queue;
    private Gson gson;

    private Net(Context context) {
        this.context = context;
        queue = getRequestQueue();
        gson = new Gson();
    }

    //getApplicationContext instead activity.context to avoid memory leaks
    public static synchronized Net getInstance(Context context) {
        if (net == null) {
            net = new Net(context.getApplicationContext());
        }
        return net;
    }

    private RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    private <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public void signUpRequest(String nickname, String email, String password,
                              OnResponseListener<String> stringResponse) {
        try {

            final String url = BuildConfig.ENDPOINT + "/test/register/";

            StringRequest stringRequest = new StringRequest
                    (Request.Method.POST, url, stringResponse::onResponse,
                            error -> Log.d(LOG_TAG, error.toString())) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nickname", nickname);
                    params.put("e-mail", email);
                    params.put("password", password);
                    return params;
                }
            };
            addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void signInRequest(String nickname, String password,
                              OnResponseListener<JSONObject> jsonResponse) {
        try {
            final String url = BuildConfig.ENDPOINT + "/test/auth/";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nickname", nickname);
            jsonObject.put("password", password);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, url, jsonObject,
                            response -> {
                                jsonResponse.onResponse(response);
                            },
                            error -> error.printStackTrace());
            addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestUserData(OnResponseListener<User> onResponseListener) {
        String url = BuildConfig.ENDPOINT + "/test/data/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    onResponseListener.onResponse(gson.fromJson(response.toString(), User.class));
                }, Throwable::printStackTrace);
        addToRequestQueue(jsonObjectRequest);
    }
}
