package com.jtech.www.hide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kyleduo.switchbutton.SwitchButton;

import java.io.File;

public class settings extends AppCompatActivity {
TextView change;
    SwitchButton tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        change=(TextView)findViewById(R.id.change);
        tb=(SwitchButton) findViewById(R.id.toggleButton);
        final File file=new File(getFilesDir()+"/check.txt");
        if(file.exists())
        {
            tb.setChecked(true);
        }
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(settings.this,changepassword.class));
                finish();
            }
        });
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try {
                    if (isChecked) {
                        file.createNewFile();
                        Toast.makeText(getBaseContext(), "true", Toast.LENGTH_LONG).show();
                    } else {
                        file.delete();
                        Toast.makeText(getBaseContext(), "false", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e)
                {
                    Toast.makeText(getBaseContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
