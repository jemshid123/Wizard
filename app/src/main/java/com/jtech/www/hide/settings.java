package com.jtech.www.hide;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kyleduo.switchbutton.SwitchButton;

import java.io.File;
import java.util.jar.Manifest;

public class settings extends AppCompatActivity {
TextView change;
    SwitchButton tb;
    int per;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

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

                        AlertDialog.Builder dialog=new AlertDialog.Builder(settings.this);
                        dialog.setTitle("Alert");
                        dialog.setCancelable(false);
                        dialog.setMessage("Dial youre password on dialer to launch Wizard");
                        dialog.setIcon(R.mipmap.lock);
                        AlertDialog alert;
                          dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {

                               try {
                                   file.createNewFile();
                                   PackageManager p = getPackageManager();
                                   ComponentName componentName = new ComponentName(settings.this, com.jtech.www.hide.MainActivity.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
                                   p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                                   //  Toast.makeText(getBaseContext(), "true", Toast.LENGTH_LONG).show();
                               }catch (Exception e)
                               {

                               }

                              }
                          });

                        if( ContextCompat.checkSelfPermission(settings.this, android.Manifest.permission.PROCESS_OUTGOING_CALLS)==PackageManager.PERMISSION_GRANTED) {
                            alert = dialog.create();
                            alert.show();
                        }
                        else
                        {
                            ActivityCompat.requestPermissions(settings.this,new String[]{android.Manifest.permission.PROCESS_OUTGOING_CALLS},2);
                            if( ContextCompat.checkSelfPermission(settings.this, android.Manifest.permission.PROCESS_OUTGOING_CALLS)==PackageManager.PERMISSION_GRANTED) {
                                alert = dialog.create();
                                alert.show();
                            }
                            else
                            {
                              dialog.setMessage("This permission is needed")  ;
                                alert = dialog.create();
                                alert.show();
                            }
                        }
                    } else {
                        file.delete();
                        PackageManager p = getPackageManager();
                        ComponentName componentName = new ComponentName(settings.this,com.jtech.www.hide.MainActivity.class);
                        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                        //Toast.makeText(getBaseContext(), "false", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e)
                {
                     Toast.makeText(getBaseContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
