package com.example.ryeblog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ryeblog.DB.Post;
import com.example.ryeblog.DB.PostViewModel;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String searchUsername = "";
        if (extras != null) {
            searchUsername = extras.getString("USERNAME");
        }

        RecyclerView recyclerView = findViewById(R.id.lstPosts);
        PostListAdapter adapter = new PostListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getPosts(searchUsername).observe(this, adapter::setPosts);
    }

    public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {
        class PostViewHolder extends RecyclerView.ViewHolder {
            private final TextView titleView;
            private final TextView userView;
            private final TextView textView;

            private Post post;

            private PostViewHolder(View itemView) {
                super(itemView);
                titleView = itemView.findViewById(R.id.txtTitle);
                userView = itemView.findViewById(R.id.txtUser);
                textView = itemView.findViewById(R.id.txtText);
            }
        }

        private final LayoutInflater layoutInflater;
        private List<Post> posts; // Cached copy of posts

        PostListAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public PostListAdapter.PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.post_item, parent, false);
            return new PostListAdapter.PostViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PostListAdapter.PostViewHolder holder, int position) {
            if (posts != null) {
                Post current = posts.get(position);
                holder.post = current;
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                if (!sharedPref.getBoolean("switch", true)) {
                    holder.titleView.setText(current.title);
                    holder.userView.setText(current.username);
                }
                else {
                    holder.titleView.setText(current.username);
                    holder.userView.setText(current.title);
                } // TODO: fix, currently you have to refresh app to see change
                holder.textView.setText(current.text);
            } else {
                holder.titleView.setText("...intializing...");
                holder.userView.setText("...intializing...");
                holder.textView.setText("...intializing...");
            }
        }

        void setPosts(List<Post> posts){
            this.posts = posts;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (posts != null)
                return posts.size();
            else return 0;
        }
    }
}