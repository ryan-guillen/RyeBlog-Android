package com.example.ryeblog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPref = this.getSharedPreferences("application", Context.MODE_PRIVATE);
        TextView login = findViewById(R.id.login);
        login.setText(sharedPref.getString("USERNAME", "no val"));
    }



    public void onLogin(View view) {
        EditText editText = findViewById(R.id.username);
        String username = editText.getText().toString();
        if (!username.equals("")) {
            SharedPreferences sharedPref = this.getSharedPreferences("application", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("USERNAME", username);
            editor.apply();

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

    }
}