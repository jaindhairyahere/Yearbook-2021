package com.example.yearbook.api.response;

import com.example.yearbook.api.model.Post;
import com.example.yearbook.api.model.Student_Profile;

import java.util.ArrayList;

public class SearchStudentAPI {
    private Student_Profile searched_student;
    private ArrayList<ArrayList<String>> questions;
    private ArrayList<String> ibs;
    private ArrayList<Post> posts_by;
    private ArrayList<Post> posts_for;
    private int num_by;
    private int num_for;

    public SearchStudentAPI(Student_Profile searched_student, ArrayList<ArrayList<String>> questions, ArrayList<String> ibs, ArrayList<Post> posts_by, ArrayList<Post> posts_for, int num_by, int num_for) {
        this.searched_student = searched_student;
        this.questions = questions;
        this.ibs = ibs;
        this.posts_by = posts_by;
        this.posts_for = posts_for;
        this.num_by = num_by;
        this.num_for = num_for;
    }

    public Student_Profile getSearched_student() {
        return searched_student;
    }

    public void setSearched_student(Student_Profile searched_student) {
        this.searched_student = searched_student;
    }

    public ArrayList<ArrayList<String>> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<ArrayList<String>> questions) {
        this.questions = questions;
    }

    public ArrayList<String> getIbs() {
        return ibs;
    }

    public void setIbs(ArrayList<String> ibs) {
        this.ibs = ibs;
    }

    public ArrayList<Post> getPosts_by() {
        return posts_by;
    }

    public void setPosts_by(ArrayList<Post> posts_by) {
        this.posts_by = posts_by;
    }

    public ArrayList<Post> getPosts_for() {
        return posts_for;
    }

    public void setPosts_for(ArrayList<Post> posts_for) {
        this.posts_for = posts_for;
    }

    public int getNum_by() {
        return num_by;
    }

    public void setNum_by(int num_by) {
        this.num_by = num_by;
    }

    public int getNum_for() {
        return num_for;
    }

    public void setNum_for(int num_for) {
        this.num_for = num_for;
    }
}
