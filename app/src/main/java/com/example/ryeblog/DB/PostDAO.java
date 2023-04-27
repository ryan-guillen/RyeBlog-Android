package com.example.ryeblog.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDAO {
    @Query("SELECT * FROM posts WHERE username = :username " +
            "ORDER BY title")
    LiveData<List<Post>> getPosts(String username);

    @Query("SELECT * FROM posts")
    LiveData<List<Post>> getAll();

    @Query("SELECT * FROM posts WHERE id = :id")
    Post getById(int id);

    @Insert
    void insert(Post... posts);

    @Update
    void update(Post... posts);

    @Delete
    void delete(Post... posts);

    @Query("DELETE FROM posts WHERE id = :id")
    void delete(int id);
}
