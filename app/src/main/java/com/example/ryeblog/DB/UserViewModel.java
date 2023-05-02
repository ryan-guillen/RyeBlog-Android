package com.example.ryeblog.DB;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private LiveData<List<User>> users;
    private User user;

    public UserViewModel(Application application) {
        super(application);
    }

    public LiveData<List<User>> getAllUsers() {
        users = UserDatabase.getDatabase(getApplication()).userDAO().getAll();
        return users;
    }

    public User getUser(String username) {
        user = UserDatabase.getDatabase(getApplication()).userDAO().getUser(username);
        return user;
    }
}
