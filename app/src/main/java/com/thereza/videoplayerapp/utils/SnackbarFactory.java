package com.thereza.videoplayerapp.utils;

import android.content.Context;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.view.ViewGroup;

import com.thereza.videoplayerapp.R;


public class SnackbarFactory {

    public static Snackbar createSnackbar(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        return snackbar;
    }
}