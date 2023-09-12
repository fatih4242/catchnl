package com.tokersoftware.catchnl.helper;

import android.app.ProgressDialog;
import android.content.Context;

public class Dialog {
    ProgressDialog progressDialog;

    public Dialog(Context context){
        progressDialog = new ProgressDialog(context);
    }

    public void ShowDialog(String title, String message){
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void DismissDialog(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
