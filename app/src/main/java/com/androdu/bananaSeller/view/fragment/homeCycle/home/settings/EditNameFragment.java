package com.androdu.bananaSeller.view.fragment.homeCycle.home.settings;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.model.requestBody.EditNameRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.api.ApiService.getClient;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.LoadDataInt;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_AVATAR;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_NAME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataInt;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataString;
import static com.androdu.bananaSeller.helper.Constants.sellerAvatars;
import static com.androdu.bananaSeller.helper.HelperMethod.disableView;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.enableView;
import static com.androdu.bananaSeller.helper.HelperMethod.replaceFragment;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showSuccessDialogCloseFragment;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

public class EditNameFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_edit_name_et_name)
    EditText fragmentEditNameEtName;
    @BindView(R.id.fragment_edit_name_btn_confirm)
    Button fragmentEditNameBtnConfirm;
    @BindView(R.id.fragment_edit_name_civ_image)
    CircleImageView fragmentEditNameCivImage;
    private View view;

    private Integer avatarPosition;

    public EditNameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_name, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.edit_your_name));
        fragmentEditNameEtName.setHint(loadDataString(getActivity(), USER_NAME));
        if (avatarPosition == null)
            avatarPosition = LoadDataInt(getActivity(), USER_AVATAR);
        Log.d("avatar", "init: " + avatarPosition);
        fragmentEditNameCivImage.setImageResource(sellerAvatars[avatarPosition]);

    }

    @OnClick({R.id.app_bar_back, R.id.fragment_edit_name_btn_confirm, R.id.fragment_edit_name_civ_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_edit_name_civ_image:
                replaceFragment(getParentFragmentManager(),
                        R.id.activity_second_home_container,
                        new AvatarFragment(new AvatarFragment.OnClickListener() {
                            @Override
                            public void onClick(Integer avatar, Integer position) {
                                avatarPosition = position;
                            }
                        }),
                        true);
                break;
            case R.id.fragment_edit_name_btn_confirm:
                if (fragmentEditNameEtName.getText().toString().isEmpty())
                    fragmentEditNameEtName.setError(getString(R.string.enter_name));
                else
                    editName();
                break;
        }
    }

    private void editName() {
        if (isConnected(getContext())) {
            disableView(fragmentEditNameBtnConfirm);
            showProgressDialog(getActivity());
            getClient().editName(loadDataString(getActivity(), TOKEN), new EditNameRequestBody(fragmentEditNameEtName.getText().toString(), avatarPosition))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();
                            enableView(fragmentEditNameBtnConfirm);
                            if (response.isSuccessful()) {
                                showSuccessDialogCloseFragment(getActivity(), getString(R.string.done));
                                saveDataString(getActivity(), USER_NAME, fragmentEditNameEtName.getText().toString());
                                saveDataInt(getActivity(), USER_AVATAR, avatarPosition);
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(getActivity(), response);
                            }
                        }

                        @Override
                        public void onFailure(Call<GeneralResponse> call, Throwable t) {
                            dismissProgressDialog();
                            enableView(fragmentEditNameBtnConfirm);
                            showErrorDialog(getActivity(), t.getMessage());
                        }
                    });
        }
    }

}