package org.findar.user.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.findar.user.Adapter.CustomRecyclearAdapterJobHistory;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.JobHistory_GS;

import org.findar.user.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderHistoryActivity extends AppCompatActivity {
    NavigationView nav_view;
    public ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    MaterialButton nav_logoutbtn;
    UICommon uicommon;
    Activity activity;
    String TAG = "OrderHistoryActivity";
    RecyclerView recycleViewJob;
    ArrayList<JobHistory_GS> jobHistoryGsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        activity = this;

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

        nav_logoutbtn = findViewById(R.id.nav_logoutbtn);
        uicommon.setUpNavigationView(this,nav_view,drawer);

        nav_logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Log out0",Toast.LENGTH_LONG).show();
                uicommon.GotoLogout(OrderHistoryActivity.this);
            }
        });

        recycleViewJob = findViewById(R.id.recycleViewJob);

        GetOrderHistory(Constant.USER_ID);



    }

    /*************************** Get Order History ***************************/
    @SuppressLint("SetTextI18n")
    private void GetOrderHistory(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_OrderHistory" + jsonObject);
                    String status = jsonObject.getString("status");
//                    String message = jsonObject.getString("message");

                    jobHistoryGsArrayList = new ArrayList<JobHistory_GS>();
                    if (status.equals("1")) {

                        JSONArray result_job = jsonObject.getJSONArray("result");
                        if(result_job.length()!=0) {
                            for (int i = 0; i < result_job.length(); i++) {
                                JobHistory_GS jobHistory_gs = new JobHistory_GS();
                                JSONObject jobRecord = result_job.getJSONObject(i);
                                System.out.println("Result_ HistoryJobID" + jobRecord.getString("job_id"));
                                jobHistory_gs.setJob_id(jobRecord.getString("job_id"));
                                jobHistory_gs.setPlumber_name(jobRecord.getString("plumber_name"));
                                jobHistory_gs.setPlumber_profile_picture(jobRecord.getString("plumber_profile_picture"));
                                jobHistory_gs.setCompany_name(jobRecord.getString("company_name"));
                                jobHistory_gs.setPlumbing_state(jobRecord.getString("plumber_status"));
                                jobHistory_gs.setCompanyId(jobRecord.getString("company_id"));

                                jobHistoryGsArrayList.add(jobHistory_gs);
                            }
                        }

                        recycleViewJob.setHasFixedSize(true);
                        LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(this);
                        recycleViewJob.setLayoutManager(layoutManagerSubProblems);
                        CustomRecyclearAdapterJobHistory mAdapterJobHistory = new CustomRecyclearAdapterJobHistory(OrderHistoryActivity.this, jobHistoryGsArrayList);
                        recycleViewJob.setAdapter(mAdapterJobHistory);
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_OrderHistory" + e);
                }
            }
        }, activity, Constant.ORDERHISTORY, params, true);
    }
}