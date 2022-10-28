package com.guillermocode.localizationcoarse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //initialize variable

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assing variable


        //initialize fusedlocationproviderclient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            getLocation();


        } else {
            ActivityCompat.requestPermissions(MainActivity.this
                    , new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 44);
            getLocation();
        }


    }

    private void getLocation() {
        Log.e("esta ","vivo");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //initialize location
                    Location location = task.getResult();
                    if (location != null) {
                        //initialize geocoder

                        //Initialize address list
                        try {
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            List<Address> address = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            //Set Latitude on Log
                            Log.e("Latitude: ", String.valueOf(address.get(0).getLatitude()));

                            //Set Logitude on Log
                            Log.e("Longitude: ", String.valueOf(address.get(0).getLongitude()));

                            //Set Country name on log
                            Log.e("Country Name: ", String.valueOf(address.get(0).getCountryName()));
                            Log.e("Country Locality: ", String.valueOf(address.get(0).getLocality()));
                            Log.e("Country Address: ", String.valueOf(address.get(0).getAddressLine(0)));



                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }

    };
}