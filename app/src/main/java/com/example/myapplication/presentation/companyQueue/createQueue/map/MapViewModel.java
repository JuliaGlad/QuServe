package com.example.myapplication.presentation.companyQueue.createQueue.map;

import static com.example.myapplication.presentation.utils.constants.Utils.FINE_PERMISSION_CODE;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapViewModel extends ViewModel {

    GoogleMap googleMap;
    Location currentLocation;

    private final MutableLiveData<String> _addressLine = new MutableLiveData<>();
    LiveData<String> addressLine = _addressLine;

    private final MutableLiveData<String> _cityName = new MutableLiveData<>();
    LiveData<String> cityName = _cityName;

    public void searchForTheLocation(String location, Fragment fragment) {
        List<Address> addressList = null;

        if (location != null) {
            Geocoder geocoder = new Geocoder(fragment.requireContext());

            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                Log.e("IOEXCEPTION MAP", "Exception caught");
            }

            try {
                Address address = addressList.get(0);

                String addressLine = address.getAddressLine(0);
                _addressLine.postValue(addressLine);

                try {
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    googleMap.addMarker(new MarkerOptions().position(latLng));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                }catch (NullPointerException e){
                    Toast.makeText(fragment.requireContext(), "No place found" + e.getMessage(), Toast.LENGTH_LONG).show();;
                }

                } catch (IndexOutOfBoundsException exception) {

                Log.e("IndexOutOfBoundsException", "Exception caught");

                }
            }
        }

    public void setLastLocationMarker() {
        try {
            LatLng lastLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(lastLocation));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLocation, 10));
        } catch (NullPointerException e){
            Log.e("NullPointerException", e.getMessage());
        }
    }

    public void checkSelfPermission(Fragment fragment, Activity activity) {
        if (ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
        }
    }

    public Task<Location> getLastLocation(Fragment fragment, Activity activity) {

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(fragment.requireContext());

        checkSelfPermission(fragment, activity);

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        return (task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;

                Geocoder geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> listAddresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
                    if(null!=listAddresses&&listAddresses.size()>0){
                        String city = listAddresses.get(0).getLocality();
                        String currentAddressLine = listAddresses.get(0).getAddressLine(0);
                        _addressLine.postValue(currentAddressLine);
                        _cityName.postValue(city);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
    }
}