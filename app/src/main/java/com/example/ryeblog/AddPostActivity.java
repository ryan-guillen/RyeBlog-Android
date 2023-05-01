package com.example.ryeblog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ryeblog.DB.Post;
import com.example.ryeblog.DB.PostDatabase;

public class AddPostActivity extends AppCompatActivity {
    String savedTitle;
    String savedText;
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_addpost);

        if (b != null) {
            savedTitle = b.getString("TITLE");
            savedText = b.getString("TEXT");
            EditText editTitle = findViewById(R.id.title);
            EditText editText = findViewById(R.id.text);
            editTitle.setText(savedTitle);
            editText.setText(savedText);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    protected void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        b.putString("TITLE", savedTitle);
        b.putString("TEXT", savedText);
    }

    public void confirmDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                addPost();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setTitle("Confirm").setMessage("Are you sure?");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addPost() {
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
