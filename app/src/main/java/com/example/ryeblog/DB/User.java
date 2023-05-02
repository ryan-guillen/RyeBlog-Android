package com.example.ryeblog.DB;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="users")
public class User {
    public User(String username, String bio) {
        this.username = username;
        this.bio = bio;
    }

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "bio")
    public String bio;
}
