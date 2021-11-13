package com.example.vaxnote.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vaxnote.R;

public class VaccineInfo extends AppCompatActivity {
    ListView listview;
    String arr[] = {"Anthrax", "Chickenpox (Varicella)", "Covid-19", "Diphtheria, tetanus, and " +
            "whooping cough (pertussis) (DTaP)", "Hepatitis A", "Hepatitis B", "Haemophilus influenzae TypeB (Hib)", "HPV vaccine",
            "Influenza (flu)", "Japanese Encephalitis", "Measles, mumps, rubella (MMR)", "Meningococcal vaccine", "Polio (IPV)", "Pneumococcal (PCV)",
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccineinfo);
        listview= findViewById(R.id.listview);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arr);
        listview.setAdapter(ad);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = urls[position];
                Uri uri = Uri.parse(url);
                Intent intent = new Intent( Intent.ACTION_VIEW, uri );
                startActivity(intent);
            }
        });
    }
}