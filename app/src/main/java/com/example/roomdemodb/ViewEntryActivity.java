package com.example.roomdemodb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewEntryActivity extends AppCompatActivity {
    // create new  list to hold dataEntry
    List<Data> allDataEntries = new ArrayList<>();

    // create table row object
    LinearLayout textList;
    LinearLayout textListId;

    // deleteButton
    Button deleteBtn;
    Button backBtn;

    // create DB object
    DataDB dataDB = DataDB.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        // set tableRow
        textList = findViewById(R.id.listContainer);
        textListId = findViewById(R.id.listContainer);

        // set button
        deleteBtn = findViewById(R.id.deleteBtn);
        backBtn = findViewById(R.id.backBtn);

        // call showList method ()
        showList();

        // set event to handle delete user input stored in DB
        deleteBtn.setOnClickListener(view -> {
            // set data object to be deleted
            allDataEntries = dataDB.dataDAO().findAllData();
            // delete data entry from the DB
            for (Data item: allDataEntries) {
                dataDB.dataDAO().deleteById((long) item.id);
                Toast.makeText(getApplicationContext(), "Delete Entry Successful!", Toast.LENGTH_SHORT).show();
            }
        });

        // set event to handle move back to previous screen
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(ViewEntryActivity.this, AddEntryActivity.class));
        });
    }

    // getDBData method () and add to list
    public void getDBData(String data) {
        // set isData to new Data object
        Data isData = new Data(data);
        allDataEntries.add(isData);
    }

    public void showList() {
        // set data object
        allDataEntries = dataDB.dataDAO().findAllData();
        // iterate through data using a for loop
        for (Data item: allDataEntries) {
            TextView tvDataEntry = new TextView(this);
            tvDataEntry.setText(item.data);
            tvDataEntry.setTextSize(20);
            tvDataEntry.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textList.addView(tvDataEntry);
            tvDataEntry.setTag(item);
            tvDataEntry.setOnClickListener(this::deleteCurrent);
        }
    }

    public void deleteCurrent(View view) {
        Data data = (Data)view.getTag();
        dataDB.dataDAO().deleteById(data.id);
        clearList();
        showList();
    }

    public void clearList() {
        textList.removeAllViewsInLayout();
    }
}