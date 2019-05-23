package com.thereza.videoplayerapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thereza.videoplayerapp.R;

public class ToastFactory {

    public static Toast createToast(Context context, String message) {
        return Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT);
    }

    public static Toast createCenterToast(Activity mActivity){
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.buffering_toast_layout, (ViewGroup) mActivity.findViewById(R.id.rl_buffer_toast_container));
        Toast toast = new Toast(mActivity);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        return toast;
    }

    public static Toast createCustomToast(Activity mActivity){
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) mActivity.findViewById(R.id.custom_toast_container));
        Toast toast = new Toast(mActivity);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
        return toast;
    }

}
