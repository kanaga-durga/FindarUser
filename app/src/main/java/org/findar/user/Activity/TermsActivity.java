package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TermsActivity extends AppCompatActivity {
    String TAG = "TermsActivity";
    TextView terms;
    UICommon uicommon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        terms = findViewById(R.id.terms);

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,TermsActivity.this);


        GoToTermsAndConditions();
    }

    private void  GoToTermsAndConditions() {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolleyGet((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Register" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        String short_description = result_obj.getString("short_description");
                        JSONObject jsonObj = result_obj.getJSONObject("full_description");
                        String full_description = jsonObj.getString("client");

                        terms.setText(full_description);
                    } else {
                        Toast.makeText(TermsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ignored) {
                    Log.d(TAG, "Terms Exception" + ignored);
                }
            }
        }, TermsActivity.this, Constant.TERMS, params, true);
    }
}