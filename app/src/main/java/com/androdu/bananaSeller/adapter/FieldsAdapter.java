package com.androdu.bananaSeller.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FieldsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Filter> modelList;
    public int lastChecked = 0;

    public FieldsAdapter(List<Filter> modelList) {
        this.modelList = modelList;

        boolean isChecked = false;
        for (Filter f : modelList) {
            if (f.isChecked()){
                isChecked = true;
                lastChecked = f.getKey();

            }
        }

        if (!isChecked)
            modelList.get(0).setChecked(true);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bottom_sheet_list_item_radio, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final Filter model = getItem(holder.getAdapterPosition());
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.bottomSheetCbFilterItem.setText(model.getName());
            viewHolder.bottomSheetCbFilterItem.setChecked(model.isChecked());

            viewHolder.bottomSheetCbFilterItem.setOnClickListener(v -> {
                modelList.get(lastChecked).setChecked(false);
                model.setChecked(true);
                lastChecked = model.getKey();
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private Filter getItem(int position) {
        return modelList.get(position);
    }

    @SuppressLint("NonConstantResourceId")
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bottom_sheet_cb_filter_item)
        RadioButton bottomSheetCbFilterItem;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
