package org.findar.user.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.findar.user.Adapter.CircleTransform;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;

import org.findar.user.Helper.UICommon;
import org.findar.user.R;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {

    String TAG = "TAG_MyProfileActivity";
    Activity activity;

    NavigationView nav_view;
    public ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    TextView txt_my_name, txt_my_email, txt_my_phone, txt_my_home_address, txt_my_work_address;
    CircularImageView img_my_profile;
    String Home_address, Work_address;
    ImageView profile_edit;
    MaterialButton nav_logoutbtn;
    UICommon uicommon;
    TextView textViewName,textViewEmail,textViewVersion;
    ImageView imageViewProfile;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        activity = MyProfileActivity.this;

        uicommon = new UICommon();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_toggle);
        nav_view = findViewById(R.id.nav_view);
        txt_my_name = findViewById(R.id.txt_my_name);
        txt_my_email = findViewById(R.id.txt_my_email);
        txt_my_phone = findViewById(R.id.txt_my_phone);
        txt_my_home_address = findViewById(R.id.txt_my_home_address);
        txt_my_work_address = findViewById(R.id.txt_my_work_address);
        img_my_profile = findViewById(R.id.img_my_profile);
        profile_edit = findViewById(R.id.profile_edit);
        profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, MyProfileUpdateActivity.class);
                startActivity(intent);
            }
        });
       // setUpNavigationView();

        nav_logoutbtn = findViewById(R.id.nav_logoutbtn);


        // setUpNavigationView();
        uicommon.setUpNavigationView(this,nav_view,drawer);

        nav_logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Log out0",Toast.LENGTH_LONG).show();
                uicommon.GotoLogout(MyProfileActivity.this);
            }
        });


        SharedPreferences GetUser_id = getApplicationContext().getSharedPreferences("Findar", MODE_PRIVATE);
        Constant.USER_ID = GetUser_id.getString("USER_ID", "");
        GetProfile(Constant.USER_ID);

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
    }

    private void GetProfile(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Profile My Profile Activity" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if(status.equals("1")){
                        JSONObject result_profile = jsonObject.getJSONObject("result");
                        String email = result_profile.getString("email");
                        String mobile_phone = result_profile.getString("mobile_phone");
                        String first_name = result_profile.getString("name");
                        String last_name = result_profile.getString("surname");
                        String file = result_profile.getString("file");
                        String postal_code1 = result_profile.getString("postal_code1");
                        String addressid1 = result_profile.getString("addressid1");
                        String address1 = result_profile.getString("address1");
                        String province1 = result_profile.getString("province1");
                        String city1 = result_profile.getString("city1");
                        String suburb1 = result_profile.getString("suburb1");
                        String postal_code2 = result_profile.getString("postal_code2");
                        String addressid2 = result_profile.getString("addressid2");
                        String address2 = result_profile.getString("address2");
                        String province2 = result_profile.getString("province2");
                        String city2 = result_profile.getString("city2");
                        String suburb2 = result_profile.getString("suburb2");

                        String name = first_name + " " + last_name;

                        if (file.equals("null")|| file.equalsIgnoreCase("")){
                            //Picasso.get().placeholder(R.drawable.back_arrow).transform(new CircleTransform()).into(img_my_profile);
                        }else {
                            Log.d("filefile",file);
                           Picasso.get().load(file).placeholder(R.drawable.back_arrow).transform(new CircleTransform()).into(img_my_profile);
                            //Picasso.get().load(new File(file)).transform(new CircleTransform()).into(img_my_profile);
                        }

                        if (address1.equals("null")){
                            address1 = "";
                        } else{
                            Home_address = address1;
                        }
                        if (province1.equals("null")){
                            province1 = "";
                        } else {
                            Home_address = address1 +", "+province1;
                        }
                        if (city1.equals("null")){
                            city1 = "";
                        }else {
                            Home_address = address1 +", "+province1+", "+city1;
                        }
                        if (suburb1.equals("null")){
                            suburb1 = "";
                        } else {
                            Home_address = address1 +", "+province1+", "+city1+", "+suburb1;
                        }
                        if (postal_code1.equals("null")){
                            postal_code1 = "";
                        } else {
                            Home_address = address1 +", "+province1+", "+city1+", "+suburb1+", "+postal_code1;
                        }

                        if (address2.equals("null")){
                            address2 = "";
                        } else {
                            Work_address = address2;
                        }
                        if (province2.equals("null")){
                            province2 = "";
                        } else {
                            Work_address = address2 +", "+province2;
                        }
                        if (city2.equals("null")){
                            city2 = "";
                        } else {
                            Work_address = address2 +", "+province2+", "+city2;
                        }
                        if (suburb2.equals("null")){
                            suburb2 = "";
                        } else {
                            Work_address = address2 +", "+province2+", "+city2+", "+suburb2;
                        }
                        if (postal_code2.equals("null")){
                            postal_code2 = "";
                        } else {
                            Work_address = address2 +", "+province2+", "+city2+", "+suburb2+", "+postal_code2;
                        }




                        txt_my_name.setText(name);
                        txt_my_email.setText(email);
                        txt_my_phone.setText(mobile_phone);
                        txt_my_home_address.setText(Home_address);
                        txt_my_work_address.setText(Work_address);

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

    @SuppressLint("NonConstantResourceId")
    private void setUpNavigationView() {
        nav_view.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    Intent intent = new Intent(MyProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_order_history:
                    Intent intent1 = new Intent(MyProfileActivity.this, OrderHistoryActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.nav_reviews:
                    Intent intent2 = new Intent(MyProfileActivity.this, ReviewsActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.nav_my_profile:
                    Intent intent3 = new Intent(MyProfileActivity.this, MyProfileActivity.class);
                    startActivity(intent3);
                    return true;
                case R.id.nav_settings:
                    Intent intent4 = new Intent(MyProfileActivity.this, SettingsActivity.class);
                    startActivity(intent4);
                    return true;
                case R.id.nav_legal:
                    Intent intent5 = new Intent(MyProfileActivity.this, LegalActivity.class);
                    startActivity(intent5);
                    return true;
                case R.id.nav_logout:
                    Log.d("HAHAHA", "hi1");
                    GotoLogout();
                    return true;
                default:
            }
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
            }
            return true;

        });
    }

    public void GotoLogout() {
        Log.d("HAHAHA", "hi");
        SharedPreferences User_ID_Remove = getSharedPreferences("Findar", Context.MODE_PRIVATE);
        SharedPreferences.Editor User_ID_Remove_editor = User_ID_Remove.edit();
        User_ID_Remove_editor.putString("USER_ID", "");
        User_ID_Remove_editor.apply();
        Constant.USER_ID = "";
//        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MyProfileActivity.this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}