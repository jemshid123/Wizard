package com.jtech.www.hide;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         final EditText password=(EditText)findViewById(R.id.PASSWORD);
        File file=new File(getFilesDir()+"/new1.txt");
        if(!file.exists())
        {
            startActivity(new Intent(this,signup.class));
            finish();
        }
               password.addTextChangedListener(new TextWatcher() {
                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                   }

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {

                   }

                   @Override
                   public void afterTextChanged(Editable s) {

                       //Toast.makeText(getBaseContext(),password.getText().toString(),Toast.LENGTH_SHORT).show();
                       String  con="content://com.log.www/students";
                       Uri uri=Uri.parse(con);
                       Cursor c=managedQuery(uri,null,null,null,"password");
                       c.moveToFirst();
                       String pass=c.getString(c.getColumnIndex("password"));
                       if(pass.equals(password.getText().toString()))
                       {
                      startActivity(new Intent(getBaseContext(),main.class));
                           finish();
                       }

                   }
               });
    }
}