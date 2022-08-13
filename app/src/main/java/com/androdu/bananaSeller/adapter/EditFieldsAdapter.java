package com.androdu.bananaSeller.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditFieldsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private int type;
    private Activity activity;
    private List<Filter> modelList;
    private EditFieldsAdapter.OnItemClickListener mItemClickListener;


    public EditFieldsAdapter(Activity activity, List<Filter> modelList, int type) {
        this.activity = activity;
        this.modelList = modelList;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fields_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final Filter model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            genericViewHolder.favoriteListsItemTvTitle.setText(model.getName());

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


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.favorite_lists_item_tv_title)
        TextView favoriteListsItemTvTitle;
        @BindView(R.id.favorite_lists_item_btn_remove)
        ImageButton favoriteListsItemBtnRemove;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            if (type == 1)
                favoriteListsItemBtnRemove.setVisibility(View.GONE);
            else {
                favoriteListsItemBtnRemove.setVisibility(View.VISIBLE);

                favoriteListsItemBtnRemove.setImageResource(R.drawable.ic_add);
            }

            favoriteListsItemBtnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClick(getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }
}
