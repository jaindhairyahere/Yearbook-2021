package com.example.yearbook.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
//import androidx.multidex.MultiDex;

import com.example.yearbook.Constants;
import com.example.yearbook.R;
import com.example.yearbook.SessionManager;
import com.example.yearbook.api.RetrofitInterface;
import com.example.yearbook.api.ServiceGenerator;
import com.example.yearbook.api.response.EmptyCallback;
import com.example.yearbook.api.response.LoginResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private final String redirectUri = "http://ybtest.sarc-iitb.org/login-android";
    private final String loginUri = "https://loginuri";
    private final String guestUri = "https://guesturi";
    public String authCode = null;
    public String fcmId = null;
    private SessionManager session;
    private Context mContext = this;
    private ProgressDialog progressDialog;
    private RetrofitInterface retrofitInterface;

    public RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(mContext);
        if (session.isLoggedIn()) {
            Log.println(Log.INFO,TAG,"Session IS Logged in onCreate");
            openMainActivity();
        } else {
            setContentView(R.layout.activity_login);
            progressDialog = new ProgressDialog(LoginActivity.this);
        }
    }

    private void openMainActivity() {
        Intent i = new Intent(mContext, MainActivity.class);
        /* Pass FCM data if available */
        Intent myIntent = getIntent();
        if (myIntent.getExtras() != null) {
            i.putExtra(Constants.MAIN_INTENT_EXTRAS, myIntent.getExtras());
        }
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Initialize

        ServiceGenerator serviceGenerator = new ServiceGenerator(getApplicationContext());
        this.retrofitInterface = serviceGenerator.getRetrofitInterface();

        // Get FCM Id
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                fcmId = instanceIdResult.getToken();
            }
        });

        // Login if intent is present
        String action = getIntent().getAction();
        String data = getIntent().getDataString();
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            Uri query = Uri.parse(data);
            authCode = query.getQueryParameter("code");
            if (authCode != null) {
                /* Show progress dialog */
                progressDialog.setMessage("Logging In");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
                /* Perform the login */
                login(authCode);
            } else {
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show();
            }
        }

        // Setup web view placeholder
        WebView webview = findViewById(R.id.login_webview);
        webview.setWebViewClient(new WvClient());
        webview.loadUrl("file:///android_asset/login.html");
    }

    private void login(String authCode) {
        /* This can be null if play services is hung */
        RetrofitInterface retrofitInterface = getRetrofitInterface();
        Call<LoginResponse> call;
        if (fcmId == null) {
            call = retrofitInterface.login(authCode, redirectUri);
        }
        else {
            call = retrofitInterface.login(authCode, redirectUri, fcmId);
        }
        call.enqueue(new EmptyCallback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    session.createLoginSession(loginResponse.getStudent_profile().getFullName(), loginResponse.getStudent_profile(), loginResponse.getSessionID());
                    progressDialog.dismiss();
                    openMainActivity();
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Authorization Failed!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
    private class WvClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
            /* Actual login button */
            if (url.startsWith(loginUri)) {
                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                        .addDefaultShareMenuItem()
                        .setToolbarColor(LoginActivity.this.getResources()
                                .getColor(R.color.colorPrimary))
                        .setShowTitle(true)
                        .build();
                customTabsIntent.launchUrl(LoginActivity.this, Uri.parse("https://gymkhana.iitb.ac.in/profiles/oauth/authorize/?client_id=yb-test&response_type=code&redirect_uri=http://ybtest.sarc-iitb.org/login-android&scope=basic%20profile%20insti_address%20program&state="));
                return true;
            }
            /* Load URL */
            view.loadUrl(url);
            return false;
        }
    }
}
