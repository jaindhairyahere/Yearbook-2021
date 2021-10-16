package com.example.yearbook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.yearbook.api.RetrofitInterface;
import com.example.yearbook.api.ServiceGenerator;
import com.example.yearbook.api.response.EmptyCallback;
import com.example.yearbook.notifications.NotificationId;

import me.leolin.shortcutbadger.ShortcutBadger;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constants.NOTIF_CANCELLED)) {
            // Get the notification id
            String id = intent.getExtras().getString(Constants.FCM_BUNDLE_NOTIFICATION_ID);
            if (id == null || id.equals("")) return;

            // Get retrofit and session id
            ServiceGenerator serviceGenerator = new ServiceGenerator(context);
            RetrofitInterface retrofitInterface = serviceGenerator.getRetrofitInterface();

            SessionManager session = new SessionManager(context);
            if (session.isLoggedIn()) {
                Utils.setSessionId(session.getSessionID());
            }

            // Mark as read
            retrofitInterface.markNotificationDeleted(Utils.getSessionIDHeader(), id).enqueue(new EmptyCallback<Void>());

            // Reduce current count
            ShortcutBadger.applyCount(context.getApplicationContext(), NotificationId.decrementAndGetCurrentCount());
        }
    }
}
