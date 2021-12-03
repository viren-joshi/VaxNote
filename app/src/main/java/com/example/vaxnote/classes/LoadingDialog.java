package com.example.vaxnote.classes;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import com.example.vaxnote.R;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;
    public LoadingDialog(Activity activity){
        this.activity = activity;
    }
    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog,null));

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dissmissLoadingDialog(){
        alertDialog.dismiss();
    }
}
