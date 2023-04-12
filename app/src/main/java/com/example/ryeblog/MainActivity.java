package com.example.ryeblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ryeblog.DB.Post;
import com.example.ryeblog.DB.PostDatabase;
import com.example.ryeblog.DB.PostViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity  {
    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setSupportActionBar(findViewById(R.id.toolbar));

        RecyclerView recyclerView = findViewById(R.id.lstPosts);
        PostListAdapter adapter = new PostListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, adapter::setPosts);

        System.out.println("item count: " + adapter.getItemCount());
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
        public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.post_item, parent, false);
            return new PostViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PostViewHolder holder, int position) {
            if (posts != null) {
                Post current = posts.get(position);
                holder.post = current;
                holder.titleView.setText(current.title);
                holder.userView.setText(current.username);
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