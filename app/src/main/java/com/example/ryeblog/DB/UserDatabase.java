package com.example.ryeblog.DB;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public interface UserListener {
        void onUserReturned(User user);
    }

    public abstract UserDAO userDAO();

    private static UserDatabase INSTANCE;

    public static UserDatabase getDatabase(final Context context) {
        return INSTANCE;
    }

    private static RoomDatabase.Callback createUserDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //createUserTable();
        }
    };

    public static void getUser(String username, UserListener listener) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                listener.onUserReturned((User) msg.obj);
            }
        };

        (new Thread(() -> {
            Message msg = handler.obtainMessage();
            msg.obj = INSTANCE.userDAO().getUser(username);
            handler.sendMessage(msg);
        })).start();
    }

    public static void insert(User user) {
        (new Thread(() -> INSTANCE.userDAO().insert(user))).start();
    }

    public static void delete(User user) {
        (new Thread(() -> INSTANCE.userDAO().delete(user))).start();
    }

    public static void update(User user) {
        (new Thread(() -> INSTANCE.userDAO().update(user))).start();
    }
}
