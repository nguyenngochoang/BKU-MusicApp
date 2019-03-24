package com.example.firebase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class Aleart {
    private int type;
    private String message;


    public  Aleart (){
        // do nothing, apply for go back button
    }



    public Aleart(int type, String message, Activity activity, String valuename, String value){

        this.type = type;
        this.message = message;
        showAlert(type,message,activity,valuename,value);

    }

    private String returnString(Context c,int id){
        return c.getResources().getString(id);

    }

    private void showAlert(int typeOfAlert, String message, final Activity activity, final String valuename, final String value){
        //1 error
        //2 successful

        if(typeOfAlert == 1){
            AlertDialog.Builder adb = new AlertDialog.Builder(activity);


            adb.setMessage(message);
            adb.setTitle("Error");
            AlertDialog ad = adb.create();
            ad.show();
        }

        if(typeOfAlert == 2){
            AlertDialog.Builder adb = new AlertDialog.Builder(activity);


            adb.setMessage(message);
            adb.setTitle("Successful");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(valuename, value);
                    activity.setResult(Activity.RESULT_OK, returnIntent);
                    activity.finish();
                }
            });

            AlertDialog ad = adb.create();
            ad.show();
        }

    }


}
