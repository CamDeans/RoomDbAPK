package com.example.roomdemodb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEntryActivity extends AppCompatActivity {
    private static final String TAG = "AddEntryActivity";
    // initialize parameters
    EditText edDataEntry;
    Button btn;

    // create null data object to add runnable insert
    Data data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        Log.d(TAG, "onCreate: Started");
        // create object
        edDataEntry = findViewById(R.id.editTextUsername);
        btn = findViewById(R.id.buttonAddEntry);
        // set event to handle user input
        btn.setOnClickListener(view -> {
            // get data entry from the user
            // cast to string method () required
            String dataEntry = edDataEntry.getText().toString();
            // validation step for user input text length of 0
            if (dataEntry.length() == 0) {
                // temporary call to display btn press functionality
                // Toast takes (3) parameters values passed in
                // outcome: application will output a popup text bubble IE> alert
                Toast.makeText(getApplicationContext(), "Please fill all details!", Toast.LENGTH_SHORT).show();
            } else {
                dbData(dataEntry);
                ViewEntryActivity viewEntryActivity = new ViewEntryActivity();
                viewEntryActivity.getDBData(dataEntry);
                Toast.makeText(getApplicationContext(), "Data Entry Successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddEntryActivity.this, ViewEntryActivity.class));
            }
        });
    }

    public void dbData(String data) {
        // create DB object
        DataDB dataDB = DataDB.getInstance(this);

        // clean data text entry
        data = data.trim();

        // set isData to new Data object
        Data isData = new Data(data);

        // insert isData to DB
        dataDB.dataDAO().insert(isData);

        // clear the data entry text box
        edDataEntry.setText("");
    }
}