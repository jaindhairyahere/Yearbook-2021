package com.example.yearbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.TypedValue;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.yearbook.api.RetrofitInterface;
import com.example.yearbook.api.model.Notification;
import com.example.yearbook.api.model.Student_Profile;
import com.example.yearbook.fragment.SettingsFragment;
import com.example.yearbook.fragment.StudentProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import io.noties.markwon.Markwon;
import io.noties.markwon.ext.tables.TablePlugin;
import io.noties.markwon.ext.tables.TableTheme;
import io.noties.markwon.html.HtmlPlugin;
import io.noties.markwon.image.picasso.PicassoImagesPlugin;
import io.noties.markwon.linkify.LinkifyPlugin;

public final class Utils {
//    public static UpdatableList<Event> eventCache = new UpdatableList<>();
    public static UpdatableList<Notification> notificationCache = null;
//    public static UpdatableList<Body> bodyCache = new UpdatableList<>();
    public static Student_Profile currentUserCache = null;

    public Utils() {
        super();
    }

    private static String sessionId;
    private static RetrofitInterface retrofitInterface;
    public static Gson gson;
    public static boolean isDarkTheme = false;
    private static Markwon markwon;
    private static Markwon markwonLinkify;

    public static final void loadImageWithPlaceholder(final ImageView imageView, final String url) {
        Picasso.get()
                .load(resizeImageUrl(url))
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.get()
                                .load(url)
                                .placeholder(imageView.getDrawable())
                                .into(imageView);
                    }

                    @Override
                    public void onError(Exception ex) {
                        // Do nothing
                    }
                });
    }

    public static final String resizeImageUrl(String url) {
        return resizeImageUrl(url, 200);
    }

    public static final String resizeImageUrl(String url, Integer dim) {
//        if (url == null) {
//            return url;
//        }
//        return url.replace("api.insti.app/static/", "img.insti.app/static/" + dim.toString() + "/");
        return url;
    }

    public static Markwon getMarkwon() {
        return getMarkwon(true);
    }

    public static Markwon getMarkwon(boolean linkify) {
        return linkify ? markwonLinkify : markwon;
    }
//
    private static Markwon.Builder getMarkwonBuilder(Context context) {
        final TableTheme tableTheme = TableTheme.create(context).asBuilder()
                .tableBorderWidth(1)
                .tableCellPadding(10)
                .build();

        return Markwon.builder((context))
                .usePlugin(HtmlPlugin.create())
                .usePlugin(TablePlugin.create(tableTheme))
                .usePlugin(PicassoImagesPlugin.create(Picasso.get()));
    }

    public static void makeMarkwon(Context context) {
        // Build without linkify
        markwon = getMarkwonBuilder(context).build();

        // Build with linkify
        markwonLinkify = getMarkwonBuilder(context)
                .usePlugin(LinkifyPlugin.create())
                .build();
    }

    /**
     * Update the open fragment
     */
    public static final void updateFragment(Fragment fragment, FragmentActivity fragmentActivity) {
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_up, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out_down);
        ft.replace(R.id.constraintlayout_for_fragment, fragment, getTag(fragment));
        ft.addToBackStack(getTag(fragment));
        ft.commit();
    }
//
//    public static void updateSharedElementFragment(final Fragment fragment, final Fragment currentFragment, Map<View, String> sharedElements) {
//        FragmentTransaction ft = currentFragment.getActivity().getSupportFragmentManager().beginTransaction();
//
//        Transition transition = new DetailsTransition();
//
//        /* Set up transitions */
//        fragment.setSharedElementEnterTransition(transition);
//        fragment.setEnterTransition(new Slide());
//        currentFragment.setExitTransition(new Fade());
//        fragment.setSharedElementReturnTransition(transition);
//
//        /* Set transition for parent in case it is a child */
//        if (currentFragment instanceof TransitionTargetChild) {
//            ((TransitionTargetChild) currentFragment).getParent().setExitTransition(new Fade());
//        }
//
//        transition.addListener(new Transition.TransitionListener() {
//            @Override
//            public void onTransitionStart(Transition transition) {
//            }
//
//            @Override
//            public void onTransitionEnd(Transition transition) {
//                if (fragment instanceof TransitionTargetFragment) {
//                    ((TransitionTargetFragment) fragment).transitionEnd();
//                }
//
//                if (currentFragment instanceof TransitionTargetFragment) {
//                    ((TransitionTargetFragment) currentFragment).transitionEnd();
//                }
//            }
//
//            @Override
//            public void onTransitionCancel(Transition transition) {}
//
//            @Override
//            public void onTransitionPause(Transition transition) {}
//
//            @Override
//            public void onTransitionResume(Transition transition) {}
//        });
//
//        /* Add all shared elements */
//        for (Map.Entry<View, String> entry : sharedElements.entrySet()) {
//            ft.addSharedElement(entry.getKey(), entry.getValue());
//        }
//
//        /* Update the fragment */
//        ft.replace(R.id.framelayout_for_fragment, fragment, getTag(fragment))
//                .addToBackStack(getTag(fragment))
//                .commit();
//    }
//
//    public static BodyFragment getBodyFragment(Body body, boolean sharedElements) {
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.BODY_JSON, new Gson().toJson(body));
//        bundle.putBoolean(Constants.NO_SHARED_ELEM, !sharedElements);
//        BodyFragment bodyFragment = new BodyFragment();
//        bodyFragment.setArguments(bundle);
//        return bodyFragment;
//    }
//
//    public static void openBodyFragment(Body body, FragmentActivity fragmentActivity) {
//        updateFragment(getBodyFragment(body, false), fragmentActivity);
//    }
//
//    public static void openBodyFragment(Body body, Fragment currentFragment, View sharedAvatar) {
//        Map<View, String> sharedElements = new HashMap<>();
//        sharedElements.put(sharedAvatar, "sharedAvatar");
//        updateSharedElementFragment(
//                getBodyFragment(body, true), currentFragment, sharedElements
//        );
//    }
//
//    public static EventFragment getEventFragment(Event event, boolean sharedElements) {
//        String eventJson = new Gson().toJson(event);
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.EVENT_JSON, eventJson);
//        bundle.putBoolean(Constants.NO_SHARED_ELEM, !sharedElements);
//        EventFragment eventFragment = new EventFragment();
//        eventFragment.setArguments(bundle);
//        return eventFragment;
//    }
//
//    public static void openEventFragment(Event event, FragmentActivity fragmentActivity) {
//        updateFragment(getEventFragment(event, false), fragmentActivity);
//    }
//
//    public static void openEventFragment(Event event, Fragment currentFragment, View sharedAvatar) {
//        Map<View, String> sharedElements = new HashMap<>();
//        sharedElements.put(sharedAvatar, "sharedAvatar");
//        updateSharedElementFragment(
//                getEventFragment(event, true), currentFragment, sharedElements
//        );
//    }
//
    public static void openUserFragment(Student_Profile profile, FragmentActivity fragmentActivity) {
        openUserFragment(profile.getStudent().getSso_id(), fragmentActivity);
    }

    public static void openUserFragment(Student_Profile profile, Fragment currentFragment, View sharedAvatar) {
        Map<View, String> sharedElements = new HashMap<>();
        sharedElements.put(sharedAvatar, "sharedAvatar");
//        updateSharedElementFragment(
//                UserFragment.newInstance(user), currentFragment, sharedElements
//        );
    }

    public static void openUserFragment(String userId, FragmentActivity fragmentActivity) {
        updateFragment(StudentProfileFragment.newInstance(userId), fragmentActivity);
    }

    public static void setSessionId(String sessionId1) {
        sessionId = sessionId1;
    }

    public static String getSessionIDHeader() {
        return "sessionid=" + sessionId;
    }

    public static RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
    }

    public static void setRetrofitInterface(RetrofitInterface retrofitInterface) {
        Utils.retrofitInterface = retrofitInterface;
    }

    public static void openWebURL(Context context, String URL) {
        if (URL != null && !URL.isEmpty()) {
            Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            context.startActivity(browse);
        }
    }

    public static void makeGson() {
        Utils.gson = new Gson();
    }

    public static void changeTheme(SettingsFragment fragment, boolean darkTheme) {
        isDarkTheme = darkTheme;
        FragmentActivity fragmentActivity = fragment.getActivity();
        fragmentActivity.setTheme(darkTheme ? R.style.AppThemeDark : R.style.AppTheme);

        // Set background color of activity
        fragmentActivity.findViewById(R.id.drawer_layout).setBackgroundColor(
                getAttrColor(fragmentActivity, R.attr.themeColor));

        // Put in a new settings fragment
        Fragment newFragment = new SettingsFragment();
        newFragment.setArguments(fragment.getArguments());
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        fm.popBackStack();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(getTag(fragment));
        ft.replace(R.id.constraintlayout_for_fragment, newFragment, getTag(fragment)).commit();
    }

    public static void setSelectedMenuItem(Activity activity, int id) {
        if (activity != null) {
            NavigationView navigationView = activity.findViewById(R.id.nav_view);
            if (navigationView != null) {
                navigationView.setCheckedItem(id);
            }
        }
    }

    public static int getAttrColor(Context context, int attrId) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrId, typedValue, true);
        return typedValue.data;
    }
//
//    public static void setupFollowButton(Context context, Button view, boolean follows) {
//        // Get background colors
//        final int themeColor = Utils.getAttrColor(context, R.attr.themeColor);
//        final int accent = context.getResources().getColor(R.color.colorAccent);
//
//        // Get font colors
//        final int themeColorInverse = Utils.getAttrColor(context, R.attr.themeColorInverse);
//        final int black = Color.BLACK;
//
//        // Set background and foreground colors
//        view.setBackgroundColor(follows ? accent : themeColor);
//        view.setTextColor(follows ? black : themeColorInverse);
//    }

    @RequiresApi(21)
    public static void clearCookies(Context context) {
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
    }

    public static String getTag(Fragment fragment) {
        String TAG = fragment.getTag();
        try {
            TAG = (String) fragment.getClass().getField("TAG").get(fragment);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        return TAG;
    }

    public static String generateDayString(int day) {
        switch (day) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                throw new IndexOutOfBoundsException("DayIndexOutOfBounds: " + day);
        }
    }
}
