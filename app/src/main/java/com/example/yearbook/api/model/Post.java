package com.example.yearbook.api.model;

import android.content.Context;
import android.view.View;

import com.example.yearbook.interfaces.Clickable;

import static com.example.yearbook.Utils.openWebURL;

public class Post implements Clickable {
    private Student_Profile written_by_profile;
    private Student_Profile written_for_profile;
    private String content;
    private String pic;
    private String time;
    private int id;
    private int likes;
    private int status;

    public Post(Student_Profile written_by_profile, Student_Profile written_for_profile, String content, String pic, String time, int id, int likes, int status) {
        this.written_by_profile = written_by_profile;
        this.written_for_profile = written_for_profile;
        this.content = content;
        this.pic = pic;
        this.time = time;
        this.id = id;
        this.likes = likes;
        this.status = status;
    }

    public Student_Profile getWritten_by_profile() {
        return written_by_profile;
    }

    public void setWritten_by_profile(Student_Profile written_by_profile) {
        this.written_by_profile = written_by_profile;
    }

    public Student_Profile getWritten_for_profile() {
        return written_for_profile;
    }

    public void setWritten_for_profile(Student_Profile written_for_profile) {
        this.written_for_profile = written_for_profile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String getId() {
        return String.valueOf(getId_int());
    }

    public int getId_int() { return id; }

    @Override
    public View.OnClickListener getOnClickListener(final Context context) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebURL(context, "null");
            }
        };
    }

    public int getLikes() { return likes; }

    public int getStatus() { return status; }

    public void setLikes(int likes) { this.likes = likes; }

    public void setId(int id) { this.id = id; }

    public void setStatus(int status) { this.status = status; }

}
