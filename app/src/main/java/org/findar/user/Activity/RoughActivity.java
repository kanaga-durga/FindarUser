package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;

import org.findar.user.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RoughActivity extends AppCompatActivity {
    TextView txt_terms_content,txt_terms_content3;
    String full_description="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rough);
        txt_terms_content = findViewById(R.id.txt_terms_content1);
        txt_terms_content3 = findViewById(R.id.txt_terms_content3);
        GoToTermsAndConditions();

        txt_terms_content3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_terms_content.setText(full_description);
                txt_terms_content.setMovementMethod(new ScrollingMovementMethod());

                // this is needed only if you want to show scrollbar also when text is not scrolling
                txt_terms_content.setScrollBarFadeDuration(0);
                txt_terms_content3.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void  GoToTermsAndConditions() {
        Map<String, String> params = new HashMap<>();
        ApiConfig.RequestToVolleyGet((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("TERMS", "Result_Register" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        String short_description = result_obj.getString("short_description");
                        txt_terms_content.setText(short_description);

                        JSONObject jsonObj = result_obj.getJSONObject("full_description");
                        full_description = jsonObj.getString("client");
                        System.out.println("Full Desc"+full_description);
                       // RL_terms.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ignored) {
                    Log.d("Terms", "Terms Exception" + ignored);
                }
            }
        }, RoughActivity.this, Constant.TERMS, params, true);
    }
}