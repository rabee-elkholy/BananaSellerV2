package com.androdu.bananaSeller.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.orders.OrderProduct;
import com.androdu.bananaSeller.helper.Constants;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_URDU;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<OrderProduct> modelList;

    public ProductsAdapter(Activity activity, List<OrderProduct> modelList) {
        this.activity = activity;
        this.modelList = modelList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final OrderProduct model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;
            String locale = getLanguagePref(activity);
            if (locale.equals(LANGUAGE_KEY_ARABIC) || locale.equals(LANGUAGE_KEY_URDU)) {
                if (model.getPath().equals("clientProducts")) {
                    genericViewHolder.productsItemTvTitle.setText(model.getOrderProductDetails().getName());
                } else {
                    if (locale.equals(LANGUAGE_KEY_ARABIC))
                        genericViewHolder.productsItemTvTitle.setText(model.getOrderProductDetails().getNameAr());
                    else
                        genericViewHolder.productsItemTvTitle.setText(model.getOrderProductDetails().getNameUr());
                }
            } else
                genericViewHolder.productsItemTvTitle.setText(model.getOrderProductDetails().getNameEn());

            int unitIndex = Arrays.asList(Constants.units).indexOf(model.getUnit());
            model.setUnitStr((activity.getResources().getStringArray(R.array.weights))[unitIndex]);
            genericViewHolder.productsItemEtCounter.setText(model.getCount() + " " + model.getUnitStr());

        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private OrderProduct getItem(int position) {
        return modelList.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_item_tv_title)
        TextView productsItemTvTitle;
        @BindView(R.id.product_item_btn_increment)
        ImageButton productsItemBtnIncrement;
        @BindView(R.id.product_item_et_counter)
        EditText productsItemEtCounter;
        @BindView(R.id.product_item_btn_decrement)
        ImageButton productsItemBtnDecrement;


        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }


        @OnClick({R.id.product_item_btn_increment, R.id.product_item_btn_decrement})
        public void onViewClicked(View view) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) {
                return;
            }
            OrderProduct model = modelList.get(getAdapterPosition());

            switch (view.getId()) {
                case R.id.product_item_btn_increment:
                    if (model.getCount() < model.getAmount()) {
                        model.setCount(model.getCount() + 1);
                        productsItemEtCounter.setText(model.getCount() + " " + model.getUnitStr());
                    }
                    break;
                case R.id.product_item_btn_decrement:
                    if (model.getCount() > 0) {
                        model.setCount(model.getCount() - 1);
                        productsItemEtCounter.setText(model.getCount() + " " + model.getUnitStr());
                    }
                    break;

            }
        }

    }
}
