package com.brunomanfrinato.upcomingmoviesapp.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.brunomanfrinato.upcomingmoviesapp.R;

public class CustomToast {

    private TextView tvMessage;
    private Toast toast;

    public void showToast(Activity activity, String message) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.toast_custom, (ViewGroup) activity.findViewById(R.id.custom_toast));

        tvMessage = (TextView) layout.findViewById(R.id.text_toast_message);
        tvMessage.setText(message);

        toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
