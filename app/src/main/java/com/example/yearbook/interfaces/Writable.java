package com.example.yearbook.interfaces;

import java.util.List;

public interface Writable<T> {
    void setPosts(List<T> posts);
}
