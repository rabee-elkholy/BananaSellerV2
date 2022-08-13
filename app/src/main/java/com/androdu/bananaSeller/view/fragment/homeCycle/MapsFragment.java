package com.androdu.bananaSeller.view.fragment.homeCycle;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.androdu.bananaSeller.R;
import com.androdu.bananaSeller.helper.HelperMethod;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsFragment extends Fragment
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private LatLng location;
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location myLocation;
    Marker myCurrLocationMarker;
    Marker mySecLocationMarker;
    MarkerOptions myMarkerOptions;

    LatLng selectedLocation;
    @BindView(R.id.app_bar_back)
    ImageView appBarBack;
    @BindView(R.id.app_bar_title)
    TextView appBarTitle;
    @BindView(R.id.fragment_maps_fab_confirm)
    FloatingActionButton fragmentMapsFabConfirm;
    private View view;
    private boolean showOnly = false;
    private AlertDialog alertDialog;

//    @Override
//    public void onResume() {
//        super.onResume();
//
//        setUpMapIfNeeded();
//    }
//
//    private void setUpMapIfNeeded() {
//
//        if (mGoogleMap == null) {
//            getMapAsync(this);
//        }
//    }


    public MapsFragment() {
    }

    public MapsFragment(boolean showOnly, LatLng location) {
        this.showOnly = showOnly;
        this.location = location;
    }

    public MapsFragment(AddLocationListener addLocationListener) {
        this.mAddLocationListener = addLocationListener;
    }

    private AddLocationListener mAddLocationListener;

    public interface AddLocationListener {
        void onPickLocation(LatLng latLng);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (showOnly) {
            fragmentMapsFabConfirm.setVisibility(View.GONE);
            appBarTitle.setText(R.string.order_location);
            myMarkerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(location.latitude, location.longitude);
            myMarkerOptions.position(latLng);
            myMarkerOptions.title(getString(R.string.order_location));
            myMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            mGoogleMap.addMarker(myMarkerOptions).showInfoWindow();
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));


        } else {
            //Initialize Google Play Services
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //Location Permission already granted
                    buildGoogleApiClient();
                    mGoogleMap.setMyLocationEnabled(true);


                } else {
                    //Request Location Permission
                    checkLocationPermission();
                }
            } else {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            }

            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    onLocationChange(latLng);
                }
            });

            mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (checkLocationEnabled()) {
                            zoomInToMyLocation();
                        }
                    } else {
                        checkLocationPermission();
                    }
                    return false;
                }
            });

            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    selectedLocation = marker.getPosition();
                    Log.d("debugging", "onMarkerClick: " + marker.getTitle());
                    return false;
                }
            });
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("debugging", "onConnected: ");
        myMarkerOptions = new MarkerOptions();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.d("debugging", "onMyLocationChanged: ");
                    //Place current location marker
                    if (myCurrLocationMarker == null) {
                        myMarkerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
                        myMarkerOptions.title(getString(R.string.your_location));
                        myMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        myCurrLocationMarker = mGoogleMap.addMarker(myMarkerOptions);
                        myCurrLocationMarker.showInfoWindow();
                        selectedLocation = myCurrLocationMarker.getPosition();
                    } else {
                        myCurrLocationMarker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                    }

                }
            });

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private void onLocationChange(LatLng latLng) {
        if (mySecLocationMarker != null) {
            mySecLocationMarker.remove();
        }

        //Place current location marker
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(getString(R.string.selected_position));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mySecLocationMarker = mGoogleMap.addMarker(markerOptions);
        mySecLocationMarker.showInfoWindow();
        selectedLocation = mySecLocationMarker.getPosition();


        Log.d("debugging", "onLocationChange: " + latLng.toString());

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme)
                        .setTitle(getString(R.string.location_permission_needed))
                        .setMessage(getString(R.string.need_location_permission))
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                Dexter.withContext(getActivity())
                                        .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                                        .withListener(new MultiplePermissionsListener() {
                                            @Override
                                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                                if (report.areAllPermissionsGranted()) {
                                                    buildGoogleApiClient();
                                                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                                    }
                                                    mGoogleMap.setMyLocationEnabled(true);
                                                }

                                            }

                                            @Override
                                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                                token.continuePermissionRequest();
                                            }
                                        }).check();

                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                Dexter.withContext(getActivity())
                        .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    buildGoogleApiClient();
                                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                    }
                                    mGoogleMap.setMyLocationEnabled(true);
                                }

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_maps, container, false);
        ButterKnife.bind(this, view);
        appBarTitle.setText(getString(R.string.add_location));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }


    @OnClick({R.id.app_bar_back, R.id.fragment_maps_fab_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.app_bar_back:
                getActivity().onBackPressed();
                break;
            case R.id.fragment_maps_fab_confirm:
                if (selectedLocation == null) {
                    HelperMethod.showErrorDialog(getActivity(), getString(R.string.mark_your_location));
                } else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("lat", selectedLocation.latitude);
                    returnIntent.putExtra("long", selectedLocation.longitude);
                    getActivity().setResult(Activity.RESULT_OK, returnIntent);
                    getActivity().finish();
                }
                break;
        }
    }

//    private void showAddFavDialog() {
//        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//
//        final View v = layoutInflater.inflate(R.layout.add_location_dialog, null);
//        alertDialog = new AlertDialog.Builder(getContext()).create();
//        alertDialog.setCancelable(false);
//
//
//        final EditText tvDetails = (EditText) v.findViewById(R.id.tv_details);
//        final EditText tvName = (EditText) v.findViewById(R.id.tv_name);
//        final EditText tvPhone = (EditText) v.findViewById(R.id.tv_phone);
//        final TextView tvConfirm = (TextView) v.findViewById(R.id.tv_confirm);
//        final TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);
//
//        tvConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (addLocation(getActivity(), tvDetails, tvName, tvPhone)) {
//                    addNewLocation(tvDetails.getText().toString().trim(),
//                            tvName.getText().toString().trim(),
//                            tvPhone.getText().toString().trim());
//                }
//            }
//        });
//
//        tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//
//        alertDialog.setView(v);
//        alertDialog.show();
//    }
//
//    private void addNewLocation(String address, String name, String phone) {
//        if (isConnected(getContext())) {
//            showProgressDialog(getActivity());
//            getClient().addNewLocation(loadDataString(getActivity(), TOKEN),
//                    new AddLocationRequestBody(selectedLocation.latitude,
//                            selectedLocation.longitude,
//                            address,
//                            name,
//                            phone))
//                    .enqueue(new Callback<GeneralResponse>() {
//                        @Override
//                        public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
//                            dismissProgressDialog();
//
//                            if (response.isSuccessful()) {
//                                showSuccessDialogCloseFragment(getActivity(), getString(R.string.successfully_added));
//                                if (mAddLocationListener != null)
//                                    mAddLocationListener.onAddLocation();
//                                alertDialog.dismiss();
//                            } else {
//                                Log.d("error_handler", "onResponse: " + response.message());
//                                ApiErrorHandler.showErrorMessage(getActivity(), response);
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<GeneralResponse> call, Throwable t) {
//                            dismissProgressDialog();
//                            showErrorDialog(getActivity(), t.getMessage());
//                        }
//                    });
//
//        } else {
//            showErrorDialog(getActivity(), getString(R.string.no_internet), getString(R.string.check_internet));
//        }
//    }

    private boolean checkLocationEnabled() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme)
                    .setMessage(R.string.gps_network_not_enabled)
                    .setPositiveButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            getActivity().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
            return false;
        }
        return true;
    }

    private void zoomInToMyLocation() {
        if (myMarkerOptions != null) {
            if (myMarkerOptions.getPosition() != null) {
                LatLng myLocationLatLng = myMarkerOptions.getPosition();
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLocationLatLng.latitude, myLocationLatLng.longitude), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(myLocationLatLng.latitude, myLocationLatLng.longitude))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the tilt of the camera to 30 degrees
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)
                        .build();                   // Creates a CameraPosition from the builder
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }
}