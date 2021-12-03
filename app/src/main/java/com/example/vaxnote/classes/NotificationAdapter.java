package com.example.vaxnote.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.vaxnote.R;

import java.util.ArrayList;

public class NotificationAdapter extends ArrayAdapter<ArrayList<String>> {
    private ArrayList<ArrayList<String>> arr;

    public NotificationAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ArrayList<String>> arr) {
        super(context, resource, arr);
        this.arr = arr;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.notifiation_layout,parent,false);
        String name = "Name : " + arr.get(position).get(0);
        String vaccine = "Vaccine : " + arr.get(position).get(1);
        String dose = "Dose No : " + arr.get(position).get(2);
        ((TextView)convertView.findViewById(R.id.personName)).setText(name);
        ((TextView)convertView.findViewById(R.id.vaccineName)).setText(vaccine);
        ((TextView)convertView.findViewById(R.id.vaccineDose)).setText(dose);
        return convertView;
    }
}
