package com.androdu.bananaSeller.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.orders.Order;
import com.androdu.bananaSeller.data.model.response.orders.OrderProduct;
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
import static java.lang.String.format;

public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final Activity activity;
    private final List<Order> modelList;
    private OnItemClickListener mItemClickListener;

    public OrdersAdapter(Activity activity, List<Order> modelList) {
        this.activity = activity;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.orders_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final Order model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.ordersListItemTvOrderNum.setText(model.getOrderDetails().getId());

            genericViewHolder.ordersListItemTvQuantity.setText("");
            genericViewHolder.ordersListItemTvOrderProducts.setText("");
            String local = getLanguagePref(activity);
            String productsStr = "";
            String unitsStr = "";

            for (OrderProduct product : model.getOrderDetails().getProducts()) {
                if (local.equals(LANGUAGE_KEY_ARABIC) || local.equals(LANGUAGE_KEY_URDU)) {
                    if (product.getPath().equals("clientProducts")) {
                        productsStr = productsStr.concat(product.getOrderProductDetails().getName() + " , ");
                    } else {
                        if (local.equals(LANGUAGE_KEY_ARABIC))
                            productsStr = productsStr.concat(product.getOrderProductDetails().getNameAr() + " , ");
                        else
                            productsStr = productsStr.concat(product.getOrderProductDetails().getNameUr() + " , ");

                    }
                } else
                    productsStr = productsStr.concat(product.getOrderProductDetails().getNameEn() + " , ");

                int unitIndex = Arrays.asList(Constants.units).indexOf(product.getUnit());
                unitsStr = unitsStr.concat(product.getAmount() + " " + (activity.getResources().getStringArray(R.array.weights))[unitIndex] + " , ");
            }

            genericViewHolder.ordersListItemTvOrderProducts.setText(productsStr.substring(0, productsStr.length() - 2));
            genericViewHolder.ordersListItemTvQuantity.setText(unitsStr.substring(0, unitsStr.length() - 2));


            if (model.isSellerOffered())
                genericViewHolder.ordersListItemBtnAction.setVisibility(View.GONE);
            else
                genericViewHolder.ordersListItemBtnAction.setVisibility(View.VISIBLE);


            genericViewHolder.ordersListItemTvClientOrders.setText(format("%d %s %d", model.getClient().getEndedClientOrders(), activity.getString(R.string.from), model.getClient().getTotalClientOrders()));

            if (model.getDistance() >= 1000) {
                genericViewHolder.ordersListItemTvDistance.setText(format("%d%s", ((int) model.getDistance()) / 1000, activity.getString(R.string.km)));
            } else {
                genericViewHolder.ordersListItemTvDistance.setText(String.format("%d%s", (int) model.getDistance(), activity.getString(R.string.m)));
            }

            if (model.getOrderDetails().getArriveDate() == 0)
                genericViewHolder.ordersListItemTvDeliveryDate.setText(R.string.direct_delivery);
            else {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(model.getOrderDetails().getArriveDate());
                genericViewHolder.ordersListItemTvDeliveryDate.setText(formatter.format(calendar.getTime()));
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
        void onClickAction(int position, Order model);
    }

    private Order getItem(int position) {
        return modelList.get(position);
    }

    @SuppressLint("NonConstantResourceId")
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orders_list_item_tv_order_num)
        TextView ordersListItemTvOrderNum;
        @BindView(R.id.orders_list_item_tv_client_orders)
        TextView ordersListItemTvClientOrders;
        @BindView(R.id.orders_list_item_tv_order_products)
        TextView ordersListItemTvOrderProducts;
        @BindView(R.id.orders_list_item_tv_quantity)
        TextView ordersListItemTvQuantity;
        @BindView(R.id.orders_list_item_tv_distance)
        TextView ordersListItemTvDistance;
        @BindView(R.id.orders_list_item_tv_delivery_date)
        TextView ordersListItemTvDeliveryDate;
        @BindView(R.id.orders_list_item_btn_action)
        Button ordersListItemBtnAction;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            if (type.equals(ORDER_STATUS_CURRENT)) {
//                ordersListItemBtnAction.setBackground(activity.getResources().getDrawable(R.drawable.button_shape_red));
//                ordersListItemBtnAction.setText(activity.getString(R.string.cancel_order));
//            } else if (type.equals(ORDER_STATUS_COMPLETED)) {
//                ordersListItemBtnAction.setBackground(activity.getResources().getDrawable(R.drawable.button_shape));
//                ordersListItemBtnAction.setText(activity.getString(R.string.make_complaint));
//            } else
//                ordersListItemBtnAction.setVisibility(View.GONE);
        }

        @OnClick(R.id.orders_list_item_btn_action)
        public void onViewClicked() {
            mItemClickListener.onClickAction(getAdapterPosition(), modelList.get(getAdapterPosition()));
        }
    }
}
