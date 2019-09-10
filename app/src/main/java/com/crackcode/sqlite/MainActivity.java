package com.crackcode.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks, editId;
    Button buttonAddData, buttonViewData, buttonUpdate, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName=findViewById(R.id.editText1);
        editSurname=findViewById(R.id.editText2);
        editMarks=findViewById(R.id.editText3);
        editId=findViewById(R.id.editText4);
        buttonAddData=findViewById(R.id.button1);
        buttonViewData=findViewById(R.id.button2);
        buttonUpdate=findViewById(R.id.button3);
        buttonDelete=findViewById(R.id.button4);

        addData();
        viewAll();
        updateData();
        DeleteData();

    }

    public void addData(){
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isInserted = myDb.insertData(editName.getText().toString(),
                        editSurname.getText().toString(),
                        editMarks.getText().toString());
               if (isInserted==true){
                   Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
               } else {
                   Toast.makeText(MainActivity.this, "Data is not inserted", Toast.LENGTH_LONG).show();
               }
            }
        });
    }

    public void viewAll(){
        buttonViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount()==0){
                    //message
                    showMessage("Error ","No data found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID : "+res.getString(0)+"\n");
                    buffer.append("Name : "+res.getString(1)+"\n");
                    buffer.append("Surname : "+res.getString(2)+"\n");
                    buffer.append("Marks : "+res.getString(3)+"\n\n");
                }
                // show all data
                showMessage("Data",buffer.toString());
            }
        });
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(editId.getText().toString(),
                        editName.getText().toString(),editSurname.getText().toString(),
                        editMarks.getText().toString());
                if (isUpdate==true){
                    Toast.makeText(MainActivity.this,"Data updated", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data is not updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void DeleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer DeleteRow = myDb.deleteData(editId.getText().toString());
                if (DeleteRow > 0){
                    Toast.makeText(MainActivity.this,"Data deleted", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data is not deleted", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
