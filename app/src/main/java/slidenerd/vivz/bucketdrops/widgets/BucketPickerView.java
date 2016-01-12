package slidenerd.vivz.bucketdrops.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import slidenerd.vivz.bucketdrops.R;

/**
 * Created by vivz on 10/01/16.
 */
public class BucketPickerView extends LinearLayout implements View.OnTouchListener {
    private Calendar mCalendar;
    private TextView mTextDate;
    private TextView mTextMonth;
    private TextView mTextYear;
    private SimpleDateFormat mFormatter;
    private String TAG = "VIVZ";

    public BucketPickerView(Context context) {
        super(context);
        init(context);
    }

    public BucketPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BucketPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.bucket_picker_view, this);
        mCalendar = Calendar.getInstance();
        mFormatter = new SimpleDateFormat("MMM");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextDate = (TextView) this.findViewById(R.id.tv_date);
        mTextMonth = (TextView) this.findViewById(R.id.tv_month);
        mTextYear = (TextView) this.findViewById(R.id.tv_year);
        mTextDate.setOnTouchListener(this);
        mTextMonth.setOnTouchListener(this);
        mTextYear.setOnTouchListener(this);
        int date = mCalendar.get(Calendar.DATE);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);
        update(date, month, year, 0, 0, 0);
    }

    private void update(int date, int month, int year, int hour, int minute, int second) {
        mCalendar.set(Calendar.DATE, date);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.HOUR, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, second);
        mTextYear.setText(year + "");
        mTextDate.setText(date + "");
        mTextMonth.setText(mFormatter.format(mCalendar.getTime()));
    }

    public long getTime() {
        return mCalendar.getTimeInMillis();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "DOWN: x: " + event.getX() + " y: " + event.getY() + " raw x: " + event.getRawX() + " raw y: " + event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "MOVE: x: " + event.getX() + " y: " + event.getY() + " raw x: " + event.getRawX() + " raw y: " + event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "UP: x: " + event.getX() + " y: " + event.getY() + " raw x: " + event.getRawX() + " raw y: " + event.getRawY());
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "CANCEL: x: " + event.getX() + " y: " + event.getY() + " raw x: " + event.getRawX() + " raw y: " + event.getRawY());
                break;
        }
        return true;
    }
}
