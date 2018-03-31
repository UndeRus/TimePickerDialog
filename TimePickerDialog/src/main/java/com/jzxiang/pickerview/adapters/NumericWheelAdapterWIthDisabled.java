package com.jzxiang.pickerview.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Dmitry Uspensky on 31/03/2018.
 */
public class NumericWheelAdapterWIthDisabled extends NumericWheelAdapter {

    private Calendar currentCalendar;
    private List<Calendar> disabledDates;

    public NumericWheelAdapterWIthDisabled(Context context, int minValue, int maxValue, String format, String unit, Calendar currentCalendar, List<Calendar> disabledDates) {
        super(context, minValue, maxValue, format, unit);
        this.currentCalendar = currentCalendar;
        this.disabledDates = disabledDates;
    }

    protected boolean isItemDisable() {
        for (Calendar disabledDate : disabledDates) {
            int disableYear = disabledDate.get(Calendar.YEAR);
            int disableMonth = disabledDate.get(Calendar.MONTH);
            int disableDate = disabledDate.get(Calendar.DAY_OF_MONTH);
            int currentYear = currentCalendar.get(Calendar.YEAR);
            int currentMonth = currentCalendar.get(Calendar.MONTH);
            int currentDate = currentCalendar.get(Calendar.DAY_OF_MONTH);
            if (disableYear == currentYear && currentMonth == disableMonth && currentDate == disableDate) {
                return true;
            }
        }
        return false;
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
