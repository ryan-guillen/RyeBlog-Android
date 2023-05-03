package com.example.ryeblog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryeblog.DB.User;
import com.example.ryeblog.DB.UserDatabase;
import com.example.ryeblog.DB.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPref = this.getSharedPreferences("application", Context.MODE_PRIVATE);
        // if already logged in, go to MainActivity instead
        if (!sharedPref.getString("USERNAME", "-1").equals("-1")) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsers().getValue();
    }

    public void onLogin(View view) {
        EditText editText = findViewById(R.id.username);
        String username = editText.getText().toString();
        if (!username.equals("")) {
            UserDatabase.getUser(username, (User user) -> {
                if (user == null) {
                    Toast.makeText(this, "That account does not exist.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    SharedPreferences sharedPref = this.getSharedPreferences("application", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("USERNAME", username);
                    editor.apply();

                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }
            });
        }
        else {
            Toast.makeText(this, "Please enter in a username", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSignup(View view) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }
}