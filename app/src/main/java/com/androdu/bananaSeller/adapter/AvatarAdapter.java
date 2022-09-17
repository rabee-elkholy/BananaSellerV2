package com.androdu.bananaSeller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvatarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Integer> modelList;
    private OnItemClickListener mItemClickListener;

    public AvatarAdapter(List<Integer> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.avatar_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final Integer model = getItem(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.avatarItemCivImage.setImageResource(model);

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
        void onItemClick(int position, Integer model);
    }

    private Integer getItem(int position) {
        return modelList.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar_item_civ_image)
        ImageView avatarItemCivImage;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> mItemClickListener.onItemClick(getAdapterPosition(), modelList.get(getAdapterPosition())));
        }
    }
}
