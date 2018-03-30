package com.jzxiang.pickerview.adapters;

import android.content.Context;

import com.jzxiang.pickerview.R;

/**
 * Created by Dmitry Uspensky on 30.03.18.
 */
public class MonthNameWheelAdapter extends NumericWheelAdapter {

    private String[] monthArray;

    /**
     * Constructor
     *
     * @param context        the current context
     * @param minValue       the wheel min value
     * @param maxValue       the wheel max value
     */
    public MonthNameWheelAdapter(Context context, int minValue, int maxValue) {
        super(context, minValue, maxValue);
        this.monthArray = context.getResources().getStringArray(R.array.month);
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return monthArray[value-1];
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }

}
