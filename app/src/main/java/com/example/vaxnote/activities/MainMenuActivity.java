package com.example.vaxnote.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

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
import com.example.vaxnote.classes.VaccineNotificationHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity {

    //Variables
    NotificationManagerCompat managerCompat;
    Toolbar toolbar;
    ListView allNotification, listPeople;
    String[] names;
    String[][] list;
    ArrayList<ArrayList<String>> notificationsList = new ArrayList<ArrayList<String>>();
    LoadingDialog loadingDialog;
    int i = 0;
    String result;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        getPeopleList(SharedPrefManager.getInstance(this).getEmail());
        loadingDialog = new LoadingDialog(MainMenuActivity.this);
        loadingDialog.startLoadingDialog();

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.statusbar_yellow));


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        listPeople = findViewById(R.id.peopleList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.left_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.newadd:
                //Connect to add new person
                startActivity(new Intent(this, NewUserActivity.class));
                break;
            case R.id.logout:
                //Logging Out and going to the Sign In Page
                LocalInfo.getInstance(this).syncVariable();
                SharedPrefManager.getInstance(this).userLogOut();
                break;
        }
        return true;
    }

    public void getUserNotification(String email, String name) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETUSERNOTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("HelloWorld2", response);
                            JSONObject jsonObject = new JSONObject(response);
                            result = jsonObject.getString("message");
                            Log.d("helloworld2", result);
                            if (result.equals("Successful")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("child");
                                ArrayList<ArrayList<String>> result_child = new ArrayList<ArrayList<String>>();
                                JSONObject array;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String s = jsonArray.getString(i);
                                    array = new JSONObject(s);
                                    result_child.add(new ArrayList<String>());

                                    result_child.get(i).add(array.getString("email"));
                                    result_child.get(i).add(array.getString("name"));
                                    result_child.get(i).add(array.getString("vaccine"));
                                    result_child.get(i).add(array.getString("child_notifs"));
                                    result_child.get(i).add(array.getString("doses"));
                                }
                                jsonArray = jsonObject.getJSONArray("teen_adult");
                                ArrayList<ArrayList<String>> result_teen_adult = new ArrayList<ArrayList<String>>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String s = jsonArray.getString(i);
                                    array = new JSONObject(s);
                                    result_teen_adult.add(new ArrayList<String>());

                                    result_teen_adult.get(i).add(array.getString("email"));
                                    result_teen_adult.get(i).add(array.getString("name"));
                                    result_teen_adult.get(i).add(array.getString("vaccine"));
                                    result_teen_adult.get(i).add(array.getString("teen_adult_notifs"));
                                    result_teen_adult.get(i).add(array.getString("doses"));
                                }
                                LocalInfo instance = LocalInfo.getInstance(MainMenuActivity.this);
                                instance.setChildNotifs(result_child);
                                instance.setTeenAdultNotifs(result_teen_adult);
                                i++;
                                if (i == LocalInfo.getInstance(MainMenuActivity.this).pList.length) {
                                    LocalInfo info = new LocalInfo(MainMenuActivity.this);
                                    list = LocalInfo.getInstance(MainMenuActivity.this).pList; //Getting the list of people
                                    for(int j=0;j<list.length;j++){
                                        if(info.getPersonNotifs(j).get(0) != null){
                                            notificationsList.add(instance.getPersonNotifs(j).get(0));
                                            if(info.getPersonNotifs(j).get(1) != null) {
                                                notificationsList.add(instance.getPersonNotifs(j).get(1));
                                            }
                                        }
                                    }
//                                    notificationsList = info.getNotifs(); //Getting their respective notification

                                    if (notificationsList == null) {
                                        Toast.makeText(MainMenuActivity.this, "An error occured", Toast.LENGTH_SHORT).show();
                                        notificationsList = new ArrayList<ArrayList<String>>();
                                        notificationsList.add(new ArrayList<String>());
                                        notificationsList.get(0).add("No Notifications to Show");
                                        notificationsList.get(0).add("N/A");
                                        notificationsList.get(0).add("N/A");
                                    }

                                    allNotification = findViewById(R.id.allNotificationListView);
                                    NotificationAdapter notificationAdapter = new NotificationAdapter(MainMenuActivity.this, R.layout.notifiation_layout, notificationsList);
                                    allNotification.setAdapter(notificationAdapter);


                                    if (list == null) {
                                        Toast.makeText(MainMenuActivity.this, "ArrayListNull", Toast.LENGTH_SHORT).show();
                                        names = new String[]{"Error!"};

                                    } else {
                                        names = new String[list.length];
                                        int i = 0;
                                        for (String[] a : list) {
                                            names[i] = a[1];
                                            i++;
                                        }
                                    }
                                    ArrayAdapter arrayAdapter = new ArrayAdapter(MainMenuActivity.this, android.R.layout.simple_list_item_1, names);
                                    listPeople.setAdapter(arrayAdapter);

                                    loadingDialog.dissmissLoadingDialog();


                                    listPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                            Intent intent = new Intent(MainMenuActivity.this, UserProfileDrawer.class);
                                            intent.putExtra("personPosition", position);
                                            startActivity(intent);
                                        }
                                    });

                                    allNotification.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                            ArrayList<String> notification = notificationsList.get(position);
                                            Intent intent = new Intent(MainMenuActivity.this,UserProfileDrawer.class);
                                            intent.putExtra("label","NewRecord");
                                            intent.putExtra("name",notification.get(0));
                                            intent.putExtra("vaccine",notification.get(1));
                                            intent.putExtra("doses",notification.get(2));
                                            startActivity(intent);
                                        }
                                    });
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("name", name);
                return params;
            }
        };
        RequestHandler.getInstance(MainMenuActivity.this).addToRequestQueue(stringRequest);
    }

    public void getPeopleList(String email) {
//        final LoadingDialog loadingDialog = new LoadingDialog((Activity) ctx);
//        loadingDialog.startLoadingDialog();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GETUSERPROFILES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            Log.d("HelloWorld", response);
                            jsonObject = new JSONObject(response);
                            result = jsonObject.getString("message");
                            Log.d("ResultOfFunction", result);
                            if (result.equals("Successful")) {
//                                loadingDialog.dissmissDialog();
                                Log.d("helloworld", "Called");
                                JSONArray jsonArray = jsonObject.getJSONArray("userList");
                                String[][] res = new String[jsonArray.length()][4];
                                JSONObject array;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    String s = jsonArray.getString(i);
                                    array = new JSONObject(s);
                                    res[i][0] = array.getString("email");
                                    res[i][1] = array.getString("name");
                                    res[i][2] = array.getString("dob");
                                    res[i][3] = array.getString("address");
                                }

                                LocalInfo.getInstance(MainMenuActivity.this).setList(res);
                                for (int i = 0; i < res.length; i++) {
                                    getUserNotification(res[i][0], res[i][1]);
                                }

                            } else {
                                //Do Something
                                Toast.makeText(MainMenuActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("getPeopleListError", error.getMessage());
                    }
                }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        RequestHandler.getInstance(MainMenuActivity.this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        for(int i=0,j=0;j<list.length;j++,i+=2) {
            String showNotifs = notificationsList.get(i).get(0) + " take your Dose : " + notificationsList.get(i).get(2) + " for " + notificationsList.get(i).get(1) + " Vaccine !";
            managerCompat = NotificationManagerCompat.from(MainMenuActivity.this);
            Intent notificationIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
            notificationIntent.setAction("CallNotifications");
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            Notification notification = new NotificationCompat.Builder(this, VaccineNotificationHandler.CHANNEL_1_ID)
                    .setContentTitle("VaxNote Reminder")
                    .setContentText(showNotifs)
                    .setSmallIcon(R.drawable.appicon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_REMINDER)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            managerCompat.notify(i+1, notification);
        }
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}