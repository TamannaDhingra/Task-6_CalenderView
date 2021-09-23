package com.example.task_6calenderview;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoInterface {

    @Insert
    void setData(CalenderData data);

    @Query("select * from Calender_tbl")
    LiveData<List<CalenderData>> getData();

    @Delete
    void deleteItem(CalenderData calenderData);



}
