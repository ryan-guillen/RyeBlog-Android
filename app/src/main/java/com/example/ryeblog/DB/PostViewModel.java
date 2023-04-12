package com.example.ryeblog.DB;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PostViewModel extends AndroidViewModel {
    private LiveData<List<Post>> posts;

    public PostViewModel (Application application) {
        super(application);
        posts = PostDatabase.getDatabase(getApplication()).postDAO().getAll();
    }

    public LiveData<List<Post>> getAllPosts() {
        return posts;
    }
}
