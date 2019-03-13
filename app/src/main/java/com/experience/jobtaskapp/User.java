package com.experience.jobtaskapp;

import org.json.JSONObject;

public class User {

    private String name;
    private String activity;
    private String age;
    private String email;
    private String imageUrl;

    public User(){}

    public User(JSONObject jsonUser){
        try {
            name = jsonUser.getString("name");
            activity = jsonUser.getString("activity");
            age = jsonUser.getString("age");
            email = jsonUser.getString("e-mail");
            imageUrl = jsonUser.getString("image");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fromJSON(JSONObject jsonUser){
        try {
            name = jsonUser.getString("name");
            activity = jsonUser.getString("activity");
            age = jsonUser.getString("age");
            email = jsonUser.getString("e-mail");
            imageUrl = jsonUser.getString("image");
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
