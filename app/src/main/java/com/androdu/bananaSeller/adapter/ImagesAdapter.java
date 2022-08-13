package com.androdu.bananaSeller.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.helper.Constants;
import com.bumptech.glide.Glide;
import com.stfalcon.imageviewer.StfalconImageViewer;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<String> modelList;
    private int type;

    public ImagesAdapter(Activity activity, List<String> modelList, int type) {
        this.activity = activity;
        this.modelList = modelList;
        this.type = type;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaints_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final String model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            Glide.with(activity)
                    .load((type == 1) ? Constants.BASE_URL + model : model)
                    .into(genericViewHolder.addFilesItemImg);


        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    private String getItem(int position) {
        return modelList.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.add_files_item_img)
        ImageView addFilesItemImg;
        @BindView(R.id.add_files_item_remove)
        ImageView addFilesItemRemove;


        public ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            if (type == 1) {
                addFilesItemRemove.setVisibility(View.GONE);

            }

        }
        @OnClick({R.id.add_files_item_remove, R.id.add_files_item_img})
        public void onViewClicked(View v) {
            switch (v.getId()){
                case R.id.add_files_item_remove:
                    modelList.remove(getAdapterPosition());
                    notifyDataSetChanged();
                    break;
                case R.id.add_files_item_img:
                    new StfalconImageViewer.Builder<String>(activity , Collections.singletonList(modelList.get(getAdapterPosition())), (imageView, imageUrl)
                            -> Glide.with(activity).load((type == 1) ? Constants.BASE_URL + imageUrl : imageUrl).into(imageView)).show();

            }

        }
    }
}
