package com.example.prac_mvvmarchitecture;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Friend.class}, version = 1, exportSchema = false)
public abstract class FriendDatabase extends RoomDatabase {

    private static FriendDatabase instance;
    public abstract FriendDao friendDau();

    public static synchronized FriendDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FriendDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTack(instance).execute();
        }
    };

    private static class PopulateDBAsyncTack extends AsyncTask<Void, Void, Void> {

        private FriendDao friendDao;

        public PopulateDBAsyncTack(FriendDatabase db) {
            this.friendDao = db.friendDau();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Friend f1 = new Friend("Banana", "banana@gmail.com", "Taipei");
            //Friend f2 = new Friend("CClemon", "lemon@gmail.com", "Taoyuan");
            //friendDao.insert(f1);
            //friendDao.insert(f2);
            return null;
        }
    }

}
