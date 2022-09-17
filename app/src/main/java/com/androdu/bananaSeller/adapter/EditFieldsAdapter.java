package com.androdu.bananaSeller.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditFieldsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int type;
    private final List<Filter> modelList;
    private EditFieldsAdapter.OnItemClickListener mItemClickListener;


    public EditFieldsAdapter(List<Filter> modelList, int type) {
        this.modelList = modelList;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fields_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final Filter model = getItem(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.favoriteListsItemTvTitle.setText(model.getName());

        }
    }


    public void SetOnItemClickListener(final EditFieldsAdapter.OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface OnItemClickListener {
        void onClick(int position, Filter model);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private Filter getItem(int position) {
        return modelList.get(position);
    }


    @SuppressLint("NonConstantResourceId")
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.favorite_lists_item_tv_title)
        TextView favoriteListsItemTvTitle;
        @BindView(R.id.favorite_lists_item_btn_remove)
        ImageButton favoriteListsItemBtnRemove;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            favoriteListsItemBtnRemove.setImageResource(type == 1 ? R.drawable.ic_check : R.drawable.ic_arrow_up);

            favoriteListsItemBtnRemove.setOnClickListener(v -> mItemClickListener.onClick(getAdapterPosition(), modelList.get(getAdapterPosition())));
        }
    }
}
