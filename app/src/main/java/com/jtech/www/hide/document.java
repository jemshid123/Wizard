package com.jtech.www.hide;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;

import java.util.regex.Pattern;


public class document extends Fragment {
     public document() {
        // Required empty public constructor
    }

    GridView grid;
    String[] names;
    Bitmap[]  bitmaps;
    PopupMenu menu;
    menuaction action;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_document, container, false);
        Log.d("photo ********","photo loader");
        try {
            grid = (GridView) v.findViewById(R.id.documentgrid);
            photoloader task = new photoloader();
            action=new menuaction();
            task.execute(grid);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    menu=new PopupMenu(getContext(),view);
                    action.menu(menu,getContext(),names[position]);
                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    private  class photoloader extends AsyncTask<GridView,Void,Void>
    {
        gridadaptor grid;
        GridView gv;
        String filename[],temp[];


        protected Void doInBackground(GridView... params) {
            gv=params[0];
            names=filestore.getmedia(getActivity(),400);
            bitmaps=filestore.storedthumb(names);
            filename=new String[names.length];
            int i=0;
            for(String d:names)
            {
                Log.d("photo ********",d);
                temp=d.split(Pattern.quote("/"));
                filename[i]=temp[temp.length-1];
                i++;
            }

            return  null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            grid=new gridadaptor(getContext(),bitmaps,filename);
            gv.setAdapter(grid);
        }


    }


}
