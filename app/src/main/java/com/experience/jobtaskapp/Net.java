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
    private static Net net;
    private Context context;
    private RequestQueue queue;


    private Net(Context context) {
        this.context = context;
        queue = getRequestQueue();
    }


    //getApplicationContext instead activity.context to avoid memory leaks
    public static synchronized Net getInstance(Context context) {
        if (net == null) {
            net = new Net(context.getApplicationContext());
        }
        return net;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public void signUpRequest(String nickname, String email, String password,
                              OnResponseListener<String> stringResponse) {
        try {
            final String url = "http://start.webpower.cf/test/register/";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nickname", nickname);
            jsonObject.put("e-mail", email);
            jsonObject.put("password", password);

            StringRequest stringRequest = new StringRequest
                    (Request.Method.POST, url, response -> stringResponse.onResponse(response)
                            , error -> Log.d(LOG_TAG, error.toString())) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
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
            final String url = "http://start.webpower.cf/test/auth/";

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
        User user = new User();
        String url = "http://start.webpower.cf/test/data/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    try {
                        user.fromJSON(response);
                        onResponseListener.onResponse(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        addToRequestQueue(jsonObjectRequest);
    }
}
