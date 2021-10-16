package com.example.yearbook.interfaces;

import android.content.Context;
import android.view.View.OnClickListener;

public interface Clickable {
    String getId();
    int getId_int();
    OnClickListener getOnClickListener(Context context);
}
