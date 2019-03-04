package com.experience.jobtaskapp;

import org.json.JSONObject;

public class User {

    private String name;
    private String activity;
    private String age;
    private String email;
    private String imageUrl;

    public void fromJson(JSONObject jsonObject){
        try {
        name = jsonObject.getString("name");
        activity = jsonObject.getString("activity");
        age = jsonObject.getString("age");
        email = jsonObject.getString("e-mail");
        imageUrl = jsonObject.getString("image");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
