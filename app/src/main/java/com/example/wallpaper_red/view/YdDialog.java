package com.example.wallpaper_red.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class YdDialog extends Dialog {
    public YdDialog(@NonNull Context context, int width, int height, View layout,int style) {
        super(context,style);
        setContentView(layout);
        Window window=getWindow();
        WindowManager.LayoutParams params=window.getAttributes();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.width= width;
        params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity= Gravity.TOP;
        window.setAttributes(params);
    }

    public YdDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected YdDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


}
