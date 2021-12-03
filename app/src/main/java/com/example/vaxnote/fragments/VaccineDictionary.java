package com.example.vaxnote.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vaxnote.R;


public class VaccineDictionary extends Fragment {
    ListView listview;
    ArrayAdapter arrayAdapter;
    String arr[] = {"Anthrax", "Chickenpox (Varicella)", "Covid-19", "Diphtheria, tetanus, and " +
            "whooping cough (Pertussis) (DTaP)", "Hepatitis A", "Hepatitis B", "Haemophilus Influenzae TypeB (Hib)", "HPV vaccine",
            "Influenza (Flu)", "Japanese Encephalitis", "Measles, Mumps, Rubella (MMR)", "Meningococcal vaccine", "Polio (IPV)", "Pneumococcal (PCV)",
            "Rabies", "Rotavirus (RV)", "Smallpox", "Tuberculosis (BCG)", "Typhoid Fever", "Yellow Fever", "Zoster vaccine"};

    String[] urls = {"https://www.cdc.gov/vaccines/vpd/anthrax/public/index.html",
            "https://www.cdc.gov/vaccines/vpd/varicella/public/index.html",
            "https://www.cdc.gov/coronavirus/2019-ncov/vaccines/keythingstoknow.html",
            "https://www.cdc.gov/vaccines/vpd/dtap-tdap-td/public/index.html",
            "https://www.cdc.gov/hepatitis/hav/afaq.htm?CDC_AA_refVal=https%3A%2F%2Fwww.cdc.gov%2Fvaccines%2Fvpd%2Fhepa%2Fpublic%2Findex.html#prevention",
            "https://www.cdc.gov/hepatitis/hbv/bfaq.htm?CDC_AA_refVal=https%3A%2F%2Fwww.cdc.gov%2Fvaccines%2Fvpd%2Fhepb%2Fpublic%2Findex.html#prevention",
            "https://www.cdc.gov/vaccines/vpd/hib/public/index.html",
            "https://www.cdc.gov/hpv/parents/index.html",
            "https://www.cdc.gov/vaccines/vpd/flu/public/index.html",
            "https://www.cdc.gov/japaneseencephalitis/vaccine/index.html",
            "https://www.cdc.gov/vaccines/vpd/mmr/public/index.html",
            "https://www.cdc.gov/vaccines/vpd/mening/public/index.html",
            "https://www.cdc.gov/vaccines/vpd/polio/public/index.html",
            "https://www.cdc.gov/vaccines/vpd/pneumo/public/index.html",
            "https://www.cdc.gov/rabies/medical_care/vaccine.html",
            "https://www.cdc.gov/vaccines/vpd/rotavirus/public/index.html",
            "https://www.cdc.gov/vaccines/vpd/smallpox/index.html",
            "https://www.cdc.gov/tb/topic/basics/vaccines.htm",
            "https://www.cdc.gov/typhoid-fever/typhoid-vaccination.html",
            "https://www.cdc.gov/vaccines/vpd/yf/index.html",
            "https://www.cdc.gov/vaccines/vpd/shingles/index.html"};


    public VaccineDictionary() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vaccine_dictionary, container, false);
    }

    @Override
    public void onStart() {
        listview = getView().findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arr);
        listview.setAdapter(arrayAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = urls[position];
                Uri uri = Uri.parse(url);
                Intent intent = new Intent( Intent.ACTION_VIEW, uri );
                startActivity(intent);
            }
        });
        super.onStart();
    }
}