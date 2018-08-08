package com.jzxiang.pickerview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.List;

/**
 * Created by Dmitry Uspensky on 31/03/2018.
 */
public class DateWheelAdapter extends NumericWheelAdapter {

    private List<Integer> disabledDates;

    public DateWheelAdapter(Context context, int minValue, int maxValue, String format, String unit, List<Integer> disabledDates) {
        super(context, minValue, maxValue, format, unit);
        this.disabledDates = disabledDates;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;

            String text = !TextUtils.isEmpty(format) ? String.format(format, value) : Integer.toString(value);
            text = TextUtils.isEmpty(unit) ? text : text + unit;

            Spannable spannable = new SpannableString(text);
            if (disabledDates.contains(value)) {
                spannable.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            return spannable;
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }
}