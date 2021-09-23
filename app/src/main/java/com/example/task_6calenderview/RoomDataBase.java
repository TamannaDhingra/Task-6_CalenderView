package com.example.task_6calenderview;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CalenderData.class},version = 1)
 abstract class RoomDataBase extends RoomDatabase {

    public abstract DaoInterface getDao();
    public static RoomDataBase instance;

    public static synchronized  RoomDataBase getInstance(Context context)
    {
        if (instance==null)
        {
            instance= Room.databaseBuilder(context,RoomDataBase.class,"db_CalernderData").build();
        }
        return instance;

    }


}
