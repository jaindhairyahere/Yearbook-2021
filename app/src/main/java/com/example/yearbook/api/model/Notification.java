package com.example.yearbook.api.model;

import androidx.annotation.NonNull;

import com.example.yearbook.interfaces.CardInterface;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;


public class Notification implements CardInterface {

    private final String TYPE_EVENT = "event";
    private final String TYPE_NEWSENTRY = "newsentry";
    private final String TYPE_BLOG = "blogentry";

    @NonNull()
    @SerializedName("id")
    private Integer notificationId;

    @SerializedName("verb")
    private String notificationVerb;

    @SerializedName("unread")
    private boolean notificationUnread;

    @SerializedName("actor_type")
    private String notificationActorType;

    @SerializedName("actor")
    private Object notificationActor;

    public Notification(@NonNull Integer notificationId, String notificationVerb, boolean notificationUnread, String notificationActorType, Object notificationActor) {
        this.notificationId = notificationId;
        this.notificationVerb = notificationVerb;
        this.notificationUnread = notificationUnread;
        this.notificationActorType = notificationActorType;
        this.notificationActor = notificationActor;
    }

    @NonNull
    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(@NonNull Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationVerb() {
        return notificationVerb;
    }

    public void setNotificationVerb(String notificationVerb) {
        this.notificationVerb = notificationVerb;
    }

    public boolean isNotificationUnread() {
        return notificationUnread;
    }

    public void setNotificationUnread(boolean notificationUnread) {
        this.notificationUnread = notificationUnread;
    }

    public String getNotificationActorType() {
        return notificationActorType;
    }

    public void setNotificationActorType(String notificationActorType) {
        this.notificationActorType = notificationActorType;
    }

    public Object getNotificationActor() {
        return notificationActor;
    }

    public void setNotificationActor(Object notificationActor) {
        this.notificationActor = notificationActor;
    }

    public long getId() {
        return getNotificationId().hashCode();
    }

    public String getTitle() {
        return "Notification";
    }

    public String getSubtitle() {
        return getNotificationVerb();
    }

    public String getAvatarUrl() {
        return null;
    }

    public boolean isEvent() {
        return getNotificationActorType().contains(TYPE_EVENT);
    }

    public boolean isNews() {
        return getNotificationActorType().contains(TYPE_NEWSENTRY);
    }

    public boolean isBlogPost() {
        return getNotificationActorType().contains(TYPE_BLOG);
    }

    public int getBadge() { return 0; }
}
