package slidenerd.vivz.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

import io.realm.Realm;
import slidenerd.vivz.bucketdrops.beans.Drop;

/**
 * Created by vivz on 28/12/15.
 */
public class DialogAdd extends DialogFragment {

    private ImageButton mBtnClose;
    private EditText mInputWhat;
    private DatePicker mInputWhen;
    private Button mBtnAdd;

    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_add_it:
                    addAction();
                    break;
            }
            dismiss();
        }
    };

    //TODO process date
    private void addAction() {
        //get the value of the 'goal' or 'to-do'
        //get the time when it was added
        String what = mInputWhat.getText().toString();
        String date = mInputWhen.getDayOfMonth() + "/" + mInputWhen.getMonth() + "/" + mInputWhen.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, mInputWhen.getDayOfMonth());
        calendar.set(Calendar.MONTH, mInputWhen.getMonth());
        calendar.set(Calendar.YEAR, mInputWhen.getYear());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long now = System.currentTimeMillis();
        Realm realm = Realm.getDefaultInstance();
        Drop drop = new Drop(what, now, calendar.getTimeInMillis(), false);
        realm.beginTransaction();
        realm.copyToRealm(drop);
        realm.commitTransaction();
        realm.close();

    }

    public DialogAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        mInputWhat = (EditText) view.findViewById(R.id.et_drop);
        mInputWhen = (DatePicker) view.findViewById(R.id.bpv_date);
        mBtnAdd = (Button) view.findViewById(R.id.btn_add_it);

        mBtnClose.setOnClickListener(mBtnClickListener);
        mBtnAdd.setOnClickListener(mBtnClickListener);
    }
}
