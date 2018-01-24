package com.example.user.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    JSONArray json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        try {
            prepareListData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    private String getJSONString(Context context)
    {
        String str = "";
        try
        {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open("sample_json");
            InputStreamReader isr = new InputStreamReader(in);
            char [] inputBuffer = new char[100];

            int charRead;
            while((charRead = isr.read(inputBuffer))>0)
            {
                String readString = String.copyValueOf(inputBuffer,0,charRead);
                str += readString;
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        return str;
    }
    /*
     * Preparing the list data
     */
    private void prepareListData() throws JSONException {
        try {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            json = new JSONArray(getJSONString(getApplicationContext()));
          //  System.out.println("json:::" + json.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json.length() > 0) {
            for (int i = 0; i < json.length(); i++)
                try {
                    JSONObject jo = json.getJSONObject(i);

                    System.out.println(jo);
                    if (jo.has("Name") && jo.get("Name") != null) {
                        String strHeader = (String) jo.get("Name");
                        // Adding child data
                        listDataHeader.add(strHeader);
                        //System.out.println(jo.get("Childrens"));
                        if (jo.has("Childrens") && jo.get("Childrens") != null){
                            JSONArray childrenArray = jo.getJSONArray("Childrens");
                            if (childrenArray.length() > 0) {
                                List<String> innerChild = new ArrayList<String>();
                               for (int j=0; j< childrenArray.length(); j++){
                                   JSONObject childObject = childrenArray.getJSONObject(j);
                                   if (childObject.has("Name") && childObject.get("Name") != null){
                                       String childName = (String) childObject.get("Name");
                                       innerChild.add(childName);
                                   }
                               }
                               listDataChild.put(listDataHeader.get(i),innerChild);
                                System.out.println("json:::" + innerChild);
                            }
                        }
                        else {
                            continue;
                        }
                    }
//                    listDataHeader.add("Now Showing");
//                    listDataHeader.add("Coming Soon..");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

}
