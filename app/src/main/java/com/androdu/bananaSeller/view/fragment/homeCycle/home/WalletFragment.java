package com.androdu.bananaSeller.view.fragment.homeCycle.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.WalletAdapter;
import com.androdu.bananaSeller.data.model.response.wallet.WalletResponse;
import com.androdu.bananaSeller.data.model.response.wallet.WalletTransaction;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.OnEndLess;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;
import static com.androdu.bananaSeller.helper.HelperMethod.hideView;
import static com.androdu.bananaSeller.helper.HelperMethod.showView;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class WalletFragment extends Fragment {

    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_wallet_tv_my_balance)
    TextView fragmentWalletTvMyBalance;
    @BindView(R.id.fragment_wallet_rv_recycler_view)
    RecyclerView fragmentWalletRvRecyclerView;
    @BindView(R.id.fragment_wallet_load_more)
    RelativeLayout loadMore;
    @BindView(R.id.fragment_wallet_list_pb_progress)
    ProgressBar fragmentWalletListPbProgress;
    @BindView(R.id.fragment_wallet_list_tv_msg)
    TextView fragmentWalletListTvMsg;
    @BindView(R.id.fragment_wallet_tv_current_balance)
    TextView fragmentWalletTvCurrentBalance;
    @BindView(R.id.fragment_wallet_tv_binding_balance)
    TextView fragmentWalletTvBindingBalance;
    @BindView(R.id.fragment_wallet_btn_pull_balance)
    Button fragmentWalletBtnPullBalance;
    private View view;
    private String checkoutId;
    private List<WalletTransaction> walletTransactions;
    private WalletAdapter walletAdapter;

    private OnEndLess onEndLess;
    private Integer maxPage = 0;

    public WalletFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        ButterKnife.bind(this, view);
        init();

        getWallet(1);

        return view;
    }

    private void init() {
        appBarTitle.setText(R.string.my_wallet);
        walletTransactions = new ArrayList<>();
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        fragmentWalletRvRecyclerView.setLayoutManager(linearLayout);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        getWallet(current_page);
                        disappearKeypad(getActivity());
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentWalletRvRecyclerView.addOnScrollListener(onEndLess);

        walletAdapter = new WalletAdapter(getActivity(), walletTransactions);
        fragmentWalletRvRecyclerView.setAdapter(walletAdapter);

    }

    @OnClick({R.id.app_bar_back, R.id.fragment_wallet_btn_pull_balance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_wallet_btn_pull_balance:
                Intent intent = new Intent(getContext(), SecondHomeActivity.class);
                intent.putExtra("id", 14);
                startActivity(intent);
                break;
        }
    }

    private void getWallet(int page) {
        if (isConnected(getContext())) {
            if (page == 1)
                showView(fragmentWalletListPbProgress);
            else
                showView(loadMore);
            getClient().getMyTransactions(loadDataString(getActivity(), TOKEN), page)
                    .enqueue(new Callback<WalletResponse>() {
                        @Override
                        public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                            hideView(fragmentWalletListPbProgress, loadMore);

                            if (response.isSuccessful()) {
                                onGetWalletSuccess(response.body(), page);
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<WalletResponse> call, Throwable t) {
                            hideView(fragmentWalletListPbProgress, loadMore);

                            Log.d("error_handler", "onFailure: " + t.getMessage());
                            t.printStackTrace();

//                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    private void onGetWalletSuccess(WalletResponse walletResponse, int page) {
        fragmentWalletTvMyBalance.setText(walletResponse.getWallet().getWallet() + "");
        fragmentWalletTvCurrentBalance.setText((walletResponse.getWallet().getWallet() - walletResponse.getWallet().getWallet()) + "");
        fragmentWalletTvBindingBalance.setText(walletResponse.getWallet().getBindingWallet() + "");

        if (page == 1) {
            initOnEndLess();
            walletTransactions.clear();
        }

        if (walletResponse.getTotal() != 0) {
            fragmentWalletListTvMsg.setText("");
            walletTransactions.addAll(walletResponse.getData());
            maxPage = (walletResponse.getTotal() + 10 - 1) / 10;
        } else {
            walletTransactions.addAll(new ArrayList<>());
            fragmentWalletListTvMsg.setText(R.string.no_transactions_found);
        }
        walletAdapter.notifyDataSetChanged();
    }

    private void initOnEndLess() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (walletTransactions != null)
            getWallet(1);
    }
}