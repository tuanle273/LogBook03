package com.example.logbook03;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Button next, previous;
    private int index = 0;
    Button add_button;
    private EditText add_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Database helper = new Database(this);
        imageView = findViewById(R.id.imageView);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);

//        Database db = new Database(MainActivity.this);
//        db.addData("https://kynguyenlamdep.com/wp-content/uploads/2020/01/hinh-anh-dep-hoa-bo-cong-anh-700x466.jpg");
//        db.addData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDrxb_5QrMoX1lJSDSKtauxvgjnmjbtFp_P-s4kLkD&s");

        loadImage();
        add_link = findViewById(R.id.add_link);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (add_link.getText().toString().length() == 0) {
                    add_link.setError("Enter Link");

                } else {

                    Database myDB = new Database(MainActivity.this);
                    myDB.addData(add_link.getText().toString().trim());

                    Glide.with(MainActivity.this)
                            .load(loadLastImg())
                            .centerCrop()
                            .into(imageView);
                }

            }

        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(MainActivity.this);
                Cursor cursor = db.readAllData();
                if(index == 0){
                    cursor.moveToLast();
                    index = cursor.getPosition();
                } else {
                    index--;
                    cursor.moveToPosition(index);

                }
                String url = cursor.getString(1);
                Glide.with(MainActivity.this)
                        .load(url)
                        .centerCrop()
                        .into(imageView);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database db = new Database(MainActivity.this);
                Cursor cursor = db.readAllData();
                cursor.moveToLast();
                int last_index = cursor.getPosition();
                if(index == last_index){
                    cursor.moveToFirst();
                    index = cursor.getPosition();
                } else {
                    index++;
                    cursor.moveToPosition(index);

                }
                String url = cursor.getString(1);
                Glide.with(MainActivity.this)
                        .load(url)
                        .centerCrop()
                        .into(imageView);
            }
        });
    }
    
    String loadLastImg(){
        Database db = new Database(MainActivity.this);
        Cursor cursor = db.readAllData();
        cursor.moveToLast();
        String url = cursor.getString(1);
        index = cursor.getPosition();
        return url;
    }

    private void loadImage(){
        Glide.with(MainActivity.this)
                .load(loadLastImg())
                .centerCrop()
                .into(imageView);
    }

}