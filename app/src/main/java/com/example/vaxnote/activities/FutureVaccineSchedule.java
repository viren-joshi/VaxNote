package com.example.vaxnote.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vaxnote.R;
import com.example.vaxnote.classes.LocalInfo;
import com.example.vaxnote.classes.NotificationAdapter;
import com.example.vaxnote.fragments.NewRecordFragment;

import java.util.ArrayList;

public class FutureVaccineSchedule extends AppCompatActivity {
    ListView listView;
    Toolbar toolbar;
    ArrayList<ArrayList<String>>arr;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_schedule);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.statusbar_yellow));

        toolbar = findViewById(R.id.futureVaccineToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.futureVaccineListView);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);

        LocalInfo info = new LocalInfo(this);
        arr = info.getPersonNotifs(position);

        if(arr == null){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            arr.add(new ArrayList<String>());
            arr.get(0).add("Error");
            arr.get(0).add("Error");
        }
        NotificationAdapter notificationAdapter = new NotificationAdapter(this,R.layout.notifiation_layout,arr);
        listView.setAdapter(notificationAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int viewPosition, long l) {
                ArrayList<String> Notification = new ArrayList<String>();
                Notification = info.getPersonNotifs(position).get(viewPosition);
                Intent i = new Intent(FutureVaccineSchedule.this,UserProfileDrawer.class);
                i.putExtra("label","NewRecord");
                i.putExtra("name", Notification.get(0));
                i.putExtra("vaccine", Notification.get(1));
                i.putExtra("doses", Notification.get(2));
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FutureVaccineSchedule.this,UserProfileDrawer.class);
        intent.putExtra("personPosition",position);
        startActivity(intent);
    }
}