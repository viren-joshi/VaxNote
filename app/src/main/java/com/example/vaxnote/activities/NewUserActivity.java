package com.example.vaxnote.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.vaxnote.Constants;
import com.example.vaxnote.R;
import com.example.vaxnote.classes.LoadingDialog;
import com.example.vaxnote.classes.LocalInfo;
import com.example.vaxnote.classes.RequestHandler;
import com.example.vaxnote.classes.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class NewUserActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView dob;
    Button continueButton;
    EditText userName, userAddress;
    String name, userDOB, address,result,setDate;
    DatePickerDialog.OnDateSetListener dateSetListener;
    LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.statusbar_yellow));

        toolbar = findViewById(R.id.newUserToolbar);
        setSupportActionBar(toolbar);

        userName = findViewById(R.id.nameofPerson);
        dob = findViewById(R.id.dobUser);
        userAddress = findViewById(R.id.addressUser);


        continueButton = findViewById(R.id.continueButton);

        dob.setOnClickListener(view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    NewUserActivity.this, android.R.style.Widget_DeviceDefault,
                    dateSetListener,
                    year,month,day);
            datePickerDialog.show();
        });

        dateSetListener = (datePicker, year, month, day) -> {
            month++;
            userDOB = year + "/" + month +"/" + day;
            Log.d("Date",userDOB);
            dob.setText(userDOB);
            setDate = year + "-" + month + "-" + day;
        };

        continueButton.setOnClickListener(view -> {
            name = userName.getText().toString();
            address = userAddress.getText().toString();
            loadingDialog = new LoadingDialog(NewUserActivity.this);
            loadingDialog.startLoadingDialog();
//                Toast.makeText(NewUserActivity.this, name + " " + address, Toast.LENGTH_SHORT).show();
            addNewPerson(SharedPrefManager.getInstance(NewUserActivity.this).getEmail(),name,setDate,address);
        });
    }
    public void addNewPerson(String email,String userName, String dob,String address){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_NEWPERSONPROFILE,
                response -> {
                    try {
                        Log.d("addNewPerson",response);
                        JSONObject jsonObject = new JSONObject(response);
                        result = jsonObject.getString("message");
                        if(Objects.equals(result, "Successful")){
                            loadingDialog.dissmissLoadingDialog();
                            LocalInfo.getInstance(NewUserActivity.this).syncVariable();
                            Toast.makeText(NewUserActivity.this, "New Person Added!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewUserActivity.this,MainMenuActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(NewUserActivity.this, "Error! New Person not Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(NewUserActivity.this,MainMenuActivity.class);
                            startActivity(intent);
                        }
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("userEmail",email);
                params.put("userName",userName);
                params.put("userDOB",dob);
                params.put("userAddress",address);
                return params;
            }
        };
        RequestHandler.getInstance(NewUserActivity.this).addToRequestQueue(stringRequest);
    }
}