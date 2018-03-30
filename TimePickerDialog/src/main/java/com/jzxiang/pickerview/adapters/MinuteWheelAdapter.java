package com.jzxiang.pickerview.adapters;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by Dmitry Uspensky on 30.03.18.
 */
public class MinuteWheelAdapter extends NumericWheelAdapter {

    private int minuteInterval = 1;

    /**
     * Constructor
     *
     * @param context        the current context
     * @param minValue       the wheel min value
     * @param maxValue       the wheel max value
     * @param format         the format string
     * @param unit           the wheel unit value
     */
    public MinuteWheelAdapter(Context context, int minValue, int maxValue, String format, String unit, int minuteInterval) {
        super(context, minValue, maxValue, format, unit);
        this.minuteInterval = minuteInterval;
        this.minValue = Math.round(((float) minValue / (float) minuteInterval)) * minuteInterval;
        this.maxValue = Math.round(((float) maxValue / (float) minuteInterval)) * minuteInterval;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = (minValue + index * minuteInterval);
            String text = !TextUtils.isEmpty(format) ? String.format(format, value) : Integer.toString(value);
            text = TextUtils.isEmpty(unit) ? text : text + unit;

            return text;
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return ((maxValue - minValue) / minuteInterval) + 1;
    }
}
