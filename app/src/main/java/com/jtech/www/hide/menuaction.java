package com.jtech.www.hide;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

/**
 * Created by others on 22-06-2017.
 */

public class menuaction {

    public void menu(PopupMenu menu, final Context context)
    {
       menu.getMenuInflater().inflate(R.menu.options,menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(context,item.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        menu.show();
    }
}
