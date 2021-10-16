package com.example.yearbook.api.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("sso_id")
    private String sso_id;
    @SerializedName("first_name")
    private String first_name;
    @SerializedName("last_name")
    private String last_name;
    public Student(String sso_id, String first_name, String last_name) {
        this.sso_id = sso_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getSso_id() {
        return sso_id;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setSso_id(String sso_id) {
        this.sso_id = sso_id;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    @NonNull
    @Override
    public String toString() {
        return first_name + " " + last_name;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    public static Student fromString(String json) {
        try {
            return new Gson().fromJson(json, Student.class);
        } catch (JsonSyntaxException e) {
            Log.d("Student", "fromString: " + json);
            return null;
        }
    }

}
