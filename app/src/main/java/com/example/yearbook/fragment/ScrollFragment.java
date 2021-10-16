package com.example.yearbook.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.yearbook.R;
import com.example.yearbook.Utils;
import com.example.yearbook.adapter.PostAdapter;
import com.example.yearbook.api.RetrofitInterface;
import com.example.yearbook.api.model.Post;
import com.example.yearbook.api.response.ScrollResponse;

import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScrollFragment extends RecyclerViewFragment<Post, PostAdapter> {

    public ScrollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("News Feed");
        Utils.setSelectedMenuItem(getActivity(), R.id.nav_menu_infi_scroll);

        setHasOptionsMenu(true);
        updateData();

        postType = Post.class;
        adapterType = PostAdapter.class;
        recyclerView = getActivity().findViewById(R.id.recyclerView_post);
        swipeRefreshLayout = getActivity().findViewById(R.id.training_feed_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });
    }

    @Override
    protected Call<List<Post>> getCall(RetrofitInterface retrofitInterface, String sessionIDHeader, int postCount) {
        return retrofitInterface.getNextPost(postCount, 10+postCount);
    }


}
