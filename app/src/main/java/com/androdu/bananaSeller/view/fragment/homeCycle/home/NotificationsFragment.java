package com.androdu.bananaSeller.view.fragment.homeCycle.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.NotificationAdapter;
import com.androdu.bananaSeller.data.model.response.notifications.Notification;
import com.androdu.bananaSeller.data.model.response.notifications.NotificationsResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.OnEndLess;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.hideView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showView;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class NotificationsFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.app_bar_filter)
    ImageButton appBarFilter;
    @BindView(R.id.fragment_notifications_rv_recycler_view)
    RecyclerView fragmentNotificationsRvRecyclerView;
    @BindView(R.id.fragment_notifications_srl_swipe)
    SwipeRefreshLayout fragmentNotificationsSrlSwipe;
    @BindView(R.id.fragment_notifications_load_more)
    RelativeLayout loadMore;
    @BindView(R.id.fragment_notifications_pb_progress_bar)
    ProgressBar fragmentNotificationsPbProgressBar;
    @BindView(R.id.fragment_notifications_tv_msg)
    TextView fragmentNotificationsTvMsg;
    @BindView(R.id.app_bar_settings)
    ImageButton appBarSettings;
    private View view;

    private ArrayList<Notification> notifications;
    private OnEndLess onEndLess;
    private Integer maxPage = 0;
    private NotificationAdapter notificationAdapter;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);

        init();
        getNotifications(1);

        return view;
    }

    private void init() {
        appBarTitle.setText(R.string.notifications);
        appBarBack.setVisibility(View.GONE);
        appBarSettings.setVisibility(View.VISIBLE);

        notifications = new ArrayList<>();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        fragmentNotificationsRvRecyclerView.setLayoutManager(linearLayout);


        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getNotifications(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentNotificationsRvRecyclerView.addOnScrollListener(onEndLess);

        notificationAdapter = new NotificationAdapter(getActivity(), notifications);
        fragmentNotificationsRvRecyclerView.setAdapter(notificationAdapter);
        notificationAdapter.SetOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, Notification model) {
//                switch (model.getData().getKey()){
//                    case "2":
//                        Intent intent = new Intent(getContext(), SecondHomeActivity.class);
//                        intent.putExtra("id", 7);
//                        startActivity(intent);
//                        break;
//                }
            }
        });
        fragmentNotificationsSrlSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNotifications(1);
                fragmentNotificationsSrlSwipe.setRefreshing(false);
            }
        });

    }


    private void getNotifications(int page) {
        if (isConnected(getContext())) {
            if (page == 1)
                showView(fragmentNotificationsPbProgressBar);
            else
                showView(loadMore);
            getClient().getNotifications(loadDataString(getActivity(), TOKEN), page)
                    .enqueue(new Callback<NotificationsResponse>() {
                        @Override
                        public void onResponse(Call<NotificationsResponse> call, Response<NotificationsResponse> response) {
                            hideView(fragmentNotificationsPbProgressBar, loadMore);

                            if (response.isSuccessful()) {
                                onGetNotificationsSuccess(response.body(), page);

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<NotificationsResponse> call, Throwable t) {
                            hideView(fragmentNotificationsPbProgressBar, loadMore);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }

    }

    private void onGetNotificationsSuccess(NotificationsResponse body, int page) {
        if (page == 1) {
            initOnEndLess();
            notifications.clear();
        }

        if (body.getTotal() != 0) {
            fragmentNotificationsTvMsg.setText("");
            notifications.addAll(body.getData());
            maxPage = (body.getTotal() + 10 - 1) / 10;
        } else {
            notifications.addAll(new ArrayList<>());
            fragmentNotificationsTvMsg.setText(R.string.no_notifications_found);
        }
        notificationAdapter.notifyDataSetChanged();
    }

    private void initOnEndLess() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
    }

    @OnClick(R.id.app_bar_settings)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), SecondHomeActivity.class);
        intent.putExtra("id", 11);

        startActivity(intent);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if (offersList != null) {
//            getNotifications(1);
//        }
//    }
}