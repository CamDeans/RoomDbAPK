package com.example.roomdemodb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Data {
    public Data(String data) {
        this.data = data;
    }

    @Ignore
    public Data(long id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    long id;

    @ColumnInfo(name="data")
    String data;

    public long getId() {
        return id;
    }
}
