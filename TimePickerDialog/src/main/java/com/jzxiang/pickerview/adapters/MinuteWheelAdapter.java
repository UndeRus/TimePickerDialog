package com.jzxiang.pickerview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Dmitry Uspensky on 30.03.18.
 */
public class MinuteWheelAdapter extends NumericWheelAdapterWIthDisabled {

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
    public MinuteWheelAdapter(Context context, int minValue, int maxValue, String format, String unit, int minuteInterval, Calendar currentCalendar, List<Calendar> disabledDates) {
        super(context, minValue, maxValue, format, unit, currentCalendar, disabledDates);
        this.minuteInterval = minuteInterval;
        this.minValue = Math.round(((float) minValue / (float) minuteInterval)) * minuteInterval;
        this.maxValue = Math.round(((float) maxValue / (float) minuteInterval)) * minuteInterval;
        this.maxValue = this.maxValue == 60 ? this.maxValue - minuteInterval : this.maxValue;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = (minValue + index * minuteInterval);
            String text = !TextUtils.isEmpty(format) ? String.format(format, value) : Integer.toString(value);
            text = TextUtils.isEmpty(unit) ? text : text + unit;
            Spannable spannable = new SpannableString(text);
            if (isItemDisable()) {
                spannable.setSpan(new ForegroundColorSpan(Color.LTGRAY), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spannable;
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return ((maxValue - minValue) / minuteInterval) + 1;
    }
}
