package com.example.prac_mvvmarchitecture;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FriendDao {

    @Query("SELECT * FROM friend_table ORDER BY name ASC")
    LiveData<List<Friend>> getAll();

    @Insert
    void insert(Friend friend);

    @Update
    void update(Friend friend);

    @Delete
    void delete(Friend friend);

    @Query("DELETE FROM friend_table")
    void deleteAll();
}
