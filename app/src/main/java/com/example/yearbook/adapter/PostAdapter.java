package com.example.yearbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yearbook.Constants;
import com.example.yearbook.R;
import com.example.yearbook.Utils;
import com.example.yearbook.api.RetrofitInterface;
import com.example.yearbook.api.model.Post;
import com.example.yearbook.interfaces.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerViewAdapter<Post> {

    public PostAdapter(List<Post> posts, ItemClickListener itemClickListener) {
        super(posts, itemClickListener);
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NonNull ViewGroup parent, Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.post_card, parent, false);

        final PostAdapter.PostViewHolder postViewHolder = new PostAdapter. PostViewHolder(postView);
        postView.setOnClickListener(v -> itemClickListener.onItemClick(v, postViewHolder.getAdapterPosition()));
        postView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
        return postViewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder recyclerHolder, int position) {
        if (recyclerHolder instanceof PostViewHolder) {
            ((PostViewHolder) recyclerHolder).bindHolder(position);
        } else {
            ((ProgressViewHolder) recyclerHolder).progressBar.setIndeterminate(true);
        }
    }

    private static final String TAG = PostAdapter.class.getSimpleName();
    private List<Post> posts = new ArrayList<>();

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView_post;
        private CircleImageView written_by_image;
        private TextView post_content;
        private TextView written_by_name;
        private TextView written_for_name;
        private CircleImageView written_for_image;


        private final RetrofitInterface retrofitInterface = Utils.getRetrofitInterface();

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView_post = itemView.findViewById(R.id.cardView_post);
            written_by_name = itemView.findViewById(R.id.written_by_name);
            written_for_name = itemView.findViewById(R.id.written_for_name);
            written_by_image = itemView.findViewById(R.id.written_by_image);
            written_for_image = itemView.findViewById(R.id.written_for_image);
            post_content = itemView.findViewById((R.id.post_content));
        }

        private void bindHolder(final int position) {
            final Post post = posts.get(position);
            try {
                String written_by_profileUrl = post.getWritten_by_profile().getProfile_image();
                Picasso.get().load(Constants.BASE_URL + written_by_profileUrl).placeholder(R.drawable.user_placeholder).into(written_by_image);
                String written_for_profileUrl = post.getWritten_for_profile().getProfile_image();
                Picasso.get().load(Constants.BASE_URL + written_for_profileUrl).placeholder(R.drawable.user_placeholder).into(written_for_image);
                written_for_name.setText(post.getWritten_for_profile().getFullName());
                written_by_name.setText(post.getWritten_by_profile().getFullName());
                post_content.setText(post.getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = inflater.inflate(R.layout.post_card, parent, false);
//        final PostViewHolder postViewHolder = new PostViewHolder(view);
//        return postViewHolder;
//    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

//    public PostAdapter(Activity activity, Fragment fragment) {
//        super(activity,fragment);
//        this.activity = activity;
//        this.fragment = fragment;
//        this.inflater = LayoutInflater.from(activity);
//    }


//    private void preEditComments(CardView cardView, TextView textViewComment, EditText editTextComment, ImageButton send_comment, ImageButton back_button) {
//        cardView.setClickable(false);
//        cardView.setLongClickable(false);
//        textViewComment.setVisibility(View.GONE);
//        editTextComment.setVisibility(View.VISIBLE);
//        editTextComment.setText(textViewComment.getText().toString());
//        send_comment.setVisibility(View.VISIBLE);
//        back_button.setVisibility(View.VISIBLE);
//        editTextComment.requestFocus();
//    }
//
//    private void postEditComments(CardView cardView, TextView textViewComment, EditText editTextComment, ImageButton send_comment, ImageButton back_button, Activity activity) {
//        editTextComment.clearFocus();
//        textViewComment.setVisibility(View.VISIBLE);
//        send_comment.setVisibility(View.GONE);
//        back_button.setVisibility(View.GONE);
//        editTextComment.setVisibility(View.GONE);
//        cardView.setClickable(true);
//        cardView.setLongClickable(true);
//        MainActivity.hideKeyboard(activity);
//    }
}
