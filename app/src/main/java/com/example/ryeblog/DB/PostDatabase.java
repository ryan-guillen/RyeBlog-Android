package com.example.ryeblog.DB;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Post.class}, version = 3, exportSchema = false)
public abstract class PostDatabase extends RoomDatabase {
    public interface PostListener {
        void onPostReturned(Post post);
    }

    public abstract PostDAO postDAO();

    private static PostDatabase INSTANCE;

    public static PostDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PostDatabase.class) {
                if (INSTANCE == null) {
                    //createPostTable();
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PostDatabase.class, "post_database")
                            .addCallback(createPostDatabaseCallback)
                            .build();
                    System.out.println("instance was null");

                    //System.out.println("text: " + INSTANCE.postDAO().getById(0).text);
                }
            }

        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback createPostDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            createPostTable();
        }
    };

    private static void createPostTable() {
        for (int i = 0; i < DefaultContent.TITLE.length; i++) {
            insert(new Post(0, DefaultContent.USER[i], DefaultContent.TITLE[i], DefaultContent.TEXT[i]));
            System.out.println("title: " + DefaultContent.TITLE[i]);
        }
    }

    public static void getPost(int id, PostListener listener) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                listener.onPostReturned((Post) msg.obj);
            }
        };

        (new Thread(() -> {
            Message msg = handler.obtainMessage();
            msg.obj = INSTANCE.postDAO().getById(id);
            handler.sendMessage(msg);
        })).start();
    }

    public static void insert(Post post) {
        (new Thread(()-> INSTANCE.postDAO().insert(post))).start();
    }

    public static void delete(int postId) {
        (new Thread(() -> INSTANCE.postDAO().delete(postId))).start();
    }

    public static void update(Post post) {
        (new Thread(() -> INSTANCE.postDAO().update(post))).start();
    }
}
