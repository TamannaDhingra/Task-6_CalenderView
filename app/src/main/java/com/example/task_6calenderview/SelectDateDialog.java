package com.example.task_6calenderview;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SelectDateDialog extends Dialog {

    EditText title,description;
    Button savebtn;
    CalenderData data;
    Context context;
    String fulldate;
    DaoInterface database;

    public SelectDateDialog(@NonNull Context context, String fulldate) {
        super(context);
        this.context=context;
        this.fulldate=fulldate;
        database=RoomDataBase.instance.getDao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date_dialog);

        title=findViewById(R.id.ettitle);
        description=findViewById(R.id.etdes);
        savebtn=findViewById(R.id.saveeventbtn);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        data=new CalenderData();
                        data.setDate(fulldate);
                        data.setEventName(title.getText().toString());
                        data.setDes(description.getText().toString());
                        database.setData(data);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
        });

    }

}