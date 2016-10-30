package slidenerd.vivz.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;
import slidenerd.vivz.bucketdrops.R;
import slidenerd.vivz.bucketdrops.beans.Drop;

/**
 * Created by vivz on 30/12/15.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {
    public static final int ITEM = 0;
    public static final int FOOTER = 1;
    private MarkListener mMarkListener;

    private LayoutInflater mInflater;
    private Realm mRealm;
    private RealmResults<Drop> mResults;
    public static final String TAG = "VIVZ";
    private AddListener mAddListener;

    public AdapterDrops(Context context, Realm realm, RealmResults<Drop> results) {
        mInflater = LayoutInflater.from(context);
        mRealm = realm;
        update(results);
    }

    public AdapterDrops(Context context, Realm realm, RealmResults<Drop> results, AddListener listener, MarkListener markListener) {
        mInflater = LayoutInflater.from(context);
        update(results);
        mRealm = realm;
        mAddListener = listener;
        mMarkListener = markListener;
    }

    public void update(RealmResults<Drop> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        //We return an item if results are null or if the position is within the bounds of the results
        if (mResults == null || position < mResults.size()) {
            return ITEM;
        } else {
            return FOOTER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View view = mInflater.inflate(R.layout.footer, parent, false);
            return new FooterHolder(view, mAddListener);
        } else {
            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            return new DropHolder(view, mMarkListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder) {
            DropHolder dropHolder = (DropHolder) holder;
            Drop drop = mResults.get(position);
            dropHolder.mTextWhat.setText(drop.getWhat());
        }
    }

    @Override
    public int getItemCount() {
        if (mResults == null || mResults.isEmpty()) {
            return 0;
        } else {
            return mResults.size() + 1;
        }

    }

    @Override
    public void onSwipe(int position) {
        if (position < mResults.size()) {
            mRealm.beginTransaction();
            mResults.get(position).removeFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }
    }

    public static class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextWhat;
        TextView mTextWhen;
        MarkListener mMarkListener;

        public DropHolder(View itemView, MarkListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
            mTextWhen = (TextView) itemView.findViewById(R.id.tv_when);
            mMarkListener = listener;
        }

        @Override
        public void onClick(View v) {
            mMarkListener.onMark(getAdapterPosition());
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button mBtnAdd;
        AddListener mListener;

        public FooterHolder(View itemView) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);
        }

        public FooterHolder(View itemView, AddListener listener) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            mListener.add();
        }
    }
}
