package slidenerd.vivz.bucketdrops.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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
import slidenerd.vivz.bucketdrops.extras.Util;

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
            dropHolder.setWhat(drop.getWhat());
            dropHolder.setBackground(drop.isCompleted());
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

    public void markComplete(int position) {
        if (position < mResults.size()) {
            mRealm.beginTransaction();
            mResults.get(position).setCompleted(true);
            mRealm.commitTransaction();
            notifyItemChanged(position);
        }
    }

    public static class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextWhat;
        TextView mTextWhen;
        MarkListener mMarkListener;
        Context mContext;
        View mItemView;

        public DropHolder(View itemView, MarkListener listener) {
            super(itemView);
            mItemView = itemView;
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
            mTextWhen = (TextView) itemView.findViewById(R.id.tv_when);
            mMarkListener = listener;
        }

        public void setWhat(String what) {
            mTextWhat.setText(what);
        }

        @Override
        public void onClick(View v) {
            mMarkListener.onMark(getAdapterPosition());
        }

        public void setBackground(boolean completed) {
            Drawable drawable;
            if (completed) {
                drawable = ContextCompat.getDrawable(mContext, R.color.bg_drop_complete);
            } else {
                drawable = ContextCompat.getDrawable(mContext, R.drawable.bg_row_drop);
            }
            Util.setBackground(mItemView, drawable);
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
