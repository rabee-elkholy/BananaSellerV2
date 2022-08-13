package com.androdu.bananaSeller.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ENGLISH;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;


public class LanguagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<String> modelList;
    public int lastChecked;

    public LanguagesAdapter(Activity activity, List<String> modelList) {
        this.activity = activity;
        this.modelList = modelList;
        if (getLanguagePref(activity).equals(LANGUAGE_KEY_ENGLISH))
            lastChecked = 0;
        else if (getLanguagePref(activity).equals(LANGUAGE_KEY_ARABIC))
            lastChecked = 1;
        else
            lastChecked = 2;
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
            final String model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            genericViewHolder.bottomSheetCbFilterItem.setText(model);
            if (lastChecked == position)
                genericViewHolder.bottomSheetCbFilterItem.setChecked(true);
            else
                genericViewHolder.bottomSheetCbFilterItem.setChecked(false);

            genericViewHolder.bottomSheetCbFilterItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastChecked = position;
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return modelList.size();
    }

    private String getItem(int position) {
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
