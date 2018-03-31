package com.jzxiang.pickerview.adapters;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by Dmitry Uspensky on 31/03/2018.
 */
public class DateWheelAdapter extends NumericWheelAdapter {

    public DateWheelAdapter(Context context, int minValue, int maxValue, String format, String unit) {
        super(context, minValue, maxValue, format, unit);
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            String text = !TextUtils.isEmpty(format) ? String.format(format, value) : Integer.toString(value);
            text = TextUtils.isEmpty(unit) ? text : text + unit;

            return text;
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }
}
