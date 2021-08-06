package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;
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

import org.findar.user.Adapter.CustomRecyclerAdapterProblems;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.Problems_GS;
import org.findar.user.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProblemsActivity extends AppCompatActivity {
    String TAG = "TAG_ProblemsActivity";
    RecyclerView recycleViewProblems;
    RecyclerView.Adapter mAdapterProblems;
    List<Problems_GS> ProblemsList;
    @SuppressLint("StaticFieldLeak")
    public static ProblemsActivity problemsActivity;
    Activity activity;
    TextView txt_problems_error;
    ScrollView recycleViewProblemsSCRL;
    UICommon uicommon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);

        problemsActivity = ProblemsActivity.this;
        activity = ProblemsActivity.this;

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,ProblemsActivity.this);

        recycleViewProblemsSCRL = findViewById(R.id.recycleViewProblemsSCRL);
        txt_problems_error = findViewById(R.id.txt_problems_error);
        recycleViewProblems = findViewById(R.id.recycleViewProblems);
        recycleViewProblems.setHasFixedSize(true);
        GridLayoutManager layoutManagerService = new GridLayoutManager(ProblemsActivity.this, 2);

        recycleViewProblems.setLayoutManager(layoutManagerService);

        ProblemsList = new ArrayList<>();

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setTitle("");
//        toolbar.setSubtitle("");
//        //    FrameLayout service_fragment_container;
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_toggle);

        SharedPreferences GetUser_id = getApplicationContext().getSharedPreferences("Findar", MODE_PRIVATE);
        Constant.USER_ID = GetUser_id.getString("USER_ID", "");
        GetProblemsList(Constant.USER_ID);
    }
    private void GetProblemsList(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);
        params.put(Constant.PSERVICES_ID,Constant.SL_ID);


        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Login" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")){
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        JSONArray services_data_arr = result_obj.getJSONArray("problemsdata");

                        for (int i = 0; i < services_data_arr.length(); i++) {
                            Problems_GS problemsGs = new Problems_GS();
                            try {
                                JSONObject services_data_Object = services_data_arr.getJSONObject(i);
                                problemsGs.setSL_ID(services_data_Object.getString("id"));
                                problemsGs.setSL_NAME(services_data_Object.getString("name"));
                                problemsGs.setSL_IMG(services_data_Object.getString("file"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ProblemsList.add(problemsGs);
                        }
                        mAdapterProblems = new CustomRecyclerAdapterProblems(ProblemsActivity.this, ProblemsList);
                        recycleViewProblems.setAdapter(mAdapterProblems);
                    } else if (status.equals("0")){
                        recycleViewProblemsSCRL.setVisibility(View.GONE);
                        txt_problems_error.setVisibility(View.VISIBLE);
                        txt_problems_error.setText(message);
                    }
                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.GET_PROBLEMS_LIST, params, true);
    }

    public void gotoSubProblem() {
        Intent intent = new Intent(ProblemsActivity.this, SubProblemsActivity.class);
        startActivity(intent);
    }
}