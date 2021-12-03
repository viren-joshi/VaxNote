package com.example.vaxnote.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vaxnote.R;
import com.example.vaxnote.classes.DoseListAdapter;
import com.example.vaxnote.classes.LocalInfo;

import java.util.ArrayList;
import java.util.Objects;



public class VaccineRecord extends AppCompatActivity {
    Toolbar toolbar;
    TextView vaccineRecord,personName,vaccinationDate, doseNo;
    String name,vaccine,date,dose;
    ListView listView;
    int position;
    ArrayList<String> array = new ArrayList<String>();
    ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_record);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.statusbar_yellow));

        toolbar = findViewById(R.id.vaccineRecordToolbar);
        setSupportActionBar(toolbar);

        vaccineRecord = findViewById(R.id.vaccineRecord);
        personName = findViewById(R.id.namePerson);
        vaccinationDate = findViewById(R.id.dateVaccination);
        doseNo = findViewById(R.id.numberDose);



        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        arr = LocalInfo.getInstance(this).getUserPastVaccineList(LocalInfo.getInstance(this).userPastVaccineList, true);
        name = arr.get(position).get(1);
        vaccine = arr.get(position).get(2);
        dose = arr.get(position).get(3);

        personName.setText(name);
        vaccineRecord.setText(vaccine);
        listView = findViewById(R.id.DoseListView);
        if(Objects.equals(dose, "0")){
            array.add("No doses taken !!");
            ArrayAdapter adapter = new ArrayAdapter(VaccineRecord.this, android.R.layout.simple_list_item_1,array);
            listView.setAdapter(adapter);
        }
        else {
            for (int i = 4, j = 0; i < arr.get(position).size(); i++, j++) {
                String date = arr.get(position).get(i);
                if (!Objects.equals(date, "null")) {
                    array.add(date);
                }
            }
            DoseListAdapter doseListAdapter = new DoseListAdapter(VaccineRecord.this,R.layout.dose_list_layout,array);
            listView.setAdapter(doseListAdapter);
        }






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vax_record_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.removeRecord){
            customDialog("Confirm Record Deletion","Do you want to delete this Vaccine Record ?");
        }
        return true;
    }
    public void customDialog(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_issue);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(
                "Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(VaccineRecord.this, "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(VaccineRecord.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        builder.show();
    }
}
