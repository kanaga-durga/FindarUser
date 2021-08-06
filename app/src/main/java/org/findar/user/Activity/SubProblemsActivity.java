package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import org.findar.user.Adapter.CustomRecyclerAdapterSubProblems;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.SubProblems_GS;
import org.findar.user.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubProblemsActivity extends AppCompatActivity {

    String TAG = "TAG_SubProblemsActivity";
    RecyclerView recycleViewSubProblems;
    RecyclerView.Adapter mAdapterSubProblems;
    List<SubProblems_GS> SubProblemsList;
    @SuppressLint("StaticFieldLeak")
    public static SubProblemsActivity subProblemsActivity;
    Activity activity;

    ImageView get_problem_img;
    TextView get_problem_name, txt_subproblems_error;

    ScrollView recycleViewSubProblemsSCRL;
    UICommon uicommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_problems);

        subProblemsActivity = SubProblemsActivity.this;
        activity = SubProblemsActivity.this;

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,SubProblemsActivity.this);

        recycleViewSubProblemsSCRL = findViewById(R.id.recycleViewSubProblemsSCRL);
        txt_subproblems_error = findViewById(R.id.txt_subproblems_error);


        recycleViewSubProblems = findViewById(R.id.recycleViewSubProblems);
        recycleViewSubProblems.setHasFixedSize(true);
        LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(this);
        recycleViewSubProblems.setLayoutManager(layoutManagerSubProblems);

        SubProblemsList = new ArrayList<>();

        get_problem_name = findViewById(R.id.get_problem_name);
        get_problem_img = findViewById(R.id.get_problem_img);

        get_problem_name.setText(Constant.PL_NAME);
        Picasso.get().load(Constant.PL_IMG).into(get_problem_img);

        SharedPreferences GetUser_id = getApplicationContext().getSharedPreferences("Findar", MODE_PRIVATE);
        Constant.USER_ID = GetUser_id.getString("USER_ID", "");
        GetSubProblemsList(Constant.USER_ID);

    }

    private void GetSubProblemsList(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);
        params.put(Constant.PSERVICES_ID,Constant.SL_ID);
        params.put(Constant.PPROBLEMS_ID,Constant.PL_ID);


        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Login" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")){
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        Log.d(TAG, "Result_SubProblem_length" + result_obj);
                        JSONArray services_data_arr = result_obj.getJSONArray("subproblemsdata");


                        for (int i = 0; i < services_data_arr.length(); i++) {
                            SubProblems_GS subproblemsGs = new SubProblems_GS();
                            try {
                                JSONObject services_data_Object = services_data_arr.getJSONObject(i);
                                subproblemsGs.setSL_ID(services_data_Object.getString("id"));
                                subproblemsGs.setSL_NAME(services_data_Object.getString("name"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            SubProblemsList.add(subproblemsGs);
                        }
                        mAdapterSubProblems = new CustomRecyclerAdapterSubProblems(SubProblemsActivity.this, SubProblemsList);
                        recycleViewSubProblems.setAdapter(mAdapterSubProblems);
                    } else if (status.equals("0")){
                        recycleViewSubProblemsSCRL.setVisibility(View.GONE);
                        txt_subproblems_error.setVisibility(View.VISIBLE);
                        txt_subproblems_error.setText(message);
                    }
                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.GET_SUB_PROBLEMS_LIST, params, true);
    }

    public void gotoSubProblem() {
        Intent intent = new Intent(SubProblemsActivity.this, DescribeProblemActivity.class);
        startActivity(intent);
    }
}