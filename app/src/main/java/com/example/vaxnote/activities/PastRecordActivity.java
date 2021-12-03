package com.example.vaxnote.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vaxnote.Constants;
import com.example.vaxnote.R;
import com.example.vaxnote.classes.LoadingDialog;
import com.example.vaxnote.classes.LocalInfo;
import com.example.vaxnote.classes.NotificationAdapter;
import com.example.vaxnote.classes.RequestHandler;
import com.example.vaxnote.classes.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class PastRecordActivity extends AppCompatActivity {
    ListView listView;
    Toolbar toolbar;
    ArrayList<ArrayList<String>>arr = new ArrayList<ArrayList<String>>();
    LoadingDialog loadingDialog;
    String result,name,email;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_record);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        position = intent.getIntExtra("position",0);

        loadingDialog = new LoadingDialog(PastRecordActivity.this);
        loadingDialog.startLoadingDialog();



        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.statusbar_yellow));

        toolbar = findViewById(R.id.pastVaccineToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.pastVaccineListView);

        email = SharedPrefManager.getInstance(this).getEmail();

        getPastVaccineList(email,name);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
//            Toast.makeText(this, "BACKKK", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PastRecordActivity.this,UserProfileDrawer.class);
            intent.putExtra("personPosition",position);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PastRecordActivity.this,UserProfileDrawer.class);
        intent.putExtra("personPosition",position);
        startActivity(intent);
    }

    public void getPastVaccineList(String email,String name){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETPASTVACCINELIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("GOHOME!!",response);
                            JSONObject jsonObject = new JSONObject(response);
                            result = jsonObject.getString("message");
                            if(Objects.equals(result, "Successful")){
                                JSONArray jsonArray = jsonObject.getJSONArray("child");
                                ArrayList<ArrayList<ArrayList<String>>> res = new ArrayList<ArrayList<ArrayList<String>>>();
                                res.add(new ArrayList<ArrayList<String>>());
                                JSONObject array;
                                for(int i=0;i<jsonArray.length();i++){
                                    String s = jsonArray.getString(i);
                                     array = new JSONObject(s);
                                    res.get(0).add(new ArrayList<String>());
                                    res.get(0).get(i).add(array.getString("email"));
                                    Log.d("PastVaccineRecord",res.get(0).get(i).get(0));
                                    res.get(0).get(i).add(array.getString("name"));
                                    Log.d("PastVaccineRecord",res.get(0).get(i).get(1));
                                    res.get(0).get(i).add(array.getString("vaccine"));
                                    Log.d("PastVaccineRecord",res.get(0).get(i).get(2));
                                    res.get(0).get(i).add(array.getString("doses"));
                                    Log.d("PastVaccineRecord",res.get(0).get(i).get(3));
                                    for(int j=1;j<=5;j++){
                                        String d = "Dose" + j;
                                        array.getString(d);
                                        res.get(0).get(i).add(array.getString(d));
                                        Log.d("PastVaccineRecord",res.get(0).get(i).get(j+4-1));
                                    }
                                }

                                res.add(new ArrayList<ArrayList<String>>());
                                jsonArray = jsonObject.getJSONArray("teen_adult");
                                for(int i=0;i<jsonArray.length();i++){
                                    String s = jsonArray.getString(i);
                                    array = new JSONObject(s);
                                    res.get(1).add(new ArrayList<String>());
                                    res.get(1).get(i).add(array.getString("email"));
                                    Log.d("PastRecord",res.get(1).get(i).get(0));
                                    res.get(1).get(i).add(array.getString("name"));
                                    Log.d("PastRecord",res.get(1).get(i).get(1));
                                    res.get(1).get(i).add(array.getString("vaccine"));
                                    Log.d("PastRecord",res.get(1).get(i).get(2));
                                    res.get(1).get(i).add(array.getString("doses"));
                                    Log.d("PastRecord",res.get(1).get(i).get(3));
                                    for(int j=1;j<=2;j++){
                                        String d = "Dose" + j;
                                        array.getString(d);
                                        res.get(1).get(i).add(array.getString(d));
                                    }
                                }
                                arr = LocalInfo.getInstance(PastRecordActivity.this).getUserPastVaccineList(res,false);



                                loadingDialog.dissmissLoadingDialog();

                                if(arr == null){
                                    arr = new ArrayList<ArrayList<String>>();
                                    Toast.makeText(PastRecordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    arr.add(new ArrayList<String>());
                                    arr.get(0).add("Error");
                                    arr.get(0).add("Error");
                                    arr.get(0).add("Error");
                                }

                                NotificationAdapter notificationAdapter = new NotificationAdapter(PastRecordActivity.this,R.layout.notifiation_layout,arr);
                                listView.setAdapter(notificationAdapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                                        Toast.makeText(PastRecordActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(PastRecordActivity.this,VaccineRecord.class);
                                        intent.putExtra("position",position);
                                        startActivity(intent);
                                    }
                                });


                            }
                            else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("getPeopleListError",error.getMessage());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("name",name);
                return params;
            }
        };
        RequestHandler.getInstance(PastRecordActivity.this).addToRequestQueue(stringRequest);
    }
}