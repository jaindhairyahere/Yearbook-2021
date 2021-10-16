package com.example.yearbook.api.response;

import android.content.Context;
import android.view.View;

import com.example.yearbook.api.model.Post;
import com.example.yearbook.api.model.Student;
import com.example.yearbook.interfaces.Clickable;

import java.util.ArrayList;

public class ScrollResponse implements Clickable {
    private ArrayList<Post> posts;
    private Student student;
    private int new_start;
    private int new_end;

    public ScrollResponse(ArrayList<Post> posts, Student student, int new_start, int new_end) {
        this.posts = posts;
        this.student = student;
        this.new_start = new_start;
        this.new_end = new_end;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getNew_start() {
        return new_start;
    }

    public void setNew_start(int new_start) {
        this.new_start = new_start;
    }

    public int getNew_end() {
        return new_end;
    }

    public void setNew_end(int new_end) {
        this.new_end = new_end;
    }

    @Override
    public String toString() {
        return "ScrollResponse{" +
                "posts=" + posts +
                ", student=" + student +
                ", new_start=" + new_start +
                ", new_end=" + new_end +
                '}';
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public int getId_int() {
        return 0;
    }

    @Override
    public View.OnClickListener getOnClickListener(Context context) {
        return null;
    }
}
