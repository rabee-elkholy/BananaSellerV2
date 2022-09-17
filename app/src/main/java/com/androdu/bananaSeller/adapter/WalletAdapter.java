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
import com.androdu.bananaSeller.data.model.response.wallet.WalletTransaction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final List<WalletTransaction> modelList;

    public WalletAdapter(List<WalletTransaction> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wallet_transaction_list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolder) {
            final WalletTransaction model = getItem(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            if (model.getAction().equals("pay")) {
                viewHolder.walletTransactionTvAmount.setText(String.format("- %s", model.getAmount()));
                viewHolder.walletTransactionTvType.setText(R.string.withdrawal);
            }else if (model.getAction().equals("deposit")){
                viewHolder.walletTransactionTvAmount.setText(String.format("+ %s", model.getAmount()));
                viewHolder.walletTransactionTvType.setText(R.string.deposit);
            } else{
                viewHolder.walletTransactionTvAmount.setText(String.format("+ %s", model.getAmount()));
                viewHolder.walletTransactionTvType.setText(R.string.refund);
            }
            viewHolder.walletTransactionTvDate.setText(model.getCreatedAt().split("T")[0]);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private WalletTransaction getItem(int position) {
        return modelList.get(position);
    }

    @SuppressLint("NonConstantResourceId")
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.wallet_transaction_tv_amount)
        TextView walletTransactionTvAmount;
        @BindView(R.id.wallet_transaction_tv_type)
        TextView walletTransactionTvType;
        @BindView(R.id.wallet_transaction_tv_date)
        TextView walletTransactionTvDate;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
