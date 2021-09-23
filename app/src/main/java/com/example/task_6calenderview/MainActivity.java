package com.example.task_6calenderview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    CalendarView calendarView;
    ImageButton addbtn;
    DaoInterface database;
    RecyclerAdapter adapter;
    Activity activity;
    RecyclerView recyclerView;
    List<CalenderData> list;
    String fulldate = "";
    ViewModelClss viewmodel;
    // LinearLayout linearLayout;
    TextView showalleventstxtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = findViewById(R.id.cv);
        // searchView = findViewById(R.id.searchbar);
        addbtn = findViewById(R.id.addbtn);
        database = RoomDataBase.getInstance(this).getDao();
        showalleventstxtv = findViewById(R.id.showallevents);
        //linearLayout = findViewById(R.id.linearmain);
        //  Snackbar.make(linearLayout, "long press to delete item", BaseTransientBottomBar.LENGTH_LONG).show();

        viewmodel = ViewModelProviders.of(this).get(ViewModelClss.class);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        list = new ArrayList<>();


        setDateLists("");
        //for inserting new events using dialog
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fulldate.isEmpty()) {
                    Toast.makeText(MainActivity.this, "select date first...", Toast.LENGTH_SHORT).show();
                } else {

                    SelectDateDialog dateDialog = new SelectDateDialog(MainActivity.this, fulldate);
                    dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dateDialog.show();
                }
            }
        });

        showalleventstxtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewmodel.getData().observe(MainActivity.this, new Observer<List<CalenderData>>() {
                    @Override
                    public void onChanged(List<CalenderData> calenderData) {
                        list = calenderData;
                        adapter = new RecyclerAdapter(list, MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        });


        //getting calender date
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year1, int month1, int dayOfMonth) {


                fulldate = String.valueOf(dayOfMonth + "-" + (month1 + 1) + "-" + year1);
                String date = "";
                list.clear();
                date = String.valueOf(dayOfMonth + "-" + (month1 + 1) + "-" + year1);
                setDateLists(date);

            }
        });


    }

    public void setDateLists(String date) {
        //viewmodel and livedata for getting live results.
        list.clear();

        viewmodel.getData().observe(this, new Observer<List<CalenderData>>() {
            @Override
            public void onChanged(List<CalenderData> calenderData) {

                if (!date.equals("")) {
                    for (int i = 0; i < calenderData.size(); i++) {
                        CalenderData data = calenderData.get(i);
                        if (date.equals(data.getDate())) {
                            list.add(data);
                        }


                    }
                } else {
                    list = calenderData;
                    adapter = new RecyclerAdapter(list, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }
                adapter = new RecyclerAdapter(list, MainActivity.this);
                recyclerView.setAdapter(adapter);

            }

        });
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);


            MenuItem item = menu.findItem(R.id.searchviewbar);
            android.widget.SearchView searchView=(android.widget.SearchView)item.getActionView();
           // SearchView searchView=(SearchView)item.getActionView();
            searchView.setMaxWidth(Integer.MAX_VALUE);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    adapter.getFilter().filter(s);
                    return false;
                }
            });
            return super.onCreateOptionsMenu(menu);
        }
    }



