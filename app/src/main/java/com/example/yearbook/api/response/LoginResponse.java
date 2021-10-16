package com.example.yearbook.api.response;

import com.example.yearbook.api.model.Student;
import com.example.yearbook.api.model.Student_Profile;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LoginResponse {
    @SerializedName("sessionid")
    private String sessionID;
    @SerializedName("user")
    private String studentName;
    @SerializedName("profile_id")
    private String sso_id;
    @SerializedName("profile")
    private Student_Profile student_profile;
    public LoginResponse(String sessionID, String studentName, String sso_id, Student_Profile student_profile) {
        this.sessionID = sessionID;
        this.studentName = studentName;
        this.sso_id = sso_id;
        this.student_profile = student_profile;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }


    public String getStudentName() { return studentName; }

    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getSso_id() { return sso_id; }

    public void setSso_id(String sso_id) { this.sso_id = sso_id; }
    public Student_Profile getStudent_profile() { return student_profile; }

    public void setStudent_profile(Student_Profile student_profile) { this.student_profile = student_profile; }

}
