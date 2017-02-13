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
            dismiss();
        }
    };
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
        mBtnAdd = (Button) view.findViewById(R.id.btn_add);

        mBtnClose.setOnClickListener(mBtnClickListener);
    }
}
