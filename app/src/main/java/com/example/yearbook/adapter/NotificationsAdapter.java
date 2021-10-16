package com.example.yearbook.adapter;

import com.example.yearbook.api.model.Notification;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.yearbook.R;
import com.example.yearbook.Utils;
import com.example.yearbook.api.RetrofitInterface;
import com.example.yearbook.api.response.EmptyCallback;
import com.example.yearbook.fragment.NotificationsFragment;
import com.example.yearbook.notifications.NotificationId;
import com.google.gson.Gson;

import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;

public class NotificationsAdapter extends CardAdapter<Notification> {
    private NotificationsFragment notificationsFragment;

    public NotificationsAdapter(List<Notification> notifications, Fragment fragment) {
        super(notifications, fragment);
        notificationsFragment = (NotificationsFragment) fragment;
    }

    @Override
    public void onClick(Notification notification, FragmentActivity fragmentActivity) {
        /* Mark notification read */
        RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();
        String sessId = Utils.getSessionIDHeader();
        retrofitInterface.markNotificationRead(sessId, notification.getNotificationId().toString()).enqueue(new EmptyCallback<Void>());
        ShortcutBadger.applyCount(fragmentActivity.getApplicationContext(), NotificationId.decrementAndGetCurrentCount());

        /* Close the bottom sheet */
        notificationsFragment.dismiss();

        Gson gson = Utils.gson;
        /* Open event */
//        if (notification.isEvent()) {
//            Event event = gson.fromJson(gson.toJson(notification.getNotificationActor()), Event.class) ;
//            Utils.openEventFragment(event, fragmentActivity);
//        } else if (notification.isNews()) {
//            NewsFragment newsFragment = new NewsFragment();
//            NewsArticle newsArticle = gson.fromJson(gson.toJson(notification.getNotificationActor()), NewsArticle.class) ;
//            newsFragment.withId(newsArticle.getId());
//            Utils.updateFragment(newsFragment, fragmentActivity);
//        } else if (notification.isBlogPost()) {
//            PlacementBlogPost post = gson.fromJson(gson.toJson(notification.getNotificationActor()), PlacementBlogPost.class);
//            Fragment fragment;
//            if (post.getLink().contains("training")) {
//                fragment = (new TrainingBlogFragment()).withId(post.getId());
//            } else {
//                fragment = (new PlacementBlogFragment()).withId(post.getId());
//            }
//            Utils.updateFragment(fragment, fragmentActivity);
//        }
    }

    @Override
    public int getAvatarPlaceholder(Notification notification) {
        return R.drawable.lotus_sq;
    }
}
