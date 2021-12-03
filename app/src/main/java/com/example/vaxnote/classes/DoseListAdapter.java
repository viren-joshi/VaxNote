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

public class DoseListAdapter extends ArrayAdapter<String> {
    ArrayList<String> arr;
    public DoseListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> arr) {
        super(context, resource, arr);
        this.arr = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from((getContext())).inflate(R.layout.dose_list_layout,parent,false);
        String vaccineDate = "Vaccine Date : \t" + arr.get(position);
        ((TextView)convertView.findViewById(R.id.dateVaccination)).setText(vaccineDate);
        String doseNo = "Date of Vaccination : " + String.valueOf(position+1);
        ((TextView)convertView.findViewById(R.id.numberDose)).setText(doseNo);
        return convertView;
    }
}
