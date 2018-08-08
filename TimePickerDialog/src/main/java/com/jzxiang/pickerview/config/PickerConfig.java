package com.jzxiang.pickerview.config;

import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.data.WheelCalendar;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by jzxiang on 16/5/15.
 */
public class PickerConfig {

    public Type mType = DefaultConfig.TYPE;
    public int mThemeColor = DefaultConfig.COLOR;

    public String mCancelString = DefaultConfig.CANCEL;
    public String mSureString = DefaultConfig.SURE;
    public String mTitleString = DefaultConfig.TITLE;
    public int mToolBarTVColor = DefaultConfig.TOOLBAR_TV_COLOR;

    public int mWheelTVNormalColor = DefaultConfig.TV_NORMAL_COLOR;
    public int mWheelTVSelectorColor = DefaultConfig.TV_SELECTOR_COLOR;
    public int mWheelTVSize = DefaultConfig.TV_SIZE;
    public boolean cyclic = DefaultConfig.CYCLIC;

    public String mYear = DefaultConfig.YEAR;
    public String mMonth = DefaultConfig.MONTH;
    public String mDay = DefaultConfig.DAY;
    public String mHour = DefaultConfig.HOUR;
    public String mMinute = DefaultConfig.MINUTE;

    public Integer mLeftMargin = DefaultConfig.LEFT_MARGIN;
    public Integer mRightMargin = DefaultConfig.RIGHT_MARGIN;

    public String mCurrentDateCaption = DefaultConfig.CURRENT_DATE_CAPTION;
    public String mNextDateCaption = DefaultConfig.NEXT_DAY_CAPTION;

    /**
     * The min timeMillseconds
     */
    public WheelCalendar mMinCalendar = new WheelCalendar(0);

    /**
     * The max timeMillseconds
     */
    public WheelCalendar mMaxCalendar = new WheelCalendar(0);

    /**
     * The default selector timeMillseconds
     */
    public WheelCalendar mCurrentCalendar = new WheelCalendar(System.currentTimeMillis());

    public OnDateSetListener mCallBack;

    /**
     * The minute interval
     */
    public int mMinuteInterval = 1;

    /**
     * The month text
     */
    public boolean mShowMonthName = false;

    /**
     * The disabled dates
     */
    public List<Calendar> mDisabledDates = new ArrayList<>();

    /**
     * The disabled date selected text
     */
    public String mDisabledDateSelectedErrorText = "This date is disabled";
}
