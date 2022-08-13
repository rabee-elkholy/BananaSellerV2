package com.androdu.bananaSeller.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.offers.Offer;
import com.androdu.bananaSeller.data.model.response.offers.OfferProduct;
import com.androdu.bananaSeller.helper.Constants;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_ARABIC;
import static com.androdu.bananaSeller.helper.LanguageManager.LANGUAGE_KEY_URDU;
import static com.androdu.bananaSeller.helper.LanguageManager.getLanguagePref;

public class MyOffersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<Offer> modelList;
    private OnItemClickListener mItemClickListener;
    private String type;

    public MyOffersAdapter(Activity activity, List<Offer> modelList, String type) {
        this.activity = activity;
        this.modelList = modelList;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_offers_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final Offer model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.offersListItemTvOfferNum.setText(model.getId());

            genericViewHolder.offersListItemTvQuantity.setText("");
            genericViewHolder.offersListItemTvItems.setText("");
            String local = getLanguagePref(activity);
            String productsStr = "";
            String unitsStr = "";

            try{
                for (OfferProduct product : model.getOfferProducts()) {
                    if (local.equals(LANGUAGE_KEY_ARABIC) || local.equals(LANGUAGE_KEY_URDU)) {
                        if (product.getPath().equals("clientProducts")) {
                            productsStr = productsStr.concat(product.getProduct().getName() + " , ");
                        } else {
                            if (local.equals(LANGUAGE_KEY_ARABIC))
                                productsStr = productsStr.concat(product.getProduct().getNameAr() + " , ");
                            else
                                productsStr = productsStr.concat(product.getProduct().getNameUr() + " , ");
                        }
                    } else
                        productsStr = productsStr.concat(product.getProduct().getNameEn() + " , ");

                    int unitIndex = Arrays.asList(Constants.units).indexOf(product.getUnit());
                    unitsStr = unitsStr.concat(product.getAmount() + " " + (activity.getResources().getStringArray(R.array.weights))[unitIndex] + " , ");

                }

                genericViewHolder.offersListItemTvItems.setText(productsStr.substring(0, productsStr.length() - 2));
                genericViewHolder.offersListItemTvQuantity.setText(unitsStr.substring(0, unitsStr.length() - 2));


                genericViewHolder.offersListItemTvPrice.setText(model.getPrice() + "");

                genericViewHolder.offersListItemTvAddress.setText(model.getOrder().getLocationDetails().getStringAdress());

                if (model.getOrder().getArriveDate() == 0)
                    genericViewHolder.offersListItemTvDeliveryDate.setText(R.string.direct_delivery);
                else {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy  hh:mm a");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(model.getOrder().getArriveDate());
                    genericViewHolder.offersListItemTvDeliveryDate.setText(formatter.format(calendar.getTime()) + " ");
                }

                if (type.equals(Constants.OFFER_STATUS_STARTED)) {
                    if (model.getBananaDelivery())
                        genericViewHolder.offersListItemBtnDelivered.setVisibility(View.GONE);
                    else
                        genericViewHolder.offersListItemBtnDelivered.setVisibility(View.VISIBLE);
                }
            }catch (Exception ignored){

            }
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
        void onClickDelivered(int position, Offer model);

        void onClickClientInfo(int position, Offer model);
    }

    private Offer getItem(int position) {
        return modelList.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.offers_list_item_tv_offer_num)
        TextView offersListItemTvOfferNum;
        @BindView(R.id.offers_list_item_tv_items)
        TextView offersListItemTvItems;
        @BindView(R.id.offers_list_item_tv_quantity)
        TextView offersListItemTvQuantity;
        @BindView(R.id.offers_list_item_tv_price)
        TextView offersListItemTvPrice;
        @BindView(R.id.offers_list_item_tv_address)
        TextView offersListItemTvAddress;
        @BindView(R.id.offers_list_item_tv_delivery_date)
        TextView offersListItemTvDeliveryDate;
        @BindView(R.id.offers_list_item_btn_delivered)
        Button offersListItemBtnDelivered;
        @BindView(R.id.offers_list_item_btn_client_info)
        Button offersListItemBtnClientInfo;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (type.equals(Constants.OFFER_STATUS_STARTED)) {
                offersListItemBtnDelivered.setVisibility(View.VISIBLE);
                offersListItemBtnClientInfo.setVisibility(View.VISIBLE);
            } else {
                offersListItemBtnDelivered.setVisibility(View.GONE);
                offersListItemBtnClientInfo.setVisibility(View.GONE);
            }
        }

        @OnClick({R.id.offers_list_item_btn_delivered, R.id.offers_list_item_btn_client_info})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.offers_list_item_btn_delivered:
                    mItemClickListener.onClickDelivered(getAdapterPosition(), modelList.get(getAdapterPosition()));
                    break;
                case R.id.offers_list_item_btn_client_info:
                    mItemClickListener.onClickClientInfo(getAdapterPosition(), modelList.get(getAdapterPosition()));
                    break;
            }
        }
    }
}
