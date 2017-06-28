package com.jtech.www.hide;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class changepassword extends AppCompatActivity {
Button set;
    EditText currpassword,pass1,pass2;
    AlertDialog.Builder dialog;
    AlertDialog alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        set=(Button)findViewById(R.id.set);
        currpassword=(EditText)findViewById(R.id.currpassword);
        pass1=(EditText)findViewById(R.id.pass1);
        pass2=(EditText)findViewById(R.id.pass2);
        String  con="content://com.log.www/students";
        final Uri uri=Uri.parse(con);
        Cursor c=managedQuery(uri,null,null,null,"password");
        c.moveToFirst();
       final String pass=c.getString(c.getColumnIndex("password"));
        Toast.makeText(getBaseContext(),c.getString(c.getColumnIndex(log._ID)),Toast.LENGTH_LONG).show();
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        if(pass.equals(currpassword.getText().toString()))
        {


            if(pass1.getText().toString().length() <4)
            {
                dialog=new AlertDialog.Builder(changepassword.this);
                dialog.setCancelable(false);
                dialog.setTitle("Incorrect password");
                dialog.setMessage("The password must be atleast 4 digits");
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert=dialog.create();
                alert.show();
            }
            else if( ! pass1.getText().toString().equals(pass2.getText().toString()))
            {
                dialog=new AlertDialog.Builder(changepassword.this);
                dialog.setCancelable(false);
                dialog.setTitle("Incorrect password");
                dialog.setMessage("The password enterd dont match");
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert=dialog.create();
                alert.show();
            }
            else{
                ContentValues cv=new ContentValues();
                cv.put(log.PASSWORD,pass1.getText().toString());
                getContentResolver().update(uri,cv,log._ID+"=1",null);
                startActivity(new Intent(getBaseContext(),main.class));
                finish();
            }


        }else
        {
            dialog=new AlertDialog.Builder(changepassword.this);
            dialog.setCancelable(false);
            dialog.setTitle("Incorrect password");
            dialog.setMessage("The password enterd is incorrect");
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert=dialog.create();
            alert.show();
        }

    }

    });
}}
