package com.example.yearbook.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.yearbook.Constants;
import com.example.yearbook.R;
import com.example.yearbook.SessionManager;
import com.example.yearbook.Utils;
import com.example.yearbook.activity.LoginActivity;
import com.example.yearbook.api.RetrofitInterface;
import com.example.yearbook.api.model.Student_Profile;
import com.example.yearbook.api.response.EmptyCallback;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SharedPreferences.Editor editor;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // Get preferences and editor
        SharedPreferences sharedPref = getActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        // Dark Theme
        SwitchPreferenceCompat darkThemePref = (SwitchPreferenceCompat) findPreference("dark_theme");
        darkThemePref.setOnPreferenceChangeListener((preference, option) -> {
            toggleDarkTheme((boolean) option);
            return true;
        });
        darkThemePref.setChecked(sharedPref.getBoolean(Constants.DARK_THEME, false));

        // Update Profile
        Preference profilePref = findPreference("profile");
        profilePref.setOnPreferenceClickListener(preference -> {
            openWebURL("https://gymkhana.iitb.ac.in/sso/user");
            return false;
        });

        // Feedback
        Preference feedbackPref = findPreference("feedback");
        feedbackPref.setOnPreferenceClickListener(preference -> {
            openWebURL("https://www.insti.app/feedback");
            return false;
        });

        // About
        Preference aboutPref = findPreference("about");
        aboutPref.setOnPreferenceClickListener(preference -> {
            openAbout();
            return false;
        });

        // Logout
        Preference logoutPref = findPreference("logout");
        logoutPref.setOnPreferenceClickListener(preference -> {
            logout();
            return false;
        });

        // Disable buttons if not logged in
        final SessionManager sessionManager = new SessionManager(getContext());
        if (!sessionManager.isLoggedIn()) {
            logoutPref.setVisible(false);
        }
    }

    private void openAbout() {
        Utils.updateFragment(new AboutFragment(), getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();

        // Set toolbar title
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        Utils.setSelectedMenuItem(getActivity(), R.id.nav_logout);

        if (Utils.currentUserCache == null) {
            // Get the user id
            Bundle bundle = getArguments();
            String profileId = bundle.getString(Constants.USER_ID);

            // Fill in the user
            RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();
            retrofitInterface.getStudentProfile(profileId).enqueue(new EmptyCallback<Student_Profile>() {
                @Override
                public void onResponse(Call<Student_Profile> call, Response<Student_Profile> response) {
                    if (response.isSuccessful()) {
                        if (getActivity() == null || getView() == null) return;
                        Utils.currentUserCache = Objects.requireNonNull(response).body();
                    }
                }
            });
        }
    }

//    private void toggleShowContact(final SwitchPreferenceCompat showContactPref, Object option) {
//        final boolean isChecked = (boolean) option;
//        showContactPref.setEnabled(false);
//        RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();
//        retrofitInterface.patchUserMe(Utils.getSessionIDHeader(), new UserShowContactPatchRequest(isChecked)).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if(getActivity() == null || getView() == null) return;
//                if (response.isSuccessful()) {
//                    showContactPref.setChecked(response.body().getShowContactNumber());
//                    showContactPref.setEnabled(true);
//                    Utils.currentUserCache = response.body();
//                } else {
//                    showContactPref.setChecked(!isChecked);
//                    showContactPref.setEnabled(true);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                if(getActivity() == null || getView() == null) return;
//                showContactPref.setChecked(!isChecked);
//                showContactPref.setEnabled(true);
//            }
//        });
//    }

    private void toggleDarkTheme(boolean option) {
        editor.putBoolean(Constants.DARK_THEME, option);
        editor.commit();
        Utils.changeTheme(this, option);
    }

//    private void toggleCalendarDialog(String option) {
//        // Using strings.xml values for populating ListPreference. `option` comes from strings.xml
//        // Using Constants for updating SharedPrefs. `choice` comes from Constants.
//        String choice = Constants.CALENDAR_DIALOG_ALWAYS_ASK;
//
//        if (option.equals(getString(R.string.calendar_yes))) choice = Constants.CALENDAR_DIALOG_YES;
//        else if (option.equals(getString(R.string.calendar_no))) choice = Constants.CALENDAR_DIALOG_NO;
//
//        editor.putString(Constants.CALENDAR_DIALOG, choice);
//        editor.commit();
//    }
//
//    private void openAbout() { Utils.updateFragment(new AboutFragment(), getActivity()); }

    private void logout() {
        final SessionManager sessionManager = new SessionManager(getContext());
        RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();
        retrofitInterface.logout(Utils.getSessionIDHeader()).enqueue(new EmptyCallback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    sessionManager.logout();
                    Utils.clearCookies(getActivity());
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }

    private void openWebURL(String URL) {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(browse);
    }
}
