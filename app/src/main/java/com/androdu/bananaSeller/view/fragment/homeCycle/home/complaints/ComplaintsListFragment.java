package com.androdu.bananaSeller.view.fragment.homeCycle.home.complaints;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.ComplaintsAdapter;
import com.androdu.bananaSeller.data.model.response.complaints.Complaint;
import com.androdu.bananaSeller.data.model.response.complaints.ComplaintsResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.Constants;
import com.androdu.bananaSeller.helper.OnEndLess;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.ApiErrorHandler.showErrorMessage;
import static com.androdu.bananaSeller.helper.HelperMethod.disappearKeypad;
import static com.androdu.bananaSeller.helper.HelperMethod.hideView;
import static com.androdu.bananaSeller.helper.HelperMethod.replaceFragment;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showView;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

@SuppressLint("NonConstantResourceId")
public class ComplaintsListFragment extends Fragment {


    @BindView(R.id.fragment_complaints_list_rv_recycler_view)
    RecyclerView fragmentComplaintsListRvRecyclerView;
    @BindView(R.id.fragment_complaints_list_srl_swipe)
    SwipeRefreshLayout fragmentComplaintsListSrlSwipe;
    @BindView(R.id.fragment_complaints_load_more)
    RelativeLayout loadMore;
    @BindView(R.id.fragment_complaints_list_pb_progress)
    ProgressBar fragmentComplaintsListPbProgress;
    @BindView(R.id.fragment_complaints_list_tv_msg)
    TextView fragmentComplaintsListTvMsg;

    private String type;
    private ArrayList<Complaint> complaints;
    private ComplaintsAdapter complaintsAdapter;

    private OnEndLess onEndLess;
    private Integer maxPage = 0;

    public ComplaintsListFragment() {
        // Required empty public constructor
    }

    public ComplaintsListFragment(String type) {
        this.type = type;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaints_list, container, false);
        ButterKnife.bind(this, view);
        init();
        getComplains(1);
        return view;
    }

    private void init() {

        complaints = new ArrayList<>();

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        fragmentComplaintsListRvRecyclerView.setLayoutManager(linearLayout);


        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        getComplains(current_page);
                        disappearKeypad(requireActivity());
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentComplaintsListRvRecyclerView.addOnScrollListener(onEndLess);

        complaintsAdapter = new ComplaintsAdapter(getActivity(), complaints);
        fragmentComplaintsListRvRecyclerView.setAdapter(complaintsAdapter);
        complaintsAdapter.SetOnItemClickListener((position, model) -> replaceFragment(getParentFragmentManager(),
                R.id.activity_second_home_container,
                new ComplaintDetailsFragment(model, type),
                true));
        fragmentComplaintsListSrlSwipe.setOnRefreshListener(() -> {
            getComplains(1);
            fragmentComplaintsListSrlSwipe.setRefreshing(false);
        });

    }

    private void getComplains(int page) {
        if (isConnected(getContext())) {
            if (page == 1)
                showView(fragmentComplaintsListPbProgress);
            else
                showView(loadMore);
            getClient().getComplaints(loadDataString(getActivity(), TOKEN), page, type)
                    .enqueue(new Callback<ComplaintsResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<ComplaintsResponse> call, @NonNull Response<ComplaintsResponse> response) {
                            hideView(fragmentComplaintsListPbProgress, loadMore);

                            if (response.isSuccessful()) {
                                onGetComplaintsSuccess(response.body(), page);

                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                showErrorMessage(requireActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ComplaintsResponse> call, @NonNull Throwable t) {
                            hideView(fragmentComplaintsListPbProgress, loadMore);
                            Log.d("error_handler", "onFailure: " + t.getMessage());
                            t.printStackTrace();

                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void onGetComplaintsSuccess(ComplaintsResponse complaintsResponse, int page) {

        if (page == 1) {
            initOnEndLess();
            complaints.clear();
        }

        if (complaintsResponse.getTotal() != 0) {
            fragmentComplaintsListTvMsg.setText("");
            complaints.addAll(complaintsResponse.getData());
            maxPage = (complaintsResponse.getTotal() + 10 - 1) / 10;
        } else {
            complaints.addAll(new ArrayList<>());
            fragmentComplaintsListTvMsg.setText(R.string.no_complaints_found);
        }
        complaintsAdapter.notifyDataSetChanged();
    }

    private void initOnEndLess() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        getComplains(1);
//    }
}