package com.example.ryeblog;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ryeblog.DB.PostViewModel;
import com.example.ryeblog.DB.User;
import com.example.ryeblog.DB.UserDatabase;
import com.example.ryeblog.DB.UserViewModel;

import java.util.List;

public class SignupActivity extends AppCompatActivity implements UserDatabase.UserListener {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().getValue();
    }

    @Override
    public void onUserReturned(User user) {
        System.out.println("user returned");
        if (user != null) {
            Toast.makeText(this, "Username taken!",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            EditText editUser = findViewById(R.id.username);
            EditText editBio = findViewById(R.id.bio);
            String username = editUser.getText().toString();
            String bio = editBio.getText().toString();
            User newUser = new User(username, bio);
            UserDatabase.insert(newUser);
            Toast.makeText(this, "Account successfully created!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onSignup(View view) {
        EditText editUser = findViewById(R.id.username);
        EditText editBio = findViewById(R.id.bio);
        String username = editUser.getText().toString();
        String bio = editBio.getText().toString();
        if (username.equals("") || bio.equals("")) {
            Toast.makeText(this, "You need to fill in all fields.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            UserDatabase.getUser(username, this);
        }
    }
}