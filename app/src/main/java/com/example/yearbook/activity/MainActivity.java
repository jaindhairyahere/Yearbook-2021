package com.example.yearbook.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
//import androidx.multidex.MultiDex;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yearbook.Constants;
import com.example.yearbook.R;
import com.example.yearbook.SessionManager;
import com.example.yearbook.Utils;
import com.example.yearbook.adapter.PostAdapter;
import com.example.yearbook.api.ServiceGenerator;
import com.example.yearbook.api.model.Post;
import com.example.yearbook.api.model.Student_Profile;
import com.example.yearbook.api.response.EmptyCallback;
import com.example.yearbook.fragment.BackHandledFragment;
import com.example.yearbook.fragment.RecyclerViewFragment;
import com.example.yearbook.fragment.ScrollFragment;
import com.example.yearbook.fragment.SettingsFragment;
import com.example.yearbook.fragment.StudentProfileFragment;
import com.example.yearbook.notifications.NotificationId;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,  BackHandledFragment.BackHandlerInterface {

    private AppBarConfiguration mAppBarConfiguration;
    private SessionManager session;
    private static final String TAG = "MainActivity";
    private Student_Profile currentProfile;
    private BackHandledFragment selectedFragment;
    private ScrollFragment scrollFragment;
    private Menu menu;
    public static void hideKeyboard(@NotNull FragmentActivity activity) {
        // Find the Keyboard Manager
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        assert imm != null;
        // Hide the keyboard from current (view,getWindowToken()) window
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get Shared Preferences for the whole app with key "LoggedInPref" in the private mode
        SharedPreferences sharedPref = getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

        // From the preferences, check if the DARK_THEME is true or false (default = false); if true set the theme to be R.AppThemeDark
        Utils.isDarkTheme = sharedPref.getBoolean(Constants.DARK_THEME, false);
        if (Utils.isDarkTheme)
            this.setTheme(R.style.AppThemeDark);

        // Create a ServiceGenerator for this Application and statically set it in the Utils class
        ServiceGenerator serviceGenerator = new ServiceGenerator(getApplicationContext());
        Utils.setRetrofitInterface(serviceGenerator.getRetrofitInterface());

        // Create a static Gson Object and a static MarkOwn for this application
        Utils.makeGson();
        Utils.makeMarkwon(getApplicationContext());

        // Set View to be layout - activity_main
        setContentView(R.layout.activity_main);

        // Create a new Session Manager Object using this application's context; Stores Django Session Object Information; And assert for active Login
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Utils.setSessionId(session.getSessionID());
        }
        assert session.getCurrentUser() != null;

        // Set the support Action Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the floating action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Get the entire window DrawerLayout as drawer layout - DrawerLayout acts as a top-level container for window content that allows for interactive "drawer" views to be pulled out from one or both vertical edges of the window.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Get Left Side drawer or nav_view
        // NavigationView navigationView = findViewById(R.id.nav_view);
        // Make action bar hamburger in the "toolbar" of the "drawer" layout of "this" activity with open_drawer and close_drawer actions
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // Set the hamburger as an toogle for the DrawerLayout and then sync State of the hamburger i.e if open then looks like cross and if closed then looks like three parallel lines
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Make a postFragment for this activity and update it every time the activity gets created
        scrollFragment = new ScrollFragment();
        updateFragment(scrollFragment);

//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        Intent intent = getIntent();
        if (intent != null) {
//             Check for data passed by FCM
            if (intent.getExtras() != null && intent.getBundleExtra(Constants.MAIN_INTENT_EXTRAS) != null) {
                handleFCMIntent(intent.getBundleExtra(Constants.MAIN_INTENT_EXTRAS));
            } else {
                handleIntent(intent);
            }
        }
        // Check for Latest Version, whoose information is maintained online; if not latest then show an SnackBar
        checkLatestVersion();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
    }

    @Override
    protected void onStart() {
        super.onStart();
        initNavigationView();
        if (session.isLoggedIn()) {
            Log.println(Log.ASSERT,TAG,"Session is logged in");
            currentProfile = session.getCurrentUser();
            Log.println(Log.ASSERT,TAG,"currentProfile is not Null");
            updateNavigationView();
//            updateFCMId();
        }
    }
    /**
     * Handle opening event/body/blog from FCM notification
     */
    private void handleFCMIntent(Bundle bundle) {
        /* Mark the notification read */
        final String notificationId = bundle.getString(Constants.FCM_BUNDLE_NOTIFICATION_ID);
        if (notificationId != null) {
            Utils.getRetrofitInterface().markNotificationRead(Utils.getSessionIDHeader(), notificationId).enqueue(new EmptyCallback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    ShortcutBadger.applyCount(getApplicationContext(), NotificationId.decrementAndGetCurrentCount());
                }
            });
        }

        /* Follow the notification */
//        chooseIntent(
//                bundle.getString(Constants.FCM_BUNDLE_TYPE),
//                bundle.getString(Constants.FCM_BUNDLE_ID),
//                bundle.getString(Constants.FCM_BUNDLE_EXTRA)
//        );
    }

    /**
     * Handle intents for links
     */
    private void handleIntent(Intent appLinkIntent) {
        String appLinkAction = appLinkIntent.getAction();
        String appLinkData = appLinkIntent.getDataString();
//        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
//            chooseIntent(getType(appLinkData), getID(appLinkData));
//        }
    }

    /**
     * Open the proper fragment from given type, id and extra
     */
//    private void chooseIntent(String type, String id, String extra) {
//        if (extra == null) {
//            chooseIntent(type, id);
//        } else {
//            switch (type) {
//                case Constants.DATA_TYPE_PT:
//                    if (extra.contains("/trainingblog")) {
//                        openTrainingBlog(id);
//                    } else {
//                        openPlacementBlog(id);
//                    }
//                    return;
//            }
//            chooseIntent(type, id);
//        }
//    }

//    private void updateFCMId() {
//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
//            @Override
//            public void onSuccess(InstanceIdResult instanceIdResult) {
//                final String fcmId = instanceIdResult.getToken();
//                RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();
//
//                retrofitInterface.patchUserMe(Utils.getSessionIDHeader(), new UserFCMPatchRequest(fcmId, getCurrentVersion())).enqueue(new EmptyCallback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        if (response.isSuccessful()) {
//                            session.createLoginSession(response.body().getUserName(), response.body(), session.getSessionID());
//                            currentUser = response.body();
//                            Utils.currentUserCache = currentUser;
//                        } else {
//                            session.logout();
//                            currentUser = null;
//                            Toast.makeText(MainActivity.this, "Your session has expired!", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//            }
//        });
//    }

    private void initNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_menu_infi_scroll:
                openInfiScroll();
                break;
            case R.id.nav_menu_your_profile:
                StudentProfileFragment studentProfileFragment = new StudentProfileFragment();
                updateFragment(studentProfileFragment);
                break;

            case R.id.nav_settings:
                SettingsFragment settingsFragment = new SettingsFragment();
                updateFragment(settingsFragment);
                break;

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (selectedFragment == null || !selectedFragment.onBackPressed()) {
            // Selected fragment did not consume the back press event.
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else if (scrollFragment != null && scrollFragment.isVisible()) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }
    private void updateNavigationView() {
        /**
         * Updates the Navigation View with user credentials if the user was logged in
         * */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openUserFragment(currentProfile.getStudent().getSso_id(), MainActivity.this);
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        TextView nameTextView = header.findViewById(R.id.nav_header_student_profile_name);
        TextView rollNoTextView = header.findViewById(R.id.nav_header_student_profile_sso);
        ImageView profilePictureImageView = header.findViewById(R.id.nav_header_student_profile_image);
        nameTextView.setText(currentProfile.getFullName());
        rollNoTextView.setText(currentProfile.getStudent().getSso_id());

        Picasso.get()
                .load(Constants.BASE_URL+currentProfile.getProfile_image())
                .resize(200, 0)
                .placeholder(R.drawable.user_placeholder)
                .into(profilePictureImageView);
    }

//    private void openMyProfile() {
//        openMyProfile(null);
//    }
//    private void openMyProfile(String id) {
//        if (session.isLoggedIn()) {
//            StudentProfileFragment placementBlogFragment = new StudentProfileFragment();
//            if (id != null) placementBlogFragment.withId(id);
//            updateFragment(placementBlogFragment);
//        } else {
//            Toast.makeText(this, Constants.LOGIN_MESSAGE, Toast.LENGTH_LONG).show();
//        }
//    }

    private void openInfiScroll() {
        openInfiScroll(null);
    }
    private void openInfiScroll(String id) {
        if (session.isLoggedIn()) {
            ScrollFragment scrollFragment = new ScrollFragment();
            if (id != null) {
                RecyclerViewFragment<Post, PostAdapter> postPostAdapterRecyclerViewFragment = scrollFragment.withId(id);
            }
            updateFragment(scrollFragment);
        } else {
            Toast.makeText(this, Constants.LOGIN_MESSAGE, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Change the active fragment to the supplied one
     */
    public void updateFragment(Fragment fragment) {
        if (session.isLoggedIn() && currentProfile == null) {
            currentProfile = session.getCurrentUser();
        }
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(Constants.SESSION_ID, session.pref.getString(Constants.SESSION_ID, ""));
        if (fragment instanceof SettingsFragment && session.isLoggedIn())
            bundle.putString(Constants.USER_ID, currentProfile.getStudent().getSso_id());
        if (fragment instanceof StudentProfileFragment){
            bundle.putString(Constants.USER_ID, currentProfile.getStudent().getSso_id());
            bundle.putString(Constants.CURRENT_USER_PROFILE_PICTURE, currentProfile.getProfile_image());
        }

        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        if (fragment instanceof ScrollFragment)
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        FragmentTransaction transaction = manager.beginTransaction();

//        /* Animate only for UserFragment */
//        if (fragment instanceof UserFragment) {
//            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out_down);
//        }

        transaction.replace(R.id.constraintlayout_for_fragment, fragment, Utils.getTag(fragment));
        transaction.addToBackStack(Utils.getTag(fragment)).commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    private int getCurrentVersion() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
            return 0;
        }
    }

    /**
     * Check for updates in andro.json
     */
    private void checkLatestVersion() {
        return;
//        final int versionCode = getCurrentVersion();
//        if (versionCode == 0) {
//            return;
//        }
//        RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();
//        retrofitInterface.getLatestVersion().enqueue(new EmptyCallback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if (response.isSuccessful()) {
//                    final JsonElement currentVersion = response.body().get("version");
//                    if (currentVersion != null && currentVersion.getAsInt() > versionCode) {
//                        showUpdateSnackBar(response.body().get("message").getAsString());
//                    }
//                }
//            }
//        });
    }
    private void showUpdateSnackBar(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).setAction("UPDATE", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        }).show();
    }

    @Override
    public void setSelectedFragment(BackHandledFragment backHandledFragment) {
        this.selectedFragment = backHandledFragment;
    }

//    private void handleFCMIntent(Bundle bundle) {
//        /* Mark the notification read */
//        final String notificationId = bundle.getString(FCM_BUNDLE_NOTIFICATION_ID);
//        if (notificationId != null) {
//            Utils.getRetrofitInterface().markNotificationRead(Utils.getSessionIDHeader(), notificationId).enqueue(new EmptyCallback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    ShortcutBadger.applyCount(getApplicationContext(), NotificationId.decrementAndGetCurrentCount());
//                }
//            });
//        }
//
//        /* Follow the notification */
//        chooseIntent(
//                bundle.getString(Constants.FCM_BUNDLE_TYPE),
//                bundle.getString(Constants.FCM_BUNDLE_ID),
//                bundle.getString(Constants.FCM_BUNDLE_EXTRA)
//        );
//    }
//
//    /**
//     * Handle intents for links
//     */
//    private void handleIntent(Intent appLinkIntent) {
//        String appLinkAction = appLinkIntent.getAction();
//        String appLinkData = appLinkIntent.getDataString();
//        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
//            chooseIntent(getType(appLinkData), getID(appLinkData));
//        }
//    }

}
