package com.jtech.www.hide;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        final EditText pass1=(EditText)findViewById(R.id.pass1);
        final EditText pass2=(EditText)findViewById(R.id.pass2);
        Button  set=(Button)findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass1.getText().toString().length() <4)
                {
                    Toast.makeText(getBaseContext(),"password must be at least 4 digit",Toast.LENGTH_LONG).show();
                }
                else if( ! pass1.getText().toString().equals(pass2.getText().toString()))
                {
                    Toast.makeText(getBaseContext(),"passwords don't match ",Toast.LENGTH_LONG).show();
                }
                else{
                    ContentValues cv=new ContentValues();
                    cv.put(log.PASSWORD,pass1.getText().toString());
                    getContentResolver().insert(log.CONTENT_URI,cv);
                    String  con="content://com.log.www/students";
                    Uri uri=Uri.parse(con);
                    Cursor c=managedQuery(uri,null,null,null,"password");
                    c.moveToFirst();
                    Toast.makeText(getBaseContext(),"id:"+c.getString(c.getColumnIndex(log._ID))+" pass:"+c.getString(c.getColumnIndex(log.PASSWORD)),Toast.LENGTH_LONG).show();
                    File file=new File(getFilesDir()+"/new1.txt");
                    try {
                        file.createNewFile();
                        startActivity(new Intent(getBaseContext(),MainActivity.class));
                        finish();
                    }catch (Exception e)
                    {
                        Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                   // Toast.makeText(getBaseContext(),"passwords set ",Toast.LENGTH_LONG).show();

                }

            }
        });

    }
}
