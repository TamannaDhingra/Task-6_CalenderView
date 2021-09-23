package com.example.task_6calenderview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ViewModelClss extends ViewModel {
    DaoInterface database;

    public ViewModelClss() {
        database=RoomDataBase.instance.getDao();
    }

    public LiveData<List<CalenderData>> getData()
    {
        return database.getData();
    }

}

