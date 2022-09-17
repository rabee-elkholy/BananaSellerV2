package com.androdu.bananaSeller.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.data.api.ApiService;
import com.androdu.bananaSeller.data.local.SharedPreferencesManger;
import com.androdu.bananaSeller.data.model.requestBody.LogoutRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.helper.ApiErrorHandler;
import com.androdu.bananaSeller.helper.Constants;
import com.androdu.bananaSeller.helper.HelperMethod;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.HomeFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.NotificationsFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.OrdersFragment;
import com.androdu.bananaSeller.view.fragment.homeCycle.home.WalletFragment;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.FCM;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.FIRST_TIME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.LoadDataInt;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.TOKEN;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_AVATAR;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.USER_NAME;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadDataString;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.loadUserData;
import static com.androdu.bananaSeller.data.local.SharedPreferencesManger.saveDataString;
import static com.androdu.bananaSeller.helper.HelperMethod.disableView;
import static com.androdu.bananaSeller.helper.HelperMethod.dismissProgressDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.enableView;
import static com.androdu.bananaSeller.helper.HelperMethod.showErrorDialog;
import static com.androdu.bananaSeller.helper.HelperMethod.showProgressDialog;
import static com.androdu.bananaSeller.helper.NetworkState.isConnected;

@SuppressLint("NonConstantResourceId")
public class HomeActivity extends BaseActivity {

    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.home_activity_dl_drawer_layout)
    DrawerLayout homeActivityDlDrawerLayout;
    @BindView(R.id.activity_home_iv_nav_home)
    ImageView activityHomeIvNavHome;
    @BindView(R.id.activity_home_iv_nav_orders)
    ImageView activityHomeIvNavOrders;
    @BindView(R.id.activity_home_iv_nav_cart)
    ImageView activityHomeIvNavCart;
    @BindView(R.id.activity_home_iv_nav_notification)
    ImageView activityHomeIvNavNotification;
    @BindView(R.id.activity_home_iv_nav_more)
    ImageView activityHomeIvNavMore;

    private View drawerView;

    private int currentPage = 1;
    private View logoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initDrawer();
        openFragment(new HomeFragment());
    }

    private void initDrawer() {
        drawerView = navView.inflateHeaderView(R.layout.nav_header_drawer);

        navView.bringToFront();
        homeActivityDlDrawerLayout.requestLayout();

        ((ImageView) drawerView.findViewById(R.id.drawer_header_civ_image)).setImageResource(Constants.sellerAvatars[LoadDataInt(this, USER_AVATAR)]);

        ((TextView) drawerView.findViewById(R.id.drawer_header_tv_name)).setText(loadDataString(this, USER_NAME));

        drawerView.findViewById(R.id.drawer_header_lin_my_offers).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SecondHomeActivity.class);
            intent.putExtra("id", 1);
            startActivity(intent);
        });

        drawerView.findViewById(R.id.drawer_header_lin_settings).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SecondHomeActivity.class);
            intent.putExtra("id", 5);
            startActivity(intent);
        });
        drawerView.findViewById(R.id.drawer_header_lin_about).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SecondHomeActivity.class);
            intent.putExtra("id", 6);
            startActivity(intent);
        });
        drawerView.findViewById(R.id.drawer_header_lin_complaints).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SecondHomeActivity.class);
            intent.putExtra("id", 7);
            startActivity(intent);
        });
        logoutBtn = drawerView.findViewById(R.id.drawer_header_lin_logout);
        logoutBtn.setOnClickListener(v -> logout());

        homeActivityDlDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        // This method will trigger on item Click of navigation menu
        navView.setNavigationItemSelectedListener(menuItem -> {

            //Checking if the item is in checked state or not, if not make it in checked state
            menuItem.setChecked(!menuItem.isChecked());

            return true;
        });

    }


    private void logout() {
        if (isConnected(this)) {
            disableView(logoutBtn);
            showProgressDialog(this);
            ApiService.getClient().logout(loadDataString(this, TOKEN),
                    new LogoutRequestBody(loadDataString(this, FCM)))
                    .enqueue(new Callback<GeneralResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<GeneralResponse> call, Response<GeneralResponse> response) {
                            dismissProgressDialog();
                            enableView(logoutBtn);
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(HomeActivity.this, SplashActivity.class);
                                startActivity(intent);
                                SharedPreferencesManger.clean(HomeActivity.this);
                                saveDataString(HomeActivity.this, FIRST_TIME, "sss");
                                finish();
                            } else {
                                Log.d("error_handler", "onResponse: " + response.message());
                                ApiErrorHandler.showErrorMessage(HomeActivity.this, response);
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<GeneralResponse> call, Throwable t) {
                            dismissProgressDialog();
                            enableView(logoutBtn);
                            showErrorDialog(HomeActivity.this, t.getMessage());
                        }
                    });
        }
    }


    private void openFragment(Fragment fragment) {
        HelperMethod.replaceFragment(getSupportFragmentManager(),
                R.id.activity_home_container,
                fragment,
                false);
    }

    private void resetNavIcons() {
        float density = getResources().getDisplayMetrics().density;
        int padding = (int) (density * 8);
        activityHomeIvNavHome.setImageResource(R.drawable.ic_home);
        activityHomeIvNavHome.setPaddingRelative(padding, padding, padding, padding);
        activityHomeIvNavOrders.setImageResource(R.drawable.ic_orders);
        activityHomeIvNavOrders.setPaddingRelative(padding, padding, padding, padding);
        activityHomeIvNavCart.setImageResource(R.drawable.ic_wallet2);
        activityHomeIvNavCart.setPaddingRelative(padding, padding, padding, padding);
        activityHomeIvNavNotification.setImageResource(R.drawable.ic_notification);
        activityHomeIvNavNotification.setPaddingRelative(padding, padding, padding, padding);
    }

    @OnClick({R.id.activity_home_iv_nav_home, R.id.activity_home_iv_nav_orders, R.id.activity_home_iv_nav_cart, R.id.activity_home_iv_nav_notification, R.id.activity_home_iv_nav_more})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.activity_home_iv_nav_home:
                if (currentPage != 1) {
                    resetNavIcons();
                    openFragment(new HomeFragment());
                    activityHomeIvNavHome.setImageResource(R.drawable.ic_home_active);
                    activityHomeIvNavHome.setPaddingRelative(0, 0, 0, 0);
                    currentPage = 1;
                }
                break;
            case R.id.activity_home_iv_nav_orders:
                if (currentPage != 2) {
                    resetNavIcons();
                    openFragment(new OrdersFragment());
                    activityHomeIvNavOrders.setImageResource(R.drawable.ic_orders_active);
                    activityHomeIvNavOrders.setPaddingRelative(0, 0, 0, 0);
                    currentPage = 2;
                }
                break;
            case R.id.activity_home_iv_nav_cart:
                if (currentPage != 3) {
                    resetNavIcons();
                    openFragment(new WalletFragment());
                    activityHomeIvNavCart.setImageResource(R.drawable.ic_wallet_active);
                    activityHomeIvNavCart.setPaddingRelative(0, 0, 0, 0);
                    currentPage = 3;
                }
                break;
            case R.id.activity_home_iv_nav_notification:
                if (currentPage != 4) {
                    resetNavIcons();
                    openFragment(new NotificationsFragment());
                    activityHomeIvNavNotification.setImageResource(R.drawable.ic_notification_active);
                    activityHomeIvNavNotification.setPaddingRelative(0, 0, 0, 0);
                    currentPage = 4;
                }
                break;
            case R.id.activity_home_iv_nav_more:
                ((TextView) drawerView.findViewById(R.id.drawer_header_tv_name)).setText(loadDataString(this, USER_NAME));

                if (homeActivityDlDrawerLayout.isDrawerOpen(GravityCompat.START))
                    homeActivityDlDrawerLayout.closeDrawer(GravityCompat.START);
                else
                    homeActivityDlDrawerLayout.openDrawer(GravityCompat.START);
                break;

        }
    }


    @Override
    public void onBackPressed() {
        if (homeActivityDlDrawerLayout.isDrawerOpen(GravityCompat.START))
            homeActivityDlDrawerLayout.closeDrawer(GravityCompat.START);
        else if (!homeActivityDlDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            if (currentPage == 1)
                super.onBackPressed();
            else {
                resetNavIcons();
                openFragment(new HomeFragment());
                activityHomeIvNavHome.setImageResource(R.drawable.ic_home_active);
                activityHomeIvNavHome.setPaddingRelative(0, 0, 0, 0);
                currentPage = 1;
            }
        }
    }

}