package com.example.vaxnote.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vaxnote.R;
import com.example.vaxnote.activities.FutureVaccineSchedule;
import com.example.vaxnote.activities.PastRecordActivity;
import com.example.vaxnote.activities.UserProfileDrawer;
import com.example.vaxnote.classes.LocalInfo;

import java.util.ArrayList;


public class UserProfileFragment extends Fragment{
    LinearLayout userNotification;
    Button pastVaccineRecords, futureVaccineSchedule;
    int position = -1;
    ArrayList<String> Notification;
    TextView name,email,dob,address,namePerson,vaccineName,dose;
    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
        }
        return view;
    }

    @Override
    public void onStart() {
        name = getView().findViewById(R.id.userName);
        email = getView().findViewById(R.id.userEmail);
        dob = getView().findViewById(R.id.userDOB);
        address = getView().findViewById(R.id.userAddress);
        userNotification = getView().findViewById(R.id.notificationUser);
        getLayoutInflater().inflate(R.layout.notifiation_layout, userNotification);
        pastVaccineRecords = getView().findViewById(R.id.vaccineRecordPast);
        futureVaccineSchedule = getView().findViewById(R.id.futureSchedule);
        namePerson = getView().findViewById(R.id.personName);
        vaccineName = getView().findViewById(R.id.vaccineName);

        if(position != -1) {
            String userName = LocalInfo.getInstance(getContext()).pList[position][1];
            String userEmail = LocalInfo.getInstance(getContext()).pList[position][0];
            String userDOB = LocalInfo.getInstance(getContext()).pList[position][2];
            String userAddress = LocalInfo.getInstance(getContext()).pList[position][3];



            name.setText(userName);
            email.setText(userEmail);
            dob.setText(userDOB);
            address.setText(userAddress);





            dose = getView().findViewById(R.id.vaccineDose);
            Notification = LocalInfo.getInstance(getContext()).getPersonNotifs(position).get(0);
            String name = "Name : " + Notification.get(0);
            String vaccine = "Vaccine : " + Notification.get(1);
            String doseNo = "Dose No : " + Notification.get(2);
            namePerson.setText(name);
            vaccineName.setText(vaccine);
            dose.setText(doseNo);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Error !");
            builder.setMessage("there was an error please try again later!");
            builder.show();
        }


        userNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewRecordFragment fragment = new NewRecordFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", Notification.get(0));
                bundle.putString("vaccine", Notification.get(1));
                bundle.putString("doses", Notification.get(2));
                fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment).addToBackStack(null).commit();
            }
        });
        pastVaccineRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),PastRecordActivity.class);
                intent.putExtra("name",LocalInfo.getInstance(getContext()).pList[position][1]);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        futureVaccineSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),FutureVaccineSchedule.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        super.onStart();
    }

}