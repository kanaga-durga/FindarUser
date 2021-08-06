package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.regex.Pattern;

public class ChangePasswordActivity extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*?[A-Z])" +         //at least 1 upper case letter
                    "(?=.*?[a-z])" +         //at least 1 lower case letter
                    "(?=.*?[0-9])" +         //at least 1 digit
                    ".{8,}" +               //at least 4 characters
                    "$");
    String TAG = "TAG_ChangePasswordActivity";
    Activity activity;
    EditText et_old_password, et_new_password, et_new_cpassword;
    TextView txt_change;
    UICommon uicommon;
    ImageView eye_icon_old_p,eye_icon_new_p,eye_icon_new_cp;

    int flag = 0,flagnew=0,flagcnew=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");*/

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,ChangePasswordActivity.this);

        activity = ChangePasswordActivity.this;
        et_old_password = findViewById(R.id.et_old_password);
        et_new_password = findViewById(R.id.et_new_password);
        et_new_cpassword = findViewById(R.id.et_new_cpassword);
        txt_change = findViewById(R.id.txt_change);
        eye_icon_old_p  = findViewById(R.id.eye_icon_old_p);
        eye_icon_new_p  = findViewById(R.id.eye_icon_new_p);
        eye_icon_new_cp  = findViewById(R.id.eye_icon_new_cp);

        SharedPreferences GetUser_id = getApplicationContext().getSharedPreferences("Findar", MODE_PRIVATE);
        Constant.USER_ID = GetUser_id.getString("USER_ID", "");

        txt_change.setOnClickListener(v -> {
            if (et_old_password.getText().toString().equals("")){
                et_old_password.setError(Constant.Please_enter_this_field);
                et_old_password.requestFocus();
            }
            else if (!validatePassword()) {
                return;
            }
//                else if (et_new_password.getText().toString().equals("")){
//                    et_new_password.setError(Constant.Please_enter_this_field);
//                    et_new_password.requestFocus();
//                }
            else if (et_new_cpassword.getText().toString().equals("")){
                et_new_cpassword.setError(Constant.Please_enter_this_field);
                et_new_cpassword.requestFocus();
            } else if (!et_new_password.getText().toString().equals(et_new_cpassword.getText().toString())){
                et_new_password.setError(Constant.Password_not_match);
                et_new_cpassword.setError(Constant.Password_not_match);
                et_new_password.requestFocus();
            } else {
                GotoUpdatePass(Constant.USER_ID, et_old_password.getText().toString(), et_new_cpassword.getText().toString());
            }
        });

        /************************* Show  Old Password  ******************************/
        eye_icon_old_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0) {
                    flag = 1;
                    et_old_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eye_icon_old_p.setImageResource(R.drawable.pass_icon);
                }
                else{
                    flag = 0;
                    et_old_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eye_icon_old_p.setImageResource(R.drawable.pass_icon);
                }
            }
        });

        /************************* Show  New Password  ******************************/
        eye_icon_new_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagnew==0) {
                    flagnew = 1;
                    et_new_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eye_icon_new_p.setImageResource(R.drawable.pass_icon);
                }
                else{
                    flagnew = 0;
                    et_new_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eye_icon_new_p.setImageResource(R.drawable.pass_icon);
                }
            }
        });
        /************************* Show  Confirm New Password  ******************************/
        eye_icon_new_cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flagcnew==0) {
                    flagcnew = 1;
                    et_new_cpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eye_icon_new_cp.setImageResource(R.drawable.pass_icon);
                }
                else{
                    flagcnew = 0;
                    et_new_cpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eye_icon_new_cp.setImageResource(R.drawable.pass_icon);
                }
            }
        });
    }
    private void GotoUpdatePass(String userId, String old_pass, String new_c_pass) {

        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);
        params.put(Constant.PPASSWORD, new_c_pass);
        params.put(Constant.P_OLD_PASSWORD, old_pass);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_ChangePassword" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                        startActivity(intent);
                    } else if(status.equals("0")){
                        et_old_password.setError(message);
                        et_old_password.requestFocus();
                    }
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Log.d(TAG, "Result_ChangePassword" + e);
                }
            }
        }, activity, Constant.CHANGE_PASSWORD, params, true);
    }
    private boolean validatePassword() {
        String passwordInput = et_new_password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            et_new_password.setError(Constant.Please_enter_this_field);
            et_new_password.requestFocus();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            et_new_password.setError(Constant.Invalid_Password);
            et_new_password.requestFocus();
            return false;
        } else {
            et_new_password.setError(null);
            return true;
        }
    }
}