package com.example.squalitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.squalitedatabase.data.DatabaseHelper;
import com.example.squalitedatabase.model.PersonalInfo;

import java.util.ArrayList;
import java.util.List;

public class ListViewDB extends AppCompatActivity {
ListView viewDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_db);
        viewDB =findViewById(R.id.listView);
        DatabaseHelper helper=new DatabaseHelper(ListViewDB.this);
        List<PersonalInfo> personal= helper.showQuery();
        ArrayList<String> arrayList=new ArrayList<>();
        for(PersonalInfo p1:personal){
            arrayList.add(p1.getName()+" ("+p1.getPhone_no()+" )");
            Log.d("TAG","name "+p1.getName()+" phone "+p1.getPhone_no()+" "+personal.size()+" id"+ p1.getId());
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        viewDB.setAdapter(adapter);

    }
}
