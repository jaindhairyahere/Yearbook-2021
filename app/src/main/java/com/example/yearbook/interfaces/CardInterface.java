package com.example.yearbook.interfaces;

import java.io.Serializable;

public interface CardInterface extends Serializable {
    long getId();
    String getTitle();
    String getSubtitle();
    String getAvatarUrl();
    int getBadge();
}
