package org.findar.user.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.findar.user.Adapter.ConfirmAddress;
import org.findar.user.Adapter.CustomRecyclearAdapterJobs;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.ClientJobs_GS;
import org.findar.user.Model.Companies;
import org.findar.user.Model.Potential_GS;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    String TAG = "MainActivity";
    private GoogleMap mMap;
    @SuppressLint("StaticFieldLeak")
    public static MainActivity mainActivity;
    String address, city, postalCode;
    Double latd, land;
    TextView txt_plus, set_location_map, txt_whats_location_continue, txt_hi_user;
    EditText et_whats_location;
    RelativeLayout welcome_with_plus, welcome_with_search_box;
    LinearLayout LL_List_address;
    ImageView img_top_arrow;
    RelativeLayout RL_overall;
    FrameLayout map;

    Activity activity;
    private DrawerLayout drawer;
    NavigationView nav_view;
    RecyclerView recycleViewPotential,recycleViewYourjob;
    CustomRecyclearAdapterJobs mAdapterJobs;
    RelativeLayout potentialLayout,yourjobLayout;
    LinearLayout linearWelcome;

    List<Companies> companiesList;
    TextView textClickMessage;

ArrayList<Potential_GS> potential_gsArrayList;
ArrayList<ClientJobs_GS> clientJobs_gsArrayList;
//    private final static int PLACE_PICKER_REQUEST = 999;
    private final static int LOCATION_REQUEST_CODE = 23;
    UICommon uicommon;
    MaterialButton nav_logoutbtn;
    TextView textViewName,textViewEmail,textViewVersion;
    ImageView imageViewProfile;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uicommon = new UICommon();


        activity = this;
        mainActivity = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        drawer = findViewById(R.id.drawer_layout);

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

        textClickMessage = findViewById(R.id.textClickMessage);

        recycleViewPotential = findViewById(R.id.recycleViewPotential);
        potentialLayout = findViewById(R.id.potentialLayout);
        linearWelcome = findViewById(R.id.linearWelcome);
        yourjobLayout = findViewById(R.id.yourjobLayout);
        recycleViewYourjob = findViewById(R.id.recycleViewYourjob);


//        txt_whats_location_continue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RL_overall.setVisibility(View.GONE);
//                map.setVisibility(View.GONE);
//                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new Set_Date_Time_Fragment()).addToBackStack( "HOME" ).commit();
//            }
//        });
//
//        et_whats_location.setOnClickListener(v -> {
//            img_top_arrow.setImageResource(R.drawable.top_close_arrow);
//            set_location_map.setVisibility(View.GONE);
//            LL_List_address.setVisibility(View.VISIBLE);
//        });

        txt_plus.setOnClickListener(v -> {
//            welcome_with_plus.setVisibility(View.GONE);
//            welcome_with_search_box.setVisibility(View.VISIBLE);
            Intent intent = new Intent(getApplicationContext(),Service_Types_Activity.class);
            startActivity(intent);

        });
//        NAV = findViewById(R.layout.nav_header);
//        LL_D_HOME = NAV.findViewById(R.id.LL_D_HOME);
//        LL_D_HOME.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
//            }
//        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_toggle);
        nav_view = findViewById(R.id.nav_view);
        nav_logoutbtn = findViewById(R.id.nav_logoutbtn);


       // setUpNavigationView();
        uicommon.setUpNavigationView(this,nav_view,drawer);

        nav_logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Log out0",Toast.LENGTH_LONG).show();
                uicommon.GotoLogout(MainActivity.this);
            }
        });

        GetProfiles(Constant.USER_ID);
        GetJobLists(Constant.USER_ID);


//        View navHeaderView= nav_view.inflateHeaderView(R.layout.nav_header);
//        textViewName= (TextView) navHeaderView.findViewById(R.id.textViewName);

        View headerView = nav_view.getHeaderView(0);
        textViewName = (TextView) headerView.findViewById(R.id.textViewName);
        textViewEmail = (TextView) headerView.findViewById(R.id.textViewEmail);
        imageViewProfile  = (ImageView) headerView.findViewById(R.id.imageViewProfile);

        textViewVersion = (TextView) headerView.findViewById(R.id.textViewVersion);

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            String version = pInfo.versionName;
            textViewVersion.setText(getResources().getString(R.string.version) + " : "+ version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
       // textViewName.setText("Your Text Here");
      //  System.out.println("TExtView Object"+textViewName);

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

    @SuppressLint("NonConstantResourceId")
    private void setUpNavigationView() {
        nav_view.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_order_history:
                    Intent intent1 = new Intent(MainActivity.this, OrderHistoryActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.nav_reviews:
                    Intent intent2 = new Intent(MainActivity.this, ReviewsActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.nav_my_profile:
                    Intent intent3 = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(intent3);
                    return true;
                case R.id.nav_settings:
                    Intent intent4 = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent4);
                    return true;
                case R.id.nav_legal:
                    Intent intent5 = new Intent(MainActivity.this, LegalActivity.class);
                    startActivity(intent5);
                    return true;
                case R.id.nav_logout:
                    Log.d("HAHAHA", "hi1");
                  //  GotoLogout();
                    return true;
                default:
            }
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
            }
            return true;

        });
    }


    private String getAddress(LatLng latLng) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality(); //district
//            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();

            postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName(); //street road
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
                        Constant.Home_Postal = result_profile.getString("postal_code1");
                        Constant.Home_ID = result_profile.getString("addressid1");
                        Constant.Home_Address = result_profile.getString("address1");
                        Constant.Home_Province_ID = result_profile.getString("provinceid1");
                        Constant.Home_Province_Name = result_profile.getString("province1");
                        Constant.Home_City_ID = result_profile.getString("cityid1");
                        Constant.Home_City_Name = result_profile.getString("city1");
                        Constant.Home_Suburb_ID = result_profile.getString("suburbid1");
                        Constant.Home_Suburb_Name = result_profile.getString("suburb1");
                        Constant.WORK_Postal = result_profile.getString("postal_code2");
                        Constant.WORK_ID = result_profile.getString("addressid2");
                        Constant.WORK_Address = result_profile.getString("address2");
                        Constant.WORK_Province_Name = result_profile.getString("province2");
                        Constant.WORK_City_Name = result_profile.getString("city2");
                        Constant.WORK_Suburb_Name = result_profile.getString("suburb2");


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

                        txt_hi_user.setText(getResources().getString(R.string.hi)+" "+Constant.USER_F_NAME);
                        textViewName.setText(Constant.USER_F_NAME);
                        textViewEmail.setText(Constant.USER_EMAIL);

                        if(!Constant.USER_P_FILE.equalsIgnoreCase(""))
                            Picasso.get()
                                    .load(Constant.USER_P_FILE)
                                    .fit()
                                    .into(imageViewProfile);



                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_Profile" + e);
                }
            }
        }, activity, Constant.GET_USER_DETAILS, params, true);
    }


    /**************************** Get Job List ***************************/

    @SuppressLint("SetTextI18n")
    private void GetJobLists(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);
        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    potential_gsArrayList = new ArrayList<Potential_GS>();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Job_Lists" + jsonObject);
                    String status = jsonObject.getString("status");
//                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        JSONObject result_job = jsonObject.getJSONObject("result");
                        JSONArray potential_jobs = result_job.getJSONArray("potential_jobs");
                        Log.d(TAG, "Result_Job_Lists_potential_jobs" + potential_jobs);
                        Log.d(TAG, "Result_Job_Lists_potential_jobs" + potential_jobs.length());
                        if(potential_jobs.length()!=0) {
                            for (int i = 0; i < potential_jobs.length(); i++) {
                                Potential_GS potential_gs = new Potential_GS();
                                JSONObject jobRecord = potential_jobs.getJSONObject(i);
                                System.out.println("Result_JobID" + jobRecord.getString("job_id"));
                                potential_gs.setJob_id(jobRecord.getString("job_id"));
                                potential_gs.setServices(jobRecord.getString("services"));
                                potential_gs.setProblems(jobRecord.getString("problems"));
                                potential_gs.setSubproblems(jobRecord.getString("subproblems"));

                                //  String companies = jobRecord.getString("companies");
                                JSONArray result_job_companies = jobRecord.getJSONArray("companies");
                                System.out.println("Result Job Company" + jobRecord.getJSONArray("companies"));
                                System.out.println("Result Job Company Length ::" + result_job_companies.length());
                                companiesList = new ArrayList<Companies>();

                                if (result_job_companies.length() != 0) {
                                    for (int j = 0; j < result_job_companies.length(); j++) {
                                        JSONObject companyRecord = result_job_companies.getJSONObject(j);
                                        Companies company = new Companies();
                                        company.setTitle("0");
                                        company.setCompanyId(companyRecord.getString("company_id"));
                                        company.setCompanyName(companyRecord.getString("company_name"));
                                        company.setCompanyprofileimage(companyRecord.getString("company_profile_image"));
                                        company.setStarrating(companyRecord.getString("star_rating"));
                                        company.setJobdatetime(companyRecord.getString("job_date_time"));
                                        company.setCalloutprice(companyRecord.getString("call_out_price"));
                                        company.setPriceperhour(companyRecord.getString("price_per_hour"));
                                        company.setAvailabletime(companyRecord.getString("available_time"));
                                        companiesList.add(company);
                                        potential_gs.setCompaniesList(companiesList);
                                    }
                                } else {
                                    Companies company = new Companies();
                                    company.setTitle("0");
                                    company.setCompanyId("");
                                    company.setCompanyName("");
                                    company.setCompanyprofileimage("");
                                    company.setStarrating("");
                                    company.setJobdatetime("");
                                    company.setCalloutprice("");
                                    company.setPriceperhour("");
                                    companiesList.add(company);
                                    potential_gs.setCompaniesList(companiesList);
                                }
                                potential_gsArrayList.add(potential_gs);
                            }

                            System.out.println("Potential Job Ends ::"+potential_gsArrayList.size());
                            Constant.POTENTIAL_END = potential_gsArrayList.size();
                           // System.out.println("Potential Your Job Starts ::"+potential_gsArrayList.size()+1);
                            JSONArray client_jobs = result_job.getJSONArray("client_jobs");

                            Log.d(TAG, "Result_Job_Lists_client_jobs" + client_jobs);
                            Log.d(TAG, "Result_Job_Lists_client_jobs" + client_jobs.length());

                            if(client_jobs.length()!=0) {
                                for (int i = 0; i < client_jobs.length(); i++) {
                                    Potential_GS clientjob_gs = new Potential_GS();
                                    JSONObject jobRecord = client_jobs.getJSONObject(i);
                                    System.out.println("Result_JobID" + jobRecord.getString("job_id"));
                                    clientjob_gs.setJob_id(jobRecord.getString("job_id"));
                                    clientjob_gs.setServices(jobRecord.getString("services"));
                                    clientjob_gs.setProblems(jobRecord.getString("problems"));
                                    clientjob_gs.setSubproblems(jobRecord.getString("subproblems"));

                                    JSONArray result_job_companies = jobRecord.getJSONArray("companies");
                                    System.out.println("Result Job Company" + jobRecord.getJSONArray("companies"));
                                    System.out.println("Result Job Company Length ::" + result_job_companies.length());
                                    companiesList = new ArrayList<Companies>();

                                    if (result_job_companies.length() != 0) {
                                        for (int j = 0; j < result_job_companies.length(); j++) {
                                            JSONObject companyRecord = result_job_companies.getJSONObject(j);
                                            Companies company = new Companies();
                                            company.setTitle("1");
                                            company.setCompanyId(companyRecord.getString("company_id"));
                                            company.setCompanyName(companyRecord.getString("company_name"));
                                            company.setCompanyprofileimage(companyRecord.getString("company_profile_image"));
                                            company.setStarrating(companyRecord.getString("star_rating"));
                                            company.setJobdatetime(companyRecord.getString("job_date_time"));
                                            company.setCalloutprice(companyRecord.getString("call_out_price"));
                                            company.setPriceperhour(companyRecord.getString("price_per_hour"));
                                            company.setAvailabletime(companyRecord.getString("available_time"));
                                            companiesList.add(company);
                                            clientjob_gs.setCompaniesList(companiesList);
                                        }
                                    }
                                    potential_gsArrayList.add(clientjob_gs);
                                }
                            }

                            System.out.println("Potential Your Job Ends ::"+potential_gsArrayList.size());

                            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            );
                            params1.setMargins(0, 300, 0, 0);
                            welcome_with_plus.setLayoutParams(params1);
                            recycleViewPotential.setVisibility(View.VISIBLE);
                            potentialLayout.setVisibility(View.VISIBLE);
                            linearWelcome.setVisibility(View.VISIBLE);
                            textClickMessage.setVisibility(View.GONE);
                            recycleViewPotential.setHasFixedSize(true);
                            LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(this);
                            recycleViewPotential.setLayoutManager(layoutManagerSubProblems);
                            mAdapterJobs = new CustomRecyclearAdapterJobs(MainActivity.this, potential_gsArrayList);
                            recycleViewPotential.setAdapter(mAdapterJobs);
                        }
                        else
                        {
                            recycleViewPotential.setVisibility(View.GONE);
                            potentialLayout.setVisibility(View.GONE);
                            linearWelcome.setVisibility(View.VISIBLE);
                        }


                        ////////////////////// Client Jobs /////////////////////
                       /* clientJobs_gsArrayList = new ArrayList<ClientJobs_GS>();
                        JSONArray client_jobs = result_job.getJSONArray("client_jobs");
                        Log.d(TAG, "Result_Job_Lists_client_jobs" + client_jobs);
                        Log.d(TAG, "Result_Job_Lists_client_jobs" + client_jobs.length());

                        if(client_jobs.length()!=0) {
                            for (int i = 0; i < client_jobs.length(); i++) {
                                ClientJobs_GS clientjob_gs = new ClientJobs_GS();
                                JSONObject jobRecord = client_jobs.getJSONObject(i);
                                System.out.println("Result_JobID" + jobRecord.getString("job_id"));
                                clientjob_gs.setJob_id(jobRecord.getString("job_id"));
                                clientjob_gs.setServices(jobRecord.getString("services"));
                                clientjob_gs.setProblems(jobRecord.getString("problems"));
                                clientjob_gs.setSubproblems(jobRecord.getString("subproblems"));

                                JSONArray result_job_companies = jobRecord.getJSONArray("companies");
                                System.out.println("Result Job Company" + jobRecord.getJSONArray("companies"));
                                System.out.println("Result Job Company Length ::" + result_job_companies.length());
                                companiesList = new ArrayList<Companies>();

                                if (result_job_companies.length() != 0) {
                                    for (int j = 0; j < result_job_companies.length(); j++) {
                                        JSONObject companyRecord = result_job_companies.getJSONObject(j);
                                        Companies company = new Companies();
                                        company.setCompanyId(companyRecord.getString("company_id"));
                                        company.setCompanyName(companyRecord.getString("company_name"));
                                        company.setCompanyprofileimage(companyRecord.getString("company_profile_image"));
                                        company.setStarrating(companyRecord.getString("star_rating"));
                                        company.setJobdatetime(companyRecord.getString("job_date_time"));
                                        company.setCalloutprice(companyRecord.getString("call_out_price"));
                                        company.setPriceperhour(companyRecord.getString("price_per_hour"));
                                        company.setAvailabletime(companyRecord.getString("available_time"));
                                        companiesList.add(company);
                                        clientjob_gs.setCompaniesList(companiesList);
                                    }
                                }
                                clientJobs_gsArrayList.add(clientjob_gs);

                                recycleViewYourjob.setVisibility(View.VISIBLE);
                                yourjobLayout.setVisibility(View.VISIBLE);
                                LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(this);
                                recycleViewYourjob.setLayoutManager(layoutManagerSubProblems);
                                CustomAdapterCompanies  mAdapterCompanies = new CustomAdapterCompanies(mainActivity, clientJobs_gsArrayList,jobRecord.getString("job_id"));
                                recycleViewYourjob.setAdapter(mAdapterCompanies);
                            }
                        }*/
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "Result_JobList" + e);
                }
            }
        }, activity, Constant.JOBLIST, params, true);
    }
}