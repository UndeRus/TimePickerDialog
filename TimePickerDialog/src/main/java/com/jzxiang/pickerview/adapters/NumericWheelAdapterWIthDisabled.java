package com.jzxiang.pickerview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.jzxiang.pickerview.TimeWheel;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Dmitry Uspensky on 31/03/2018.
 */
public class NumericWheelAdapterWIthDisabled extends NumericWheelAdapter {


    public NumericWheelAdapterWIthDisabled(Context context, int minValue, int maxValue, String format, String unit) {
        super(context, minValue, maxValue, format, unit);
    }

    protected boolean isItemDisable() {
        return TimeWheel.sDisabled;
    }

    @Override
    public CharSequence getItemText(int index) {
        CharSequence text = super.getItemText(index);
        Spannable spannable = new SpannableString(text);
        if (isItemDisable()) {
            spannable.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }
}
