package org.findar.user.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Adapter.ConfirmAddress;
import org.findar.user.Adapter.CustomRecyclerAdapterAddress;
import org.findar.user.Fragment.Set_Date_Time_Fragment;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.Address_GS;

import org.findar.user.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    String TAG = "MainActivity";
    private GoogleMap mMap;
    @SuppressLint("StaticFieldLeak")
    public static MapActivity mapActivity;
    String address, city, state,postalCode;
    Double latd, land;
    TextView txt_plus, set_location_map, txt_whats_location_continue, txt_hi_user;
    TextView et_whats_location;
    RelativeLayout welcome_with_plus, welcome_with_search_box;
    LinearLayout LL_List_address;
    ImageView img_top_arrow;
    RelativeLayout RL_overall;
    FrameLayout map,fragment_container;

    Activity activity;
    private DrawerLayout drawer;
    NavigationView nav_view;
    RecyclerView recycleViewAddress;

    List<Address_GS> addressList;

    //    private final static int PLACE_PICKER_REQUEST = 999;
    private final static int LOCATION_REQUEST_CODE = 23;
    UICommon uicommon;
    MaterialButton nav_logoutbtn;
    CustomRecyclerAdapterAddress mAdapterAddress;

    TextView textView_Name;
    ImageView back_arrow;
    TextView txt_setAddress,text_whatAddress;

    LinearLayout linear_setAddress,linear_welcome;
    String getSetAddress="";
    public static MapActivity instance;
    DialogFragment dialogFragment;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        uicommon = new UICommon();

        instance = this;
        activity = this;
        mapActivity = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setTitle("");
//        toolbar.setSubtitle("");
//        drawer = findViewById(R.id.drawer_layout);

//        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION )
//                != PackageManager.PERMISSION_GRANTED ) {

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_REQUEST_CODE);
//        }
        String apiKey = getString(R.string.google_api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
        // Create a new Places client instance.
//        PlacesClient placesClient = Places.createClient(this);
//        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
//                getSupportFragmentManager().findFragmentById(R.id.map1);

//        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
//
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                Log.i("QWERTY", "Place: " + place.getName() + ", " + place.getId());
//            }
//
//            @Override
//            public void onError(Status status) {
//                Log.i("QWERTY", "An error occurred: " + status);
//            }
//        });


        txt_plus = findViewById(R.id.txt_plus);
        welcome_with_plus = findViewById(R.id.welcome_with_plus);
        welcome_with_search_box = findViewById(R.id.welcome_with_search_box);
        LL_List_address = findViewById(R.id.LL_List_address);
        set_location_map = findViewById(R.id.set_location_map);
        et_whats_location = findViewById(R.id.et_whats_location);
        img_top_arrow = findViewById(R.id.img_top_arrow);
        txt_whats_location_continue = findViewById(R.id.txt_whats_location_continue);
        RL_overall = findViewById(R.id.RL_overall);
        txt_hi_user = findViewById(R.id.txt_hi_user);
        map = findViewById(R.id.map);
        recycleViewAddress = findViewById(R.id.recycleViewAddress);
        back_arrow = findViewById(R.id.back_arrow);



        textView_Name = findViewById(R.id.textView_Name);
        linear_setAddress = findViewById(R.id.linear_setAddress);
        linear_welcome = findViewById(R.id.linear_welcome);

        text_whatAddress = findViewById(R.id.text_whatAddress);
        txt_setAddress = findViewById(R.id.txt_setAddress);
        fragment_container = findViewById(R.id.fragment_container);

        txt_whats_location_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RL_overall.setVisibility(View.GONE);
//                map.setVisibility(View.GONE);
//                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new Set_Date_Time_Fragment()).addToBackStack( "HOME" ).commit();

                if(!getSetAddress.equalsIgnoreCase("")) {

                    linear_welcome.setVisibility(View.GONE);
                    linear_setAddress.setVisibility(View.VISIBLE);

                    recycleViewAddress.setVisibility(View.GONE);
                    txt_whats_location_continue.setVisibility(View.GONE);
                    set_location_map.setVisibility(View.VISIBLE);
                    text_whatAddress.setText(getSetAddress);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),R.string.error_address,Toast.LENGTH_LONG).show();
                }
            }
        });

        et_whats_location.setOnClickListener(v -> {
           /* img_top_arrow.setImageResource(R.drawable.top_close_arrow);
            set_location_map.setVisibility(View.GONE);
            LL_List_address.setVisibility(View.VISIBLE);*/
          //  addressList = new ArrayList<>();
//            for (int i = 0; i < 2; i++) {
//                Address_GS address = new Address_GS();
//                try {
//                    address.setAD_ID("ID "+i);
//                    address.setAD_NAME("Home "+i);
//                    address.setAD_LOCATION("Address "+i);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                addressList.add(address);
//            }
if(addressList!=null) {
    recycleViewAddress.setVisibility(View.VISIBLE);
    recycleViewAddress.setHasFixedSize(true);
    LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(this);
    recycleViewAddress.setLayoutManager(layoutManagerSubProblems);
    mAdapterAddress = new CustomRecyclerAdapterAddress(MapActivity.this, addressList);
    recycleViewAddress.setAdapter(mAdapterAddress);
    txt_whats_location_continue.setVisibility(View.VISIBLE);
    set_location_map.setVisibility(View.GONE);
}
        });

        txt_plus.setOnClickListener(v -> {
//            welcome_with_plus.setVisibility(View.GONE);
//            welcome_with_search_box.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getApplicationContext(),Service_Types_Activity.class);
            startActivity(intent);

        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // activity.finish();
                if(recycleViewAddress.getVisibility()==View.VISIBLE) {
                    recycleViewAddress.setVisibility(View.GONE);
                    txt_whats_location_continue.setVisibility(View.GONE);
                    set_location_map.setVisibility(View.VISIBLE);

                    linear_welcome.setVisibility(View.GONE);
                    linear_setAddress.setVisibility(View.VISIBLE);
                }
                else
                {
                    finish();
                }

            }
        });


        txt_setAddress.setOnClickListener(v->{
           // Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                RL_overall.setVisibility(View.GONE);
                map.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new Set_Date_Time_Fragment()).addToBackStack( "HOME" ).commit();
        });

//        txt_setAddress.setOnClickListener(v -> {
//
//        });
//        NAV = findViewById(R.layout.nav_header);
//        LL_D_HOME = NAV.findViewById(R.id.LL_D_HOME);
//        LL_D_HOME.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
//            }
//        });
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_toggle);
//        nav_view = findViewById(R.id.nav_view);
    //    nav_logoutbtn = findViewById(R.id.nav_logoutbtn);

        // setUpNavigationView();
      //  uicommon.setUpNavigationView(this,nav_view,drawer);

//        nav_logoutbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(getApplicationContext(),"Log out0",Toast.LENGTH_LONG).show();
//                uicommon.GotoLogout(MapActivity.this);
//            }
//        });

        GetProfiles(Constant.USER_ID);
    }

    public static MapActivity getInstance() {
        return instance;
    }


//    public void GotoNextPage() {
//        lat = String.valueOf(latd);
//        lan = String.valueOf(land);
//        SharedPreferences Get_CHECK_ADD = getApplicationContext().getSharedPreferences("Amvegon", MODE_PRIVATE);
//        String Get_CHECK_ADD_id = Get_CHECK_ADD.getString("CHECK_ADD", "");
//        if (Get_CHECK_ADD_id.equals("1")) {
//            Intent i = new Intent(MainActivity.this, AddMainActivity.class);
//            i.putExtra("Address", String.valueOf(address));
//            i.putExtra("city", String.valueOf(city));
//            i.putExtra("postalcode", String.valueOf(postalCode));
//            i.putExtra("lat", lat);
//            i.putExtra("lan", lan);
//            SharedPreferences Check_add_clear = getSharedPreferences("Amvegon", Context.MODE_PRIVATE);
//            SharedPreferences.Editor Check_add_cleareditor = Check_add_clear.edit();
//            Check_add_cleareditor.putString("CHECK_ADD", "");
//            Check_add_cleareditor.commit();
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(i);
//        } else  if (Get_CHECK_ADD_id.equals("2")) {
//            Intent i = new Intent(MainActivity.this, ProfileMainActivity.class);
//            i.putExtra("Address", String.valueOf(address));
//            i.putExtra("city", String.valueOf(city));
//            i.putExtra("postalcode", String.valueOf(postalCode));
//            i.putExtra("lat", lat);
//            i.putExtra("lan", lan);
//            SharedPreferences Check_add_clear = getSharedPreferences("Amvegon", Context.MODE_PRIVATE);
//            SharedPreferences.Editor Check_add_cleareditor = Check_add_clear.edit();
//            Check_add_cleareditor.putString("CHECK_ADD", "");
//            Check_add_cleareditor.commit();
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(i);
//        }

//    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mMap.setOnMyLocationChangeListener(location -> {
                        LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                ltlng, 16f);
                        mMap.animateCamera(cameraUpdate);
                    });
//                    Location location = mMap.getMyLocation();

                    mMap.setOnMapClickListener(latLng -> {
                        Log.d("latLng++", String.valueOf(latLng));
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


    public void GotoNextPage() {
        dialogFragment.dismiss();
        String lat = String.valueOf(latd);
        String lan = String.valueOf(land);
        Intent i = new Intent(MapActivity.this, AddAddressActivity.class);
        i.putExtra("Address", String.valueOf(address));
        i.putExtra("country", String.valueOf(state));
        i.putExtra("postalcode", String.valueOf(postalCode));
        i.putExtra("lat", lat);
        i.putExtra("lan", lan);
        i.putExtra("SET_ADDRESS", "");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


    private String getAddress(LatLng latLng) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            System.out.println("Address "+address);

            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality(); //district
            state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();

            postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName(); //street road
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {

                ft.remove(prev);
            }
            ft.addToBackStack(null);
            dialogFragment = new ConfirmAddress();

            Bundle args = new Bundle();
            latd = latLng.latitude;
            land = latLng.longitude;
            args.putDouble("lat", latLng.latitude);
            args.putDouble("long", latLng.longitude);
            args.putString("address", address);
            args.putString("city", city);
            args.putString("postalCode", postalCode);
            dialogFragment.setArguments(args);
            dialogFragment.show(ft, "dialog");
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return "No Address Found";

        }


    }



    //    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.nav_message:
//                break;
//            case R.id.nav_chat:
//                break;
//            case R.id.nav_profile:
//                break;
//            case R.id.nav_share:
//                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.nav_send:
//                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            RL_overall.setVisibility(View.VISIBLE);
            map.setVisibility(View.VISIBLE);
        } else {
            RL_overall.setVisibility(View.VISIBLE);
            map.setVisibility(View.VISIBLE);
            super.onBackPressed();
        }
    }
    @SuppressLint("SetTextI18n")
    private void GetProfiles(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Profile" + jsonObject);

                    String status = jsonObject.getString("status");
//                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        JSONObject result_profile = jsonObject.getJSONObject("result");
                        Constant.USER_DETAIL_ID = result_profile.getString("usersdetailid");
                        Constant.USER_EMAIL = result_profile.getString("email");
                        Constant.USER_DOB = result_profile.getString("dob");
                        Constant.USER_MOBILE = result_profile.getString("mobile_phone");
                        Constant.USER_F_NAME = result_profile.getString("name");
                        Constant.USER_L_NAME = result_profile.getString("surname");
                        Constant.USER_P_FILE = result_profile.getString("file");

                        addressList = new ArrayList<>();
                        Address_GS Homeaddress = new Address_GS();
                        Homeaddress.setAd_TITLE("Home");
                        Homeaddress.setIMAGEID(R.drawable.icon_home);
                        Constant.Home_Postal = result_profile.getString("postal_code1");
                        Homeaddress.setAD_POSTALCODE(Constant.Home_Postal);
                        Constant.Home_ID = result_profile.getString("addressid1");
                        Homeaddress.setAD_ADDRESSID(Constant.Home_ID);
                        Constant.Home_Address = result_profile.getString("address1");
                        Homeaddress.setAD_ADDRESS(Constant.Home_Address );
                        Constant.Home_Province_ID = result_profile.getString("provinceid1");
                        Homeaddress.setPROVINCEID(Constant.Home_Province_ID);
                        Constant.Home_Province_Name = result_profile.getString("province1");
                        Homeaddress.setPROVICE(Constant.Home_Province_Name);
                        Constant.Home_City_ID = result_profile.getString("cityid1");
                        Homeaddress.setCITYID(Constant.Home_City_ID);
                        Constant.Home_City_Name = result_profile.getString("city1");
                        Homeaddress.setCITY(Constant.Home_City_Name);
                        Constant.Home_Suburb_ID = result_profile.getString("suburbid1");
                        Homeaddress.setSUBURBID(Constant.Home_Suburb_ID);
                        Constant.Home_Suburb_Name = result_profile.getString("suburb1");
                        Homeaddress.setSUBURB(Constant.Home_Suburb_Name);
                        addressList.add(Homeaddress);

                        Address_GS Workaddress = new Address_GS();
                        Workaddress.setAd_TITLE("Work");
                        Workaddress.setIMAGEID(R.drawable.icon_work);
                        Constant.WORK_Postal = result_profile.getString("postal_code2");
                        Workaddress.setAD_POSTALCODE(Constant.WORK_Postal);
                        Constant.WORK_ID = result_profile.getString("addressid2");
                        Workaddress.setAD_ADDRESSID(Constant.WORK_ID);
                        Constant.WORK_Address = result_profile.getString("address2");
                        Workaddress.setAD_ADDRESS(Constant.WORK_Address);
                        Constant.WORK_Province_Name = result_profile.getString("province2");
                        Workaddress.setPROVICE(Constant.WORK_Province_Name);
                        Constant.WORK_City_Name = result_profile.getString("city2");
                        Homeaddress.setCITY(Constant.WORK_City_Name);
                        Constant.WORK_Suburb_Name = result_profile.getString("suburb2");
                        Homeaddress.setSUBURB(Constant.WORK_Suburb_Name);
                        addressList.add(Workaddress);

                        if (Constant.USER_P_FILE.equals("null")) {
                            Constant.USER_P_FILE = "";
                            Constant.USER_P_FILE_NAME = "";
//                            Picasso.get().load(Constant.USER_P_FILE).into(img_et_profile);
                        }
                        if (Constant.Home_ID.equals("null")) {
                            Constant.Home_ID = "";
                        }
                        if (Constant.Home_Address.equals("null")) {
                            Constant.Home_Address = "";
                        }
                        if (Constant.Home_Province_Name.equals("null")) {
                            Constant.Home_Province_Name = "";
                        }
                        if (Constant.Home_City_Name.equals("null")) {
                            Constant.Home_City_Name = "";
                        }
                        if (Constant.Home_Suburb_Name.equals("null")) {
                            Constant.Home_Suburb_Name = "";
                        }
                        if (Constant.Home_Postal.equals("null")) {
                            Constant.Home_Postal = "";
                        }
                        if (Constant.WORK_ID.equals("null")) {
                            Constant.WORK_ID = "";
                        }
                        if (Constant.WORK_Address.equals("null")) {
                            Constant.WORK_Address = "";
                        }
                        if (Constant.WORK_Province_Name.equals("null")) {
                            Constant.WORK_Province_Name = "";
                        }
                        if (Constant.WORK_City_Name.equals("null")) {
                            Constant.WORK_City_Name = "";
                        }
                        if (Constant.WORK_Suburb_Name.equals("null")) {
                            Constant.WORK_Suburb_Name = "";
                        }
                        if (Constant.WORK_Postal.equals("null")) {
                            Constant.WORK_Postal = "";
                        }

                        txt_hi_user.setText(getResources().getString(R.string.hi)+" "+Constant.USER_F_NAME+" "+Constant.USER_L_NAME);
                        textView_Name.setText("Hi " +Constant.USER_F_NAME);



                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_Profile" + e);
                }
            }
        }, activity, Constant.GET_USER_DETAILS, params, true);
    }

    public void gotoSetAddress(String address){
        this.getSetAddress = address;
        getLocationFromAddress(this, address);

        linear_welcome.setVisibility(View.GONE);
        linear_setAddress.setVisibility(View.VISIBLE);

        recycleViewAddress.setVisibility(View.GONE);
        txt_whats_location_continue.setVisibility(View.GONE);
        set_location_map.setVisibility(View.VISIBLE);
        text_whatAddress.setText(address);


        if(!getSetAddress.equalsIgnoreCase("")) {

            linear_welcome.setVisibility(View.GONE);
            linear_setAddress.setVisibility(View.VISIBLE);

            recycleViewAddress.setVisibility(View.GONE);
            txt_whats_location_continue.setVisibility(View.GONE);
            set_location_map.setVisibility(View.VISIBLE);
            text_whatAddress.setText(getSetAddress);
        }
        else
        {
            Toast.makeText(getApplicationContext(),R.string.error_address,Toast.LENGTH_LONG).show();
        }

        //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new Set_Date_Time_Fragment()).addToBackStack( "HOME" ).commit();
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            System.out.println("***** Latitude "+location.getLatitude() + " ~ "+location.getLongitude());
            Constant.LATITUDE = String.valueOf(location.getLatitude());
            Constant.LONGITUDE = String.valueOf(location.getLongitude());
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }
}

