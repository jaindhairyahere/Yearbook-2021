package com.example.yearbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.yearbook.activity.LoginActivity;
import com.example.yearbook.api.model.Student_Profile;

public class SessionManager {
    public SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private final int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(Constants.PREF_NAME, PRIVATE_MODE);
    }

    public void checkLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            context.startActivity(i);
        }
    }

    public void createLoginSession(String gcmId, Student_Profile currentUser, String sessionID) {
        Log.d("SessionManager", "GcmId being stored");
        editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN, true);
        editor.putString(Constants.GCM_ID, gcmId);
        editor.putString(Constants.USER_ID, currentUser.getStudent().getSso_id());
        editor.putString(Constants.CURRENT_USER, currentUser.toString());
        editor.putString(Constants.SESSION_ID, sessionID);
        boolean commit = editor.commit();
        if (!commit) throw new AssertionError();
    }

    public String getUserID() {
        return pref.getString(Constants.USER_ID, "");
    }

    public Student_Profile getCurrentUser() {
        return Student_Profile.fromString(pref.getString(Constants.CURRENT_USER, ""));
    }

    public String getSessionID() {
        return pref.getString(Constants.SESSION_ID, "");
    }

    public boolean isLoggedIn() {
        return getCurrentUser()!=null;
    }

    public void logout() {
        editor = pref.edit();
        editor.clear().apply();
    }
}
