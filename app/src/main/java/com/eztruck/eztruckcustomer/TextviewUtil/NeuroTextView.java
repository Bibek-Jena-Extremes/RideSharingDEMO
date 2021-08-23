package com.eztruck.eztruckcustomer.TextviewUtil;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.eztruck.eztruckcustomer.FontUtil.Font;

public class NeuroTextView extends AppCompatTextView {
    public NeuroTextView(Context context) {
        super(context);
        setFont(context);
    }

    public NeuroTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public NeuroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont(context);
    }

    private void setFont(Context context) {
        setTypeface(Font.neuro_political_font(context));
    }
}


