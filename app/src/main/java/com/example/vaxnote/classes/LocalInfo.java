package com.example.vaxnote.classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

public class LocalInfo {
    public static LocalInfo Instance;
    public static Context ctx;
    public String[][] pList;
    public ArrayList<ArrayList<ArrayList<String>>> childVaccineNotifs = new ArrayList<ArrayList<ArrayList<String>>>();
    public ArrayList<ArrayList<ArrayList<String>>> teenAdultVaccineNotifs = new ArrayList<ArrayList<ArrayList<String>>>();
    public ArrayList<ArrayList<ArrayList<String>>> userPastVaccineList = new ArrayList<ArrayList<ArrayList<String>>>();

    public LocalInfo(Context context){
        ctx = context;
    }

    public static synchronized LocalInfo getInstance(Context context){
        if(Instance == null){
            Instance = new LocalInfo(context);
        }
        return Instance;
    }

    public void setList(String[][]list){
        Log.d("LocalInfo","Setter");
        getInstance(ctx).pList = list;
    }


    public void setChildNotifs(ArrayList<ArrayList<String>> ChildNotifs){
        getInstance(ctx).childVaccineNotifs.add(ChildNotifs);
    }

    public void setTeenAdultNotifs(ArrayList<ArrayList<String>> teenAdultNotifs) {
        getInstance(ctx).teenAdultVaccineNotifs.add(teenAdultNotifs);
    }


    public ArrayList<ArrayList<String>> getUserPastVaccineList(ArrayList<ArrayList<ArrayList<String>>> userPastVaccines,boolean bool){
        getInstance(ctx).userPastVaccineList = userPastVaccines;
        ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
        if(bool){
            int k = 0, size = 0;
            for (int i = 0; i < 2; i++) {
                size += userPastVaccines.get(i).size();
                for (int l = 0; k < size; k++, l++) {
                    arr.add(new ArrayList<String>());
                    for (int m = 0; m < userPastVaccines.get(i).get(l).size(); m++) {
                        arr.get(k).add(userPastVaccines.get(i).get(l).get(m));
                    }
                }
            }
        }
        else {
            int k = 0, size = 0;
            for (int i = 0; i < 2; i++) {
                size += userPastVaccines.get(i).size();
                for (int l = 0; k < size; k++, l++) {
                    arr.add(new ArrayList<String>());
                    arr.get(k).add(userPastVaccines.get(i).get(l).get(1));
                    arr.get(k).add(userPastVaccines.get(i).get(l).get(2));
                    arr.get(k).add(userPastVaccines.get(i).get(l).get(3));
                }
            }
        }
        return arr;
    }



    public ArrayList<ArrayList<String>> getPersonNotifs(int position){
        int i=0,k=0;
        ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> cVI = LocalInfo.getInstance(ctx).childVaccineNotifs.get(position);
        ArrayList<ArrayList<String>> tAVI = LocalInfo.getInstance(ctx).teenAdultVaccineNotifs.get(position);
        for (i = 0; i < cVI.size(); i++) {
            if (cVI.get(i).get(3) != null) {
                arr.add(new ArrayList<String>());
                arr.get(k).add(cVI.get(i).get(1));
                arr.get(k).add(cVI.get(i).get(2));
                arr.get(k).add(cVI.get(i).get(4));
                k++;
            }
        }
        for (int j = 0; j < tAVI.size(); j++) {
            if (tAVI.get(j).get(3) != null) {
                arr.add(new ArrayList<String>());
                arr.get(k).add(tAVI.get(j).get(1));
                arr.get(k).add(tAVI.get(j).get(2));
                arr.get(k).add(tAVI.get(j).get(4));
                k++;
            }
        }
        return arr;
    }

    public boolean syncVariable(){
        getInstance(ctx).pList = null;
        getInstance(ctx).childVaccineNotifs = new ArrayList<ArrayList<ArrayList<String>>>();
        getInstance(ctx).teenAdultVaccineNotifs = new ArrayList<ArrayList<ArrayList<String>>>();
        getInstance(ctx).userPastVaccineList = new ArrayList<ArrayList<ArrayList<String>>>();

        return true;
    }


}
