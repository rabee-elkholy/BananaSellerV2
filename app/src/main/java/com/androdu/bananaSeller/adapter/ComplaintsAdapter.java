package com.androdu.bananaSeller.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.complaints.Complaint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ENGLISH;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;

public class ComplaintsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Activity activity;
    private List<Complaint> modelList;
    private OnItemClickListener mItemClickListener;

    public ComplaintsAdapter(Activity activity, List<Complaint> modelList) {
        this.activity = activity;
        this.modelList = modelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final Complaint model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            if (getLanguagePref(activity).equals(LANGUAGE_KEY_ENGLISH))
                genericViewHolder.complaintItemTvTitle.setText(model.getReason().getReasonEn());
            else if (getLanguagePref(activity).equals(LANGUAGE_KEY_ARABIC))
                genericViewHolder.complaintItemTvTitle.setText(model.getReason().getReasonAr());
            else
                genericViewHolder.complaintItemTvTitle.setText(model.getReason().getReasonUr());

        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position, Complaint model);
    }

    private Complaint getItem(int position) {
        return modelList.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.complaint_item_tv_title)
        TextView complaintItemTvTitle;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });

        }
    }
}
