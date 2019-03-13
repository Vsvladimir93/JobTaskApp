package com.experience.jobtaskapp;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class User {

    @SerializedName("name")
    private String name;
    @SerializedName("activity")
    private String activity;
    @SerializedName("age")
    private String age;
    @SerializedName("e-mail")
    private String email;
    @SerializedName("image")
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
