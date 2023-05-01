package com.example.ryeblog.DB;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private LiveData<List<User>> users;

    public UserViewModel(Application application) {
        super(application);
    }

    public LiveData<List<User>> getAllUsers() {
        users = UserDatabase.getDatabase(getApplication()).userDAO().getAll();
        return users;
    }

    public LiveData<List<User>> getUser(String username) {
        users = UserDatabase.getDatabase(getApplication()).userDAO().getUser(username);
        return users;
    }
}
