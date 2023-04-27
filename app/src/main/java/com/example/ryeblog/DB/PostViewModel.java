package com.example.ryeblog.DB;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private LiveData<List<Post>> posts;

    public PostViewModel (Application application) {
        super(application);
    }

    public LiveData<List<Post>> getAllPosts() {
        posts = PostDatabase.getDatabase(getApplication()).postDAO().getAll();
        return posts;
    }

    public LiveData<List<Post>> getPosts(String username) {
        posts = PostDatabase.getDatabase(getApplication()).postDAO().getPosts(username);
        return posts;
    }
}
