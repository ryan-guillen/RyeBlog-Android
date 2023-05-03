package com.example.ryeblog;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryeblog.DB.Post;
import com.example.ryeblog.DB.PostViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PostViewModel postViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.lstPosts);
        PostListAdapter adapter = new PostListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, adapter::setPosts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.action_settings:
                i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.add:
                i = new Intent(this, AddPostActivity.class);
                startActivity(i);
                return true;
            case R.id.search:
                searchDialog();
                return true;
            case R.id.logout:
                SharedPreferences sharedPref = this.getSharedPreferences("application", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("USERNAME", "-1");
                editor.apply(); // logs user out

                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void searchDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_search, null);
        builder.setView(dialogView);
        builder.setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText editText = dialogView.findViewById(R.id.searchUsername);
                String searchUsername = editText.getText().toString();

                if (searchUsername.equals("")) { // if username is empty
                    Toast.makeText(MainActivity.this, "You need to enter a username.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                i.putExtra("USERNAME", searchUsername);
                MainActivity.this.startActivity(i);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setTitle("Search by user");
        AlertDialog dialog = builder.create();
        dialog.show();
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
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            View itemView;
            if (sharedPref.getBoolean("switch", false))
                itemView = layoutInflater.inflate(R.layout.post_item, parent, false);
            else
                itemView = layoutInflater.inflate(R.layout.post_item2, parent, false);
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