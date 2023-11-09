package com.example.mysqlitepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button insertBtn,getBtn,showAllBtn;
    EditText name,email,getPerson;
    TextView result,getAll;
    boolean showAllClicked=false,searchClicked=false;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBtn=findViewById(R.id.searchbtn);
        showAllBtn=findViewById(R.id.showall);
        getAll=findViewById(R.id.showalldata);
        getPerson=findViewById(R.id.search);
        insertBtn=findViewById(R.id.add);
        name=findViewById(R.id.name);
        result=findViewById(R.id.searchresult);
        email=findViewById(R.id.email);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr=name.getText().toString();
                String emailStr=email.getText().toString();
                DBHelper db=new DBHelper(MainActivity.this);
                boolean insert = db.addUser(nameStr, emailStr);
                if(insert){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    email.setText("");
                }
                else{
                    Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_SHORT).show();
                }

            }
        });
        getBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(searchClicked){
                    searchClicked=false;
                    result.setText("");
                    getPerson.setText("");
                    getBtn.setText("Search");
                    return;
                }
                String nameStr=getPerson.getText().toString();
                DBHelper db=new DBHelper(MainActivity.this);
                Cursor cursor=db.getRecord(nameStr);
                if(cursor.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
                else{
                    while(cursor.moveToNext()){
                        searchClicked=true;
                        getBtn.setText("Hide");
                        result.setText("Name: "+cursor.getString(1)+"\nEmail: "+cursor.getString(2));
                        Toast.makeText(MainActivity.this, "Name: "+cursor.getString(1)+"\nEmail: "+cursor.getString(2), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        showAllBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(showAllClicked){
                    showAllClicked=false;
                    showAllBtn.setText("Show All");
                    getAll.setText("");
                    return;
                }
                DBHelper db=new DBHelper(MainActivity.this);
                Cursor cursor=db.getAllData();
                if(cursor.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Record Found", Toast.LENGTH_SHORT).show();
                }
                else{
                    showAllClicked=true;
                    showAllBtn.setText("Hide All");
                    StringBuffer buffer=new StringBuffer();
                    while(cursor.moveToNext()){
                        buffer.append("Name: "+cursor.getString(1)+"\nEmail: "+cursor.getString(2)+"\n\n");
                    }
                    getAll.setText(buffer.toString());
                }
            }
        });
    }

}