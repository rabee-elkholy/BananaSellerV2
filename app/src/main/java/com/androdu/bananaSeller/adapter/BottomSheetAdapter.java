package com.androdu.bananaSeller.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BottomSheetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Filter> modelList;


    public BottomSheetAdapter(Activity activity, List<Filter> modelList) {
        this.activity = activity;
        this.modelList = modelList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bottom_sheet_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final Filter model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.bottomSheetCbFilterItem.setText(model.getName());
            genericViewHolder.bottomSheetCbFilterItem.setChecked(model.isChecked());
            genericViewHolder.bottomSheetCbFilterItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    model.setChecked(isChecked);
                }
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


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.bottom_sheet_cb_filter_item)
        CheckBox bottomSheetCbFilterItem;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
