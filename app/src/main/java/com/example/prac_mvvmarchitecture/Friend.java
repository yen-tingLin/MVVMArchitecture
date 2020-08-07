package com.example.prac_mvvmarchitecture;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "friend_table")
public class Friend {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String email;
    private String location;
    //@ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    //private byte[] image;

    public Friend(String name, String email, String location) {
        this.name = name;
        this.email = email;
        this.location = location;
        //this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    /*
    public byte[] getImage() {
        return image;
    }

     */

}
