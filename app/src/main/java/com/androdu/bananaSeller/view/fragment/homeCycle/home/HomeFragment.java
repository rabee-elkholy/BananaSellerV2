package com.androdu.bananaSeller.view.fragment.homeCycle.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.response.SellerFieldsResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.view.activity.SecondHomeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_LOGGED;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataBoolean;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;
import static com.androdu.bananaSeller.view.fragment.homeCycle.home.ProductsFragment.baked_goods;
import static com.androdu.bananaSeller.view.fragment.homeCycle.home.ProductsFragment.fish_and_meat;
import static com.androdu.bananaSeller.view.fragment.homeCycle.home.ProductsFragment.food;
import static com.androdu.bananaSeller.view.fragment.homeCycle.home.ProductsFragment.fruits_and_vegetables;


public class HomeFragment extends Fragment {

    @BindView(R.id.h1)
    ImageView h1;
    @BindView(R.id.h3)
    ImageView h3;
    @BindView(R.id.h2)
    ImageView h2;
    @BindView(R.id.h4)
    ImageView h4;
    @BindView(R.id.b1)
    View b1;
    @BindView(R.id.b2)
    View b2;
    @BindView(R.id.b3)
    View b3;
    @BindView(R.id.b4)
    View b4;
    private View view;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        if (loadDataBoolean(getActivity(), USER_LOGGED))
            getFields();

        return view;
    }

    private void getFields() {
        if (isConnected(getContext())) {
            getClient().getFields(loadDataString(getActivity(), TOKEN))
                    .enqueue(new Callback<SellerFieldsResponse>() {
                        @Override
                        public void onResponse(Call<SellerFieldsResponse> call, Response<SellerFieldsResponse> response) {
                            if (response.isSuccessful()) {
                                List<String> myFields = response.body().getData();

                                if (!myFields.contains("F-V"))
                                    b1.setVisibility(View.VISIBLE);
                                if (!myFields.contains("F-M"))
                                    b3.setVisibility(View.VISIBLE);
                                if (!myFields.contains("B"))
                                    b2.setVisibility(View.VISIBLE);
                                if (!myFields.contains("F"))
                                    b4.setVisibility(View.VISIBLE);
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<SellerFieldsResponse> call, Throwable t) {
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });

        }
    }

    @OnClick({R.id.h1, R.id.h3, R.id.h2, R.id.h4})
    public void onViewClicked(View view) {
        Intent intent = new Intent(getActivity(), SecondHomeActivity.class);
        intent.putExtra("id", 16);
        switch (view.getId()) {
            case R.id.h1:
                intent.putExtra("category", fruits_and_vegetables);
                intent.putExtra("type", 2);
                break;
            case R.id.h2:
                intent.putExtra("category", baked_goods);
                intent.putExtra("type", 3);
                break;
            case R.id.h3:
                intent.putExtra("category", fish_and_meat);
                intent.putExtra("type", 4);
                break;
            case R.id.h4:
                intent.putExtra("category", food);
                intent.putExtra("type", 5);
                break;
        }
        startActivity(intent);
    }

}