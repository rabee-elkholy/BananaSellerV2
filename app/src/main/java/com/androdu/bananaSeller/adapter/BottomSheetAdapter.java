package com.androdu.bananaSeller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomSheetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Filter> modelList;

    public BottomSheetAdapter(List<Filter> modelList) {
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bottom_sheet_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final Filter model = getItem(position);
            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.bottomSheetCbFilterItem.setText(model.getName());
            viewHolder.bottomSheetCbFilterItem.setChecked(model.isChecked());
            viewHolder.bottomSheetCbFilterItem.setOnCheckedChangeListener((buttonView, isChecked) -> model.setChecked(isChecked));
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    private Filter getItem(int position) {
        return modelList.get(position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bottom_sheet_cb_filter_item)
        CheckBox bottomSheetCbFilterItem;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
