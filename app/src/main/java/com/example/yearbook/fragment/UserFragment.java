//package com.example.yearbook.fragment;
//
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.Point;
//import android.graphics.Rect;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.DecelerateInterpolator;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.widget.Toolbar;
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.tabs.TabLayout;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import app.insti.Constants;
//import app.insti.R;
//import app.insti.ShareURLMaker;
//import app.insti.Utils;
//import app.insti.adapter.TabAdapter;
//import app.insti.api.EmptyCallback;
//import app.insti.api.RetrofitInterface;
//import app.insti.api.model.Body;
//import app.insti.api.model.Event;
//import app.insti.api.model.Role;
//import app.insti.api.model.User;
//import app.insti.interfaces.CardInterface;
//import retrofit2.Call;
//import retrofit2.Response;
//
//import static android.view.View.VISIBLE;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class UserFragment extends BackHandledFragment implements TransitionTargetFragment {
//    private User user = null;
//
//    // Hold a reference to the current animator,
//    // so that it can be canceled mid-way.
//    private Animator mCurrentAnimator;
//
//    // The system "short" animation time duration, in milliseconds. This
//    // duration is ideal for subtle animations or animations that occur
//    // very frequently.
//    private int mShortAnimationDuration;
//    private boolean zoomMode;
//    private ImageView expandedImageView;
//    private Rect startBounds;
//    private float startScaleFinal;
//    private ImageView userProfilePictureImageView;
//    private boolean showingMin = false;
//
//    public UserFragment() {
//        // Required empty public constructor
//    }
//
//    public static UserFragment newInstance(String userID) {
//        UserFragment fragment = new UserFragment();
//        Bundle args = new Bundle();
//        args.putString(Constants.USER_ID, userID);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public static UserFragment newInstance(User minUser) {
//        UserFragment fragment = new UserFragment();
//        Bundle args = new Bundle();
//        args.putString(Constants.USER_JSON, Utils.gson.toJson(minUser));
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_user, container, false);
//    }
//
//    @Override
//    public void transitionEnd() {
//        if (getActivity() == null || getView() == null) return;
//        if (showingMin) {
//            showingMin = false;
//            loadUser(user.getUserID());
//        }
//    }
//
//    @Override
//    public boolean onBackPressed() {
//        if (zoomMode) {
//            zoomOut(expandedImageView, startBounds, startScaleFinal, userProfilePictureImageView);
//            zoomMode = false;
//            return true;
//        }
//        return false;
//    }
//
//
//    private void populateViews() {
//        if (getActivity() == null || getView() == null) return;
//
//        userProfilePictureImageView = getActivity().findViewById(R.id.user_profile_picture_profile);
//        TextView userNameTextView = getActivity().findViewById(R.id.user_name_profile);
//        TextView userRollNumberTextView = getActivity().findViewById(R.id.user_rollno_profile);
//        final TextView userEmailIDTextView = getActivity().findViewById(R.id.user_email_profile);
//        TextView userContactNumberTextView = getActivity().findViewById(R.id.user_contact_no_profile);
//        FloatingActionButton userShareFab = getActivity().findViewById(R.id.share_user_button);
//
//        Picasso.get()
//                .load(user.getUserProfilePictureUrl())
//                .placeholder(R.drawable.user_placeholder)
//                .into(userProfilePictureImageView);
//
//        userProfilePictureImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zoomImageFromThumb(userProfilePictureImageView);
//            }
//        });
//        mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
//
//        if (!showingMin) {
//            /* Show tabs */
//            getActivity().findViewById(R.id.tab_layout).setVisibility(VISIBLE);
//
//            /* Load lists */
//            final List<Role> roleList = user.getUserRoles();
//            final List<Body> bodyList = user.getUserFollowedBodies();
//            final List<Event> eventList = user.getUserGoingEvents();
//            final List<Role> formerRoleList = user.getUserFormerRoles();
//
//            /* Construct user profile */
//            final List<CardInterface> profile = new ArrayList<>(roleList);
//            for (Role role : formerRoleList) {
//                Role temp = new Role(role);
//                temp.setRoleName("Former " + role.getRoleName() + " " + role.getRoleYear());
//                profile.add(temp);
//            }
//            profile.addAll(user.getUserAchievements());
//
//            List<Event> eventInterestedList = user.getUserInterestedEvents();
//            eventList.removeAll(eventInterestedList);
//            eventList.addAll(eventInterestedList);
//            GenericRecyclerViewFragment frag1 = GenericRecyclerViewFragment.newInstance(profile);
//            BodyRecyclerViewFragment frag2 = BodyRecyclerViewFragment.newInstance(bodyList);
//            EventRecyclerViewFragment frag3 = EventRecyclerViewFragment.newInstance(eventList);
//
//            frag1.parentFragment = this;
//            frag2.parentFragment = this;
//            frag3.parentFragment = this;
//
//            TabAdapter tabAdapter = new TabAdapter(getChildFragmentManager());
//            tabAdapter.addFragment(frag1, "Profile");
//            tabAdapter.addFragment(frag2, "Following");
//            tabAdapter.addFragment(frag3, "Events");
//
//            // Set up the ViewPager with the sections adapter.
//            ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewPager);
//            viewPager.setAdapter(tabAdapter);
//            viewPager.setOffscreenPageLimit(2);
//
//            TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tab_layout);
//            tabLayout.setupWithViewPager(viewPager);
//
//            userShareFab.show();
//            getActivity().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//        }
//
//        userNameTextView.setText(user.getUserName());
//        userRollNumberTextView.setText(user.getUserRollNumber());
//        if (user.getUserEmail() != null && !user.getUserEmail().equals("N/A")) {
//            userEmailIDTextView.setText(user.getUserEmail());
//        } else {
//            if (user.getUserRollNumber() != null)
//                userEmailIDTextView.setText(user.getUserRollNumber() + "@iitb.ac.in");
//        }
//
//        userEmailIDTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mail((String) userEmailIDTextView.getText());
//            }
//        });
//
//        if (!"N/A".equals(user.getUserContactNumber())) {
//            userContactNumberTextView.setText(user.getUserContactNumber());
//            userContactNumberTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    call(user.getUserContactNumber());
//                }
//            });
//        } else {
//            userContactNumberTextView.setVisibility(View.GONE);
//        }
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
//    }
//
//
//
//}
