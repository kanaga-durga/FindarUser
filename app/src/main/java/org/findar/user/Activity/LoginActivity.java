package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
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

public class LoginActivity extends AppCompatActivity {

    String TAG = "TAG_LoginActivity";
    TextView Login, create_an_account;
    EditText et_username, et_password;
    Activity activity;
    ImageView eye_icon;
    int flag = 0;
    UICommon uicommon;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,LoginActivity.this);


        Login = findViewById(R.id.Login);
        create_an_account = findViewById(R.id.create_an_account);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        eye_icon = findViewById(R.id.eye_icon);

        create_an_account.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Onclick Login Button
        Login.setOnClickListener(v -> {
            if (et_username.toString().length() == 0) {
                et_username.setError(Constant.Please_enter_this_field);
                et_username.requestFocus();
            } else if (!et_username.getText().toString().trim().matches(Constant.emailPattern)) {
                et_username.setError(Constant.Invalid_Email);
                et_username.requestFocus();
            } else if (et_password.getText().toString().length() == 0) {
                et_password.setError(Constant.Please_enter_this_field);
                et_password.requestFocus();
            } else {
                String email_str = et_username.getText().toString().trim();
                String password_str = et_password.getText().toString().trim();
                // Login Method Call
                GotoLogin(email_str, password_str);
            }
        });

        /************************* Show Password  ******************************/
        eye_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==0) {
                    flag = 1;
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    eye_icon.setImageResource(R.drawable.pass_icon);
                }
                else{
                    flag = 0;
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    eye_icon.setImageResource(R.drawable.pass_icon);
                }
            }
        });
    }

    // Login Method
    private void GotoLogin(String get_email, String get_password) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PEMAIL, get_email);
        params.put(Constant.PPASSWORD, get_password);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Login" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")){
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        String userid = result_obj.getString("userid");
                        //userid = "27";
                        String mobileno = result_obj.getString("mobile");

                        SharedPreferences User_ID_Save = getSharedPreferences("Findar", Context.MODE_PRIVATE);
                        SharedPreferences.Editor User_ID_Save_editor = User_ID_Save.edit();
                        User_ID_Save_editor.putString("USER_ID", userid);
                        User_ID_Save_editor.putString("MOBILENO", mobileno);
                        User_ID_Save_editor.apply();
                        Constant.USER_ID = userid;
                        Constant.MOBILE_NO = mobileno;

                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

////                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                        startActivity(intent);

                        GoToDeviceToken();
//
                       /* Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                        intent.putExtra("NAVIGATION","LoginPage");
                        intent.putExtra("MOBILENO","mobileno");
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);*/
                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        et_username.requestFocus();
                    }

                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.LOGIN, params, true);
    }

    // Login Method
    private void GoToDeviceToken() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, Constant.USER_ID);
        params.put(Constant.P_DEVICEID, Constant.DEVICEID);
        params.put(Constant.P_DEVICETYPE, "Android");

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("LoginActivity", "Result_DeviceUpdate" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")){
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                        intent.putExtra("NAVIGATION","LoginPage");
                        intent.putExtra("MOBILENO","mobileno");
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.UPDATE_DEVICEID, params, true);
    }
}