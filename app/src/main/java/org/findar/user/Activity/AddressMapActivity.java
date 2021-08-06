package org.findar.user.Activity;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.findar.user.Adapter.ConfirmAddress;
import org.findar.user.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddressMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    @SuppressLint("StaticFieldLeak")
    public static AddressMapActivity addressMapActivity;
    String state, country, address, city, postalCode, lat, lan, set_address;
    Double latd, land;

    private final static int LOCATION_REQUEST_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_address);

        set_address = getIntent().getStringExtra("SET_ADDRESS");


        addressMapActivity = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_REQUEST_CODE);
    }

    public void GotoNextPage() {
        lat = String.valueOf(latd);
        lan = String.valueOf(land);
        finish();
        Intent i = new Intent(AddressMapActivity.this, AddAddressActivity.class);
        i.putExtra("Address", String.valueOf(address));
        i.putExtra("country", String.valueOf(state));
        i.putExtra("postalcode", String.valueOf(postalCode));
        i.putExtra("lat", lat);
        i.putExtra("lan", lan);
        i.putExtra("SET_ADDRESS", set_address);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @SuppressLint({"MissingSuperCall", "MissingPermission"})
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    mMap.setOnMyLocationChangeListener(location -> {
                        LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                ltlng, 16f);
                        mMap.animateCamera(cameraUpdate);
                    });

                    mMap.setOnMapClickListener(latLng -> {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);

                        markerOptions.title(getAddress(latLng));
                        mMap.clear();
                        CameraUpdate location1 = CameraUpdateFactory.newLatLngZoom(
                                latLng, 15);
                        mMap.animateCamera(location1);
                        mMap.addMarker(markerOptions);
                    });
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private String getAddress(LatLng latLng) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality(); //district
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);
            DialogFragment dialogFragment = new ConfirmAddress();

            Bundle args = new Bundle();
            latd = latLng.latitude;
            land = latLng.longitude;
            args.putDouble("lat", latLng.latitude);
            args.putDouble("long", latLng.longitude);
            args.putString("address", address);
            args.putString("city", city);
            args.putString("state", state);
            args.putString("country", country);
            args.putString("postalCode", postalCode);
            dialogFragment.setArguments(args);
            dialogFragment.show(ft, "dialog");
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return "No Address Found";
        }
    }
}