package com.androdu.bananaSeller.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FieldsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Filter> modelList;
    public int lastChecked = 0;

    public FieldsAdapter(Activity activity, List<Filter> modelList) {
        this.activity = activity;
        this.modelList = modelList;

        boolean isChecked = false;
        for (Filter f : modelList) {
            if (f.isChecked()){
                isChecked = true;
                lastChecked = f.getKey();
                Log.d("error_", "forEach: " + f.getKey());

            }
        }

        if (!isChecked)
            modelList.get(0).setChecked(true);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bottom_sheet_list_item_radio, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final Filter model = getItem(holder.getAdapterPosition());
            ViewHolder genericViewHolder = (ViewHolder) holder;
            genericViewHolder.bottomSheetCbFilterItem.setText(model.getName());
            if (model.isChecked()) {
                genericViewHolder.bottomSheetCbFilterItem.setChecked(true);
            }else
                genericViewHolder.bottomSheetCbFilterItem.setChecked(false);

            genericViewHolder.bottomSheetCbFilterItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modelList.get(lastChecked).setChecked(false);
                    model.setChecked(true);
                    lastChecked = model.getKey();
                    notifyDataSetChanged();
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
        RadioButton bottomSheetCbFilterItem;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
