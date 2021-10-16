package com.example.yearbook.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.yearbook.Constants;
import com.example.yearbook.R;
import com.example.yearbook.SessionManager;
import com.example.yearbook.Utils;
import com.example.yearbook.api.RetrofitInterface;
import com.example.yearbook.api.model.Student_Profile;
import com.example.yearbook.api.response.EmptyCallback;
import com.example.yearbook.interfaces.CardInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static android.view.View.VISIBLE;

public class StudentProfileFragment extends Fragment {
    private Student_Profile currentProfile;
    private SessionManager session;
    private ImageView student_profile_image;
    private TextView student_profile_name;
    private TextView student_profile_descriptors;
    private TextView student_profile_question;
    private TextView student_profile_answer;
    private TextView student_profile_tagline;

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;
    private boolean zoomMode;
    private ImageView expandedImageView;
    private Rect startBounds;
    private float startScaleFinal;
    private ImageView studentProfilePictureImageView;
    private boolean showingMin = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_student_profile, container, false);
        initialize(view);
        session = new SessionManager(getContext());
        if(session.isLoggedIn()){
            currentProfile = session.getCurrentUser();
        }
        else{
            throw new AssertionError();
        }
//        updateProfile();
        return view;
    }
    public StudentProfileFragment() {
        // Required empty public constructor
    }

    public static StudentProfileFragment newInstance(String userID) {
        StudentProfileFragment fragment = new StudentProfileFragment();
        Bundle args = new Bundle();
        args.putString(Constants.USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    public static StudentProfileFragment newInstance(Student_Profile profile) {
        StudentProfileFragment fragment = new StudentProfileFragment();
        Bundle args = new Bundle();
        args.putString(Constants.USER_JSON, Utils.gson.toJson(profile));
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_student_profile, container, false);
//    }

//    @Override
//    public void transitionEnd() {
//        if (getActivity() == null || getView() == null) return;
//        if (showingMin) {
//            showingMin = false;
//            loadUser(currentProfile.getStudent().getSso_id());
//        }
//    }
    @SuppressLint("DefaultLocale")
    private void updateProfile() {
//        if(currentProfile == null) throw new AssertionError();

        Picasso.get().load(currentProfile.getProfile_image()).placeholder(R.drawable.user_placeholder).into(student_profile_image);
        student_profile_name.setText(String.format("%s(%s)", currentProfile.getFullName(), currentProfile.getNickname()));
        student_profile_question.setText(currentProfile.getQuestion1());
        student_profile_answer.setText(currentProfile.getAnswer1());
        student_profile_tagline.setText(currentProfile.getTagline());
        student_profile_descriptors.setText(String.format("%s-%d | %s | Hostel %s | %s | %s | %s",
                currentProfile.getDegree(),
                currentProfile.getGraduation_year(),
                currentProfile.getDepartment(),
                currentProfile.getHostel(),
                currentProfile.getIb1(),
                currentProfile.getIb2(),
                currentProfile.getIb3()));
    }

    private void initialize(View view) {
        student_profile_image = view.findViewById(R.id.student_profile_image);
        student_profile_name = view.findViewById(R.id.student_profile_name);
        student_profile_descriptors = view.findViewById(R.id.student_profile_descriptors);
        student_profile_question = view.findViewById(R.id.student_profile_question);
        student_profile_answer = view.findViewById(R.id.student_profile_answer);
        student_profile_tagline = view.findViewById(R.id.student_profile_tagline);
        studentProfilePictureImageView = student_profile_image;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(StudentProfileFragment.this)
                        .navigate(R.id.action_StudentProfileFragment_to_PostFragment);
            }
        });
//        callHomeAPI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateProfile();
    }
    @Override
    public void onStart() {
        super.onStart();

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");

//        Bundle bundle = getArguments();
//
//        String userID = bundle.getString(Constants.USER_ID);
//        String userJson = bundle.getString(Constants.USER_JSON);
        if (currentProfile != null)
            populateViews();
//        else if (userID != null) {
//            loadUser(userID);
//        }
//        else if (userJson != null) {
//            currentProfile = Utils.gson.fromJson(userJson, Student_Profile.class);
//            showingMin = true;
//            populateViews();
//        }
    }
    public void loadUser(String sso_id) {
        RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();
        retrofitInterface.getStudentProfile(sso_id).enqueue(new EmptyCallback<Student_Profile>() {
            @Override
            public void onResponse(Call<Student_Profile> call, Response<Student_Profile> response) {
                if (response.isSuccessful()) {
                    if (getActivity() == null || getView() == null) return;
                    currentProfile = response.body();
                    populateViews();
                }
            }
        });
    }


    private void call(String contactNumber) {
        String uri = "tel:" + contactNumber;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(Intent.createChooser(intent, "Place a Call"));
    }

    private void mail(String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Let's have Coffee!");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void zoomOut(final ImageView expandedImageView, Rect startBounds, float startScaleFinal, final View thumbView) {
        expandedImageView.setBackgroundColor(0x00000000);
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Animate the four positioning/sizing properties in parallel,
        // back to their original values.
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator
                .ofFloat(expandedImageView, View.X, startBounds.left))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView,
                                View.Y, startBounds.top))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView,
                                View.SCALE_X, startScaleFinal))
                .with(ObjectAnimator
                        .ofFloat(expandedImageView,
                                View.SCALE_Y, startScaleFinal));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                thumbView.setAlpha(1f);
                expandedImageView.setVisibility(View.GONE);
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                thumbView.setAlpha(1f);
                expandedImageView.setVisibility(View.GONE);
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;
    }

    private void zoomImageFromThumb(final ImageView thumbView) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        expandedImageView = (ImageView) getActivity().findViewById(
                R.id.expanded_image_profile);
        expandedImageView.setImageDrawable(thumbView.getDrawable());

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        getActivity().findViewById(R.id.container_profile)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y,
                        startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
                expandedImageView.setBackgroundColor(Color.parseColor("#9E9E9E"));
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        startScaleFinal = startScale;
        zoomMode = true;
    }
//    public void callHomeAPI(Student_Profile s){
//
//        RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();
//        retrofitInterface.getStudentProfile(s.getStudent().getSso_id()).enqueue(new EmptyCallback<SearchStudentAPI>(){
//            @Override
//            public void onResponse(Call<SearchStudentAPI> call, Response<SearchStudentAPI> response){
//                ;
//            }
//        });
//    }
    private void populateViews() {
        if (getActivity() == null || getView() == null) return;

        initialize(getView());

        updateProfile();

        studentProfilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomImageFromThumb(studentProfilePictureImageView);
            }
        });
        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//        userShareFab.setOnClickListener(new View.OnClickListener() {
//            String shareUrl = ShareURLMaker.getUserURL(user);
//
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_SEND);
//                i.setType("text/plain");
//                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL");
//                i.putExtra(Intent.EXTRA_TEXT, shareUrl);
//                startActivity(Intent.createChooser(i, "Share URL"));
//            }
//        });
    }
}
