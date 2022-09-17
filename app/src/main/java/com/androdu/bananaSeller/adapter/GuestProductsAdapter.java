package com.androdu.bananaSeller.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.products.Product;
import com.androdu.bananaSeller.helper.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ENGLISH;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;


public class GuestProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Activity activity;
    private final List<Product> modelList;

    public GuestProductsAdapter(Activity activity, List<Product> modelList) {
        this.activity = activity;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.guest_products_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final Product model = getItem(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            Glide.with(activity)
                    .load(Constants.BASE_URL + model.getImageUrl())
                    .into(viewHolder.productsItemIvImage);
            if (getLanguagePref(activity).equals(LANGUAGE_KEY_ARABIC))
                viewHolder.productsItemTvTitle.setText(model.getNameAr());
            else if (getLanguagePref(activity).equals(LANGUAGE_KEY_ENGLISH))
                viewHolder.productsItemTvTitle.setText(model.getNameEn());
            else
                viewHolder.productsItemTvTitle.setText(model.getNameUr());


        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private Product getItem(int position) {
        return modelList.get(position);
    }

    @SuppressLint("NonConstantResourceId")
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.products_item_iv_image)
        CircleImageView productsItemIvImage;
        @BindView(R.id.products_item_tv_title)
        TextView productsItemTvTitle;


        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
