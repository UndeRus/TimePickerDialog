package com.jzxiang.pickerview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jzxiang.pickerview.config.PickerConfig;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.data.WheelCalendar;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.util.Calendar;
import java.util.List;

/**
 * Created by jzxiang on 16/4/19.
 */
public class TimePickerDialog extends DialogFragment implements View.OnClickListener {
    PickerConfig mPickerConfig;
    private TimeWheel mTimeWheel;
    private long mCurrentMillSeconds;

    private static TimePickerDialog newIntance(PickerConfig pickerConfig) {
        TimePickerDialog timePickerDialog = new TimePickerDialog();
        timePickerDialog.initialize(pickerConfig);
        return timePickerDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public void onResume() {
        super.onResume();
        int height = getResources().getDimensionPixelSize(R.dimen.picker_height);

        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, height);//Here!
        window.setGravity(Gravity.BOTTOM);
    }

    private void initialize(PickerConfig pickerConfig) {
        mPickerConfig = pickerConfig;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.Dialog_NoTitle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(initView());
        return dialog;
    }

    View initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.timepicker_layout, null);
        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
        cancel.setOnClickListener(this);
        TextView sure = (TextView) view.findViewById(R.id.tv_sure);
        sure.setOnClickListener(this);
        TextView title = (TextView) view.findViewById(R.id.tv_title);
        View toolbar = view.findViewById(R.id.toolbar);
        View leftMargin = view.findViewById(R.id.left_empty_view);
        View rightMargin = view.findViewById(R.id.right_empty_view);

        title.setText(mPickerConfig.mTitleString);
        cancel.setText(mPickerConfig.mCancelString);
        sure.setText(mPickerConfig.mSureString);
        toolbar.setBackgroundColor(mPickerConfig.mThemeColor);

        leftMargin.setLayoutParams(new LinearLayout.LayoutParams(mPickerConfig.mLeftMargin,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        rightMargin.setLayoutParams(new LinearLayout.LayoutParams(mPickerConfig.mRightMargin,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        leftMargin.setOnClickListener(this);
        rightMargin.setOnClickListener(this);

        mTimeWheel = new TimeWheel(view, mPickerConfig);
        return view;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_cancel || i == R.id.left_empty_view || i == R.id.right_empty_view) {
            dismiss();
        } else if (i == R.id.tv_sure) {
            sureClicked();
        }
    }
    
    /*
    * @desc This method returns the current milliseconds. If current milliseconds is not set,
    *       this will return the system milliseconds.
    * @param none
    * @return long - the current milliseconds.
    */
    public long getCurrentMillSeconds() {
        if (mCurrentMillSeconds == 0)
            return System.currentTimeMillis();

        return mCurrentMillSeconds;
    }

    /*
    * @desc This method is called when onClick method is invoked by sure button. A Calendar instance is created and 
    *       initialized. 
    * @param none
    * @return none
    */
    void sureClicked() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        calendar.set(Calendar.YEAR, mTimeWheel.getCurrentYear());
        calendar.set(Calendar.MONTH, mTimeWheel.getCurrentMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, mTimeWheel.getCurrentDay());
        calendar.set(Calendar.HOUR_OF_DAY, mTimeWheel.getCurrentHour());
        calendar.set(Calendar.MINUTE, mTimeWheel.getCurrentMinute());

        int selectedYear = calendar.get(Calendar.YEAR);
        int selectedMonth = calendar.get(Calendar.MONTH) + 1;
        int selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

        mCurrentMillSeconds = calendar.getTimeInMillis();
        boolean selectedDisableDate = false;
        for (Calendar disableCalendar : mPickerConfig.mDisabledDates) {
            int disabledYear = disableCalendar.get(Calendar.YEAR);
            int disabledMonth = disableCalendar.get(Calendar.MONTH) + 1;
            int disabledDay = disableCalendar.get(Calendar.DAY_OF_MONTH);

            if (selectedYear == disabledYear && selectedMonth == disabledMonth && selectedDay == disabledDay) {
                selectedDisableDate = true;
                break;
            }
        }
        if (selectedDisableDate) {
            Toast.makeText(getActivity(), mPickerConfig.mDisabledDateSelectedErrorText, Toast.LENGTH_SHORT).show();
        } else {
            if (mPickerConfig.mCallBack != null) {
                mPickerConfig.mCallBack.onDateSet(this, mCurrentMillSeconds);
            }
            dismiss();
        }
    }

    public static class Builder {
        PickerConfig mPickerConfig;

        public Builder() {
            mPickerConfig = new PickerConfig();
        }

        public Builder setType(Type type) {
            mPickerConfig.mType = type;
            return this;
        }

        public Builder setThemeColor(int color) {
            mPickerConfig.mThemeColor = color;
            return this;
        }

        public Builder setCancelStringId(String left) {
            mPickerConfig.mCancelString = left;
            return this;
        }

        public Builder setSureStringId(String right) {
            mPickerConfig.mSureString = right;
            return this;
        }

        public Builder setTitleStringId(String title) {
            mPickerConfig.mTitleString = title;
            return this;
        }

        public Builder setToolBarTextColor(int color) {
            mPickerConfig.mToolBarTVColor = color;
            return this;
        }

        public Builder setWheelItemTextNormalColor(int color) {
            mPickerConfig.mWheelTVNormalColor = color;
            return this;
        }

        public Builder setWheelItemTextSelectorColor(int color) {
            mPickerConfig.mWheelTVSelectorColor = color;
            return this;
        }

        public Builder setWheelItemTextSize(int size) {
            mPickerConfig.mWheelTVSize = size;
            return this;
        }

        public Builder setCyclic(boolean cyclic) {
            mPickerConfig.cyclic = cyclic;
            return this;
        }

        public Builder setMinMillseconds(long millseconds) {
            mPickerConfig.mMinCalendar = new WheelCalendar(millseconds);
            return this;
        }

        public Builder setMaxMillseconds(long millseconds) {
            mPickerConfig.mMaxCalendar = new WheelCalendar(millseconds);
            return this;
        }

        public Builder setCurrentMillseconds(long millseconds) {
            mPickerConfig.mCurrentCalendar = new WheelCalendar(millseconds);
            return this;
        }

        public Builder setYearText(String year){
            mPickerConfig.mYear = year;
            return this;
        }

        public Builder setMonthText(String month){
            mPickerConfig.mMonth = month;
            return this;
        }

        public Builder setDayText(String day){
            mPickerConfig.mDay = day;
            return this;
        }

        public Builder setHourText(String hour){
            mPickerConfig.mHour = hour;
            return this;
        }

        public Builder setMinuteText(String minute){
            mPickerConfig.mMinute = minute;
            return this;
        }

        public Builder setCallBack(OnDateSetListener listener) {
            mPickerConfig.mCallBack = listener;
            return this;
        }

        public Builder setMinuteInterval(int minuteInterval) {
            mPickerConfig.mMinuteInterval = minuteInterval;
            return this;
        }

        public Builder setShowMonthName(boolean show) {
            mPickerConfig.mShowMonthName = show;
            return this;
        }

        public Builder setDisabledDates(List<Calendar> disableDates) {
            mPickerConfig.mDisabledDates = disableDates;
            return this;
        }

        public Builder setDisabledDateSelectedText(String disabledDateSelectedText) {
            mPickerConfig.mDisabledDateSelectedErrorText = disabledDateSelectedText;
            return this;
        }

        public Builder setLeftRightMargins(Context context, int leftDp, int rightDp) {
            Resources r = context.getResources();
            float pxLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDp, r.getDisplayMetrics());
            float pxRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDp, r.getDisplayMetrics());
            mPickerConfig.mLeftMargin = Math.round(pxLeft);
            mPickerConfig.mRightMargin = Math.round(pxRight);
            return this;
        }

        public Builder setCurrentDateCaption(String caption) {
            mPickerConfig.mCurrentDateCaption = caption;
            return this;
        }

        public Builder setNextDateCaption(String caption) {
            mPickerConfig.mNextDateCaption = caption;
            return this;
        }

        public TimePickerDialog build() {
            return newIntance(mPickerConfig);
        }

    }


}
