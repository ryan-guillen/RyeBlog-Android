package com.example.ryeblog;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ryeblog.DB.Post;
import com.example.ryeblog.DB.PostDatabase;

public class AddPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addpost);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public void onAddPost(View view) {
        EditText editUser = findViewById(R.id.username);
        EditText editTitle = findViewById(R.id.title);
        EditText editText = findViewById(R.id.text);
        String user = editUser.getText().toString();
        String title = editTitle.getText().toString();
        String text = editText.getText().toString();
        if (user.equals("") || title.equals("") || text.equals("")) {
            Toast.makeText(this, "You need to fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        Post post = new Post(0, user, title, text);
        PostDatabase.insert(post);
        finish();
    }
}
