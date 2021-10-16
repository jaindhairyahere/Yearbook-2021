package com.example.yearbook.api.model;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.yearbook.Constants;
import com.example.yearbook.api.response.LoginResponse;
import com.example.yearbook.interfaces.Clickable;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Date;

public class Student_Profile{
    protected int id;
    protected String email;
    protected Date dob;
    protected String profile_image;
    protected Student student;
    protected String hostel;
    protected String room_no;
    protected String department;
    protected String program;
    protected String degree;
    protected int join_year;
    protected int graduation_year;
    protected String nickname;
    protected String tagline;
    protected String ib1;
    protected String ib2;
    protected String ib3;
    protected String img1;
    protected String img2;
    protected String img3;
    protected String img4;
    protected String question1;
    protected String answer1;


    protected boolean details_done;


    public Student_Profile(int id, String email, Date dob, String profile_image, Student student, String hostel, String room_no, String department, String program, String degree, int join_year, int graduation_year, String nickname, String tagline, String ib1, String ib2, String ib3, String img1, String img2, String img3, String img4, String question1, String answer1, boolean details_done) {
        this.id = id;
        this.email = email;
        this.dob = dob;
        this.profile_image = Constants.BASE_URL + profile_image;
        this.student = student;
        this.hostel = hostel;
        this.room_no = room_no;
        this.department = department;
        this.program = program;
        this.degree = degree;
        this.join_year = join_year;
        this.graduation_year = graduation_year;
        this.nickname = nickname;
        this.tagline = tagline;
        this.ib1 = ib1;
        this.ib2 = ib2;
        this.ib3 = ib3;
        this.img1 = Constants.BASE_URL + img1;
        this.img2 = Constants.BASE_URL + img2;
        this.img3 = Constants.BASE_URL + img3;
        this.img4 = Constants.BASE_URL + img4;
        this.question1 = question1;
        this.answer1 = answer1;
        this.details_done = details_done;
    }

    public Student_Profile() { }


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Date getDob() { return dob; }

    public String getProfile_image() {
        return profile_image;
    }

    public Student getStudent() {
        return student;
    }

    public String getHostel() {
        return hostel;
    }

    public String getRoom_no() {
        return room_no;
    }

    public String getDepartment() {
        return department;
    }

    public String getProgram() {
        return program;
    }

    public String getDegree() {
        return degree;
    }

    public int getJoin_year() {
        return join_year;
    }

    public int getGraduation_year() {
        return graduation_year;
    }

    public String getNickname() {
        return nickname;
    }

    public String getTagline() {
        return tagline;
    }

    public String getIb1() {
        return ib1;
    }

    public String getIb2() {
        return ib2;
    }

    public String getIb3() {
        return ib3;
    }

    public String getImg1() { return img1; }

    public String getImg2() { return img2; }

    public String getImg3() { return img3; }

    public String getImg4() { return img4; }

    public String getQuestion1() {
        return question1;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setJoin_year(int join_year) {
        this.join_year = join_year;
    }

    public void setGraduation_year(int graduation_year) { this.graduation_year = graduation_year; }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setIb1(String ib1) {
        this.ib1 = ib1;
    }

    public void setIb2(String ib2) {
        this.ib2 = ib2;
    }

    public void setIb3(String ib3) { this.ib3 = ib3; }

    public void setImg1(String img1) { this.img1 = img1; }

    public void setImg2(String img2) { this.img2 = img2; }

    public void setImg3(String img3) { this.img3 = img3; }

    public void setImg4(String img4) { this.img4 = img4; }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public boolean isDetails_done() { return details_done; }

    public void setDetails_done(boolean details_done) { this.details_done = details_done; }

    public static Student_Profile fromString(String json) {
        try {
            return new Gson().fromJson(json, Student_Profile.class);
        } catch (JsonSyntaxException e) {
            Log.d("Student Profile", "fromString: " + json);
            return null;
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getFullName() {
        return student.toString();
    }
}
