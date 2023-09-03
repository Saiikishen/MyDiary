package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class updatedata extends AppCompatActivity
{
    int uid;
    EditText date, to_do;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatedata);

        date=findViewById(R.id.edit_date);
        to_do=findViewById(R.id.edit_todo);
        btn=findViewById(R.id.btn);

        uid=Integer.parseInt(getIntent().getStringExtra("uid"));
        date.setText(getIntent().getStringExtra("date"));
        to_do.setText(getIntent().getStringExtra("to do"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "room_db").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();
                userDao.updateById(uid,date.getText().toString(),to_do.getText().toString());
                startActivity(new Intent(getApplicationContext(),fetchdata.class));
                finish();
            }
        });
    }




}