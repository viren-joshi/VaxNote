package com.example.vaxnote.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.vaxnote.Constants;
import com.example.vaxnote.R;
import com.example.vaxnote.activities.MainMenuActivity;
import com.example.vaxnote.activities.PastRecordActivity;
import com.example.vaxnote.classes.LoadingDialog;
import com.example.vaxnote.classes.LocalInfo;
import com.example.vaxnote.classes.RequestHandler;
import com.example.vaxnote.classes.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class NewRecordFragment extends Fragment {
    Button saveRecord;
    TextView vaccinationDate,newVaccineName,personName,doseNo;
    String dateVaccination;
    String name,vaccine,doses,setDate;
    DatePickerDialog.OnDateSetListener dateSetListener;
    LoadingDialog loadingDialog;
    String result;

    public NewRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_record, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            vaccine = bundle.getString("vaccine");
            doses = bundle.getString("doses");
        }
        else{
            Log.d("BundleError","BundleIsNULL");
        }
        return view;
    }

    @Override
    public void onStart() {
        //Initializing All Views
        saveRecord = getView().findViewById(R.id.saveButton);
        vaccinationDate = getView().findViewById(R.id.dateVaccination);
        newVaccineName = getView().findViewById(R.id.vaccineRecord);
        personName = getView().findViewById(R.id.personName);
        doseNo = getView().findViewById(R.id.numberDose);

        //Setting the text of all Views
        //TODO: set the text of all views here (via Intent Extras or diretly the variables)
        String userName = "Name : " + name,
                vaccineName = "Vaccine : " + vaccine,
                dose = "Dose No : " + doses;
        personName.setText(userName);
        newVaccineName.setText(vaccineName);
        doseNo.setText(dose);

        //OnClickListeners
        vaccinationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), android.R.style.Widget_DeviceDefault,
                        dateSetListener,
                        year,month,day);
//                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.));
                datePickerDialog.show();
            }
        });
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                int m = month + 1;
                dateVaccination = year + "/" + m +"/" + day;
                Log.d("Vaccination Date",dateVaccination);
                setDate = year + "-" + m + "-" + day;
                Log.d("Vaccination Date",setDate);
                vaccinationDate.setText(dateVaccination);
            }
        };
        saveRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Connect to DB to add the record here
                loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.startLoadingDialog();
                addNewVaccineRecord(SharedPrefManager.getInstance(getContext()).getEmail(),name,vaccine,doses,setDate);
                //Make the app to refresh with database here to get all correct values
//                Toast.makeText(getContext(), "Record Saved", Toast.LENGTH_SHORT).show();

            }
        });
        super.onStart();
    }

    public void addNewVaccineRecord(String email,String name, String vaccine,String dose,String date){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_UPDATEVACCINEVACCINE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("AddNewVaccine",response);
                            JSONObject jsonObject = new JSONObject(response);
                            result = jsonObject.getString("message");
                            if(Objects.equals(result, "Successful")){
                                loadingDialog.dissmissLoadingDialog();
                                Toast.makeText(getActivity(), "Vaccine Record Saved !", Toast.LENGTH_SHORT).show();
                                LocalInfo.getInstance(getActivity()).syncVariable();
                                Intent intent = new Intent(getContext(), MainMenuActivity.class);
                                startActivity(intent);
                                requireActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(), "Error! Record Not Saved", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("AddNewVaccine",error.getMessage());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("name",name);
                params.put("vaccine",vaccine);
                params.put("dose",dose);
                params.put("date",date);
                return params;
            }
        };
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

}