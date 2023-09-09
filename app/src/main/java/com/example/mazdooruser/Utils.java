package com.example.mazdooruser;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;

public class Utils {



    public static void hideKeyboard(Activity activity){
        try {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static void showSnackBar(Activity context,String msg){

        Snackbar snackbar = Snackbar.make(
                context.findViewById(android.R.id.content),
                msg,
                Snackbar.LENGTH_SHORT
        );
        snackbar.show();
    }
}
