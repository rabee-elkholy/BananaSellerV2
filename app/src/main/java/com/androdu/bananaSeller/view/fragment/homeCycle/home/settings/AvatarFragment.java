package com.androdu.bananaSeller.view.fragment.homeCycle.home.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.adapter.AvatarAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.androdu.bananaSeller.helper.Constants.sellerAvatars;

@SuppressLint("NonConstantResourceId")
public class AvatarFragment extends Fragment {

    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.app_bar_filter)
    ImageButton appBarFilter;
    @BindView(R.id.fragment_avatar_rv_recycler_view)
    RecyclerView fragmentAvatarRvRecyclerView;
    private OnClickListener mOnClickListener;

    public AvatarFragment() {
        // Required empty public constructor
    }

    public AvatarFragment(OnClickListener mOnClickListener) {
        // Required empty public constructor
        this.mOnClickListener = mOnClickListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_avatar, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        appBarTitle.setText(getString(R.string.choose_your_favorite_avatar));
        AvatarAdapter avatarAdapter = new AvatarAdapter(Arrays.asList(sellerAvatars));
        avatarAdapter.SetOnItemClickListener((position, model) -> {
            mOnClickListener.onClick(model, position);
            Log.d("avatar", "onItemClick: " + R.drawable.banana_logo + " " + model);
            requireActivity().onBackPressed();
        });

        fragmentAvatarRvRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        fragmentAvatarRvRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        fragmentAvatarRvRecyclerView.setAdapter(avatarAdapter);

    }

    @OnClick(R.id.app_bar_back)
    public void onViewClicked() {
        requireActivity().onBackPressed();
    }

    public interface OnClickListener {
        void onClick(Integer avatarId, Integer position);
    }
}