package org.findar.user.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.findar.user.Adapter.CustomRecyclerAdapterService;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.Service_GS;
import org.findar.user.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service_Types_Activity extends AppCompatActivity {

    String TAG = "TAG_Service_Types_Activity";
    RecyclerView recycleViewService;
    RecyclerView.Adapter mAdapterService;
    List<Service_GS> ServiceList;
    @SuppressLint("StaticFieldLeak")
    public static Service_Types_Activity serviceTypesActivity;
    Activity activity;

    TextView txt_services_error;
    ScrollView recycleViewServiceSCRL;
    NavigationView nav_view;
    public ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    UICommon uicommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_types);

        activity = Service_Types_Activity.this;
        serviceTypesActivity = Service_Types_Activity.this;

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,Service_Types_Activity.this);

        recycleViewServiceSCRL = findViewById(R.id.recycleViewServiceSCRL);
        txt_services_error = findViewById(R.id.txt_services_error);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setTitle("");
//        toolbar.setSubtitle("");
//        drawer = findViewById(R.id.drawer_layout);
//        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_toggle);
//        nav_view = findViewById(R.id.nav_view);
//        setUpNavigationView();
        recycleViewService = findViewById(R.id.recycleViewService);
        recycleViewService.setHasFixedSize(true);
        GridLayoutManager layoutManagerService = new GridLayoutManager(getApplicationContext(), 2);

        recycleViewService.setLayoutManager(layoutManagerService);

        ServiceList = new ArrayList<>();
        SharedPreferences GetUser_id = getApplicationContext().getSharedPreferences("Findar", MODE_PRIVATE);
        Constant.USER_ID = GetUser_id.getString("USER_ID", "");
        GetServiceList(Constant.USER_ID);

    }


    private void GetServiceList(String get_user_id) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, get_user_id);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Service_Type" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        JSONArray services_data_arr = result_obj.getJSONArray("servicesdata");

                        for (int i = 0; i < services_data_arr.length(); i++) {
                            Service_GS serviceGS = new Service_GS();
                            try {
                                JSONObject services_data_Object = services_data_arr.getJSONObject(i);
                                serviceGS.setSL_ID(services_data_Object.getString("id"));
                                serviceGS.setSL_NAME(services_data_Object.getString("name"));
                                serviceGS.setSL_IMG(services_data_Object.getString("file"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ServiceList.add(serviceGS);
                        }
                        mAdapterService = new CustomRecyclerAdapterService(Service_Types_Activity.this, ServiceList);
                        recycleViewService.setAdapter(mAdapterService);
                    } else if (status.equals("0")) {
                        recycleViewServiceSCRL.setVisibility(View.GONE);
                        txt_services_error.setVisibility(View.VISIBLE);
                        txt_services_error.setText(message);
                    }
                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.GET_SERVICES_LIST, params, true);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void gotoProblem() {
        Intent intent = new Intent(Service_Types_Activity.this, ProblemsActivity.class);
        startActivity(intent);
    }

    /*@SuppressLint("NonConstantResourceId")
    private void setUpNavigationView() {
        nav_view.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    Intent intent = new Intent(Service_Types_Activity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_order_history:
                    Intent intent1 = new Intent(Service_Types_Activity.this, OrderHistoryActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.nav_reviews:
                    Intent intent2 = new Intent(Service_Types_Activity.this, ReviewsActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.nav_my_profile:
                    Intent intent3 = new Intent(Service_Types_Activity.this, MyProfileActivity.class);
                    startActivity(intent3);
                    return true;
                case R.id.nav_settings:
                    Intent intent4 = new Intent(Service_Types_Activity.this, SettingsActivity.class);
                    startActivity(intent4);
                    return true;
                case R.id.nav_legal:
                    Intent intent5 = new Intent(Service_Types_Activity.this, LegalActivity.class);
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
    }*/

    /*public void GotoLogout() {
        Log.d("HAHAHA", "hi");
        SharedPreferences User_ID_Remove = getSharedPreferences("Findar", Context.MODE_PRIVATE);
        SharedPreferences.Editor User_ID_Remove_editor = User_ID_Remove.edit();
        User_ID_Remove_editor.putString("USER_ID", "");
        User_ID_Remove_editor.apply();
        Constant.USER_ID = "";
//        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Service_Types_Activity.this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }*/
}