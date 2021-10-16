//package com.example.yearbook.fragment;
//
//import android.annotation.SuppressLint;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.yearbook.Constants;
//import com.example.yearbook.R;
//import com.example.yearbook.activity.MainActivity;
//import com.example.yearbook.adapter.ScrollResponseAdapter;
//import com.example.yearbook.api.RetrofitInterface;
//import com.example.yearbook.api.model.Post;
//import com.example.yearbook.api.response.ScrollResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class ScrollFragment extends Fragment {
//    private final String TAG = ScrollFragment.class.getSimpleName();
//    private RecyclerView recyclerViewPosts;
//    private View mView;
//    private ScrollResponseAdapter postListAdapter;
//    private List<Post> postList;
//    int new_start;
//    int new_end;
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        new_start = 0;
//        new_end = 5;
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_post, container, false);
//        postList = new ArrayList<>();
//        postListAdapter = new ScrollResponseAdapter(getActivity(), this);
//        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
//        recyclerViewPosts = (RecyclerView) view.findViewById(R.id.recyclerView_post);
//        recyclerViewPosts.setLayoutManager(linearLayoutManager);
//        recyclerViewPosts.setHasFixedSize(true);
//        recyclerViewPosts.setAdapter(postListAdapter);
////
//        mView = view;
//        return view;
//    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        callScrollAPI();
//    }
//
//
//    private void addNewPost(Post post) {
//        postList.add(post);
//        postListAdapter.setPosts(postList);
//        postListAdapter.notifyItemInserted(postList.indexOf(post));
//        postListAdapter.notifyItemRangeChanged(0, postListAdapter.getItemCount());
//        recyclerViewPosts.post(new Runnable() {
//            @Override
//            public void run() {
//                MainActivity.hideKeyboard(getActivity());
//            }
//        });
//    }
//
//    private void addPostToView(ArrayList<Post> postList) {
//        this.postList.addAll(postList);
//        postListAdapter.setPosts(postList);
//        postListAdapter.notifyDataSetChanged();
//    }
//
//    public void callScrollAPI(){
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.level(HttpLoggingInterceptor.Level.BODY);
//
//
//        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//        Retrofit retrofit = new Retrofit.Builder().client(httpClient).addConverterFactory(GsonConverterFactory.create()).baseUrl(Constants.BASE_URL).build();
//
//        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
//        retrofitInterface.getScrollFeed(new_start,new_end).enqueue(new Callback<ScrollResponse>() {
//            @Override
//            public void onResponse(Call<ScrollResponse> call, Response<ScrollResponse> response) {
//                if(response.isSuccessful()) {
//                    ScrollResponse scroll_response = response.body();
//                    if(new_start != scroll_response.getNew_start() && new_end != scroll_response.getNew_end()){
//                        new_start = scroll_response.getNew_start();
//                        new_end = scroll_response.getNew_end();
//                        addPostToView(scroll_response.getPosts());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ScrollResponse> call, Throwable t) {
//
//            }
//        });
//        Log.println(Log.ASSERT,TAG,"Called Scroll API");
//    }
//}
