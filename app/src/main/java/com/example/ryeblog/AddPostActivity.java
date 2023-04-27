package com.example.ryeblog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ryeblog.DB.Post;
import com.example.ryeblog.DB.PostDatabase;

public class AddPostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    public void onAddPost(View view) {
        SharedPreferences sharedPref = this.getSharedPreferences("application", Context.MODE_PRIVATE);
        String user = sharedPref.getString("USERNAME", "-1");

        EditText editTitle = findViewById(R.id.title);
        EditText editText = findViewById(R.id.text);
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
