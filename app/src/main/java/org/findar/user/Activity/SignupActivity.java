package org.findar.user.Activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.R;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    String TAG = "TAG_SignupActivity";
    CountryCodePicker ccp;
    RelativeLayout RT_Phone_number, RT_OTP;
    TextView txt_phn_continue, txt_OTP_continue, txt_demo_OTP, txt_resend;
    EditText et_OPT_1, et_OPT_2, et_OPT_3, et_OPT_4;
    EditText et_phone_number;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    UICommon uicommon;
    String navigation="";

    String ProfileImage,HomeAddresss,WorkAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        activity = this;

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,SignupActivity.this);



        ccp = findViewById(R.id.ccp);
        RT_Phone_number = findViewById(R.id.RT_Phone_number);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_OPT_1 = findViewById(R.id.et_OPT_1);
        et_OPT_2 = findViewById(R.id.et_OPT_2);
        et_OPT_3 = findViewById(R.id.et_OPT_3);
        et_OPT_4 = findViewById(R.id.et_OPT_4);
        RT_OTP = findViewById(R.id.RT_OTP);
        txt_phn_continue = findViewById(R.id.txt_phn_continue);
        txt_OTP_continue = findViewById(R.id.txt_OTP_continue);
        txt_demo_OTP = findViewById(R.id.txt_demo_OTP);
        txt_resend = findViewById(R.id.txt_resend);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            navigation = extras.getString("NAVIGATION");
            String MOBILENO = extras.getString("MOBILENO");
            if(navigation.equalsIgnoreCase("LoginPage")){
                RT_Phone_number.setVisibility(View.GONE);
                RT_OTP.setVisibility(View.VISIBLE);
                et_phone_number.setText(MOBILENO);
                GotoOTP(MOBILENO);

                System.out.println("********** Extras"+extras);
                System.out.println("********** Extras"+extras);
                System.out.println("********** NAVIGATION"+extras.getString("NAVIGATION"));
            }

        }

        txt_resend.setOnClickListener(v -> GotoOTP(et_phone_number.getText().toString()));


//        includedLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        txt_phn_continue.setOnClickListener(v -> {
            if (et_phone_number.getText().toString().length() == 0) {
                et_phone_number.setError(Constant.Please_enter_this_field);
                et_phone_number.requestFocus();
            } else {
                GotoMobileValidation(et_phone_number.getText().toString());
            }
        });
        et_OPT_1.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    et_OPT_2.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_OPT_2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et_OPT_3.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }
        });
        et_OPT_3.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et_OPT_4.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }
        });

        txt_OTP_continue.setOnClickListener(v -> {

            if (et_OPT_1.getText().toString().length() == 0) {
                et_OPT_1.requestFocus();
            } else if (et_OPT_2.getText().toString().length() == 0) {
                et_OPT_2.requestFocus();
            } else if (et_OPT_3.getText().toString().length() == 0) {
                et_OPT_3.requestFocus();
            } else if (et_OPT_4.getText().toString().length() == 0) {
                et_OPT_4.requestFocus();
            } else {
                String EnterOTP = et_OPT_1.getText().toString() + et_OPT_2.getText().toString() + et_OPT_3.getText().toString() + et_OPT_4.getText().toString();

                System.out.println("Enter OTP Value ::"+EnterOTP + " ~ "+txt_demo_OTP.getText().toString());
                if (EnterOTP.equals(txt_demo_OTP.getText().toString())) {
                   // Toast.makeText(getApplicationContext(), "OTP Verified", Toast.LENGTH_LONG).show();


                    GotoOTPValidation(et_phone_number.getText().toString(),EnterOTP);

//                    Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
//                    intent.putExtra("MOBILE", et_phone_number.getText().toString());
//                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "OTP Invalid", Toast.LENGTH_LONG).show();
                    et_OPT_1.requestFocus();
                }
            }
        });


        ccp.setOnCountryChangeListener(() -> {
//                Toast.makeText(getApplicationContext(), "Updated " + ccp.getSelectedCountryCodeWithPlus(), Toast.LENGTH_SHORT).show();
        });
    }

    private void GotoMobileValidation(String GetMobileNumber) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PMOBILE, GetMobileNumber);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Login" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        RT_Phone_number.setVisibility(View.GONE);
                        RT_OTP.setVisibility(View.VISIBLE);
                        GotoOTP(GetMobileNumber);
                    } else {
                        message = GetMobileNumber + " " + message;
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.MOBILE_VALIDATION, params, true);
    }

    /**************************** GET OTP ******************************/
    private void GotoOTP(String GetMobileNumber) {

//        Random random = new Random();
//        @SuppressLint("DefaultLocale")
//        String generatedOTP = String.format("%04d", random.nextInt(10000));
//        txt_demo_OTP.setText(generatedOTP);

        Map<String, String> params = new HashMap<>();
        params.put(Constant.PMOBILE, GetMobileNumber);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_OTP" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    JSONObject jObject = new JSONObject(jsonObject.getString("result"));
                    String otpcode = jObject.getString("otpcode");
                    Log.d(TAG, "Result_OTPCODE" + otpcode);

                    if (status.equals("1")) {
//                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//                        RT_Phone_number.setVisibility(View.GONE);
//                        RT_OTP.setVisibility(View.VISIBLE);
//                        GotoOTP();
                        txt_demo_OTP.setText(otpcode);
                        txt_demo_OTP.setVisibility(View.INVISIBLE);
                        et_OPT_1.setText(otpcode.substring(0));
                        et_OPT_2.setText(otpcode.substring(1));
                        et_OPT_3.setText(otpcode.substring(2));
                        et_OPT_4.setText(otpcode.substring(3));
                       // GotoOTPValidation(GetMobileNumber,otpcode);
                    } else {
                        message = GetMobileNumber + " " + message;
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException ignored) {
                }
            }
        }, activity, Constant.REQUESTOTP, params, true);
    }

    private void GotoOTPValidation(String GetMobileNumber,String OTPCode) {
        Map<String, String> params = new HashMap<>();
        Log.d(TAG, "********* PHONE NUMBER " + GetMobileNumber + " ~ "+OTPCode);
        params.put(Constant.PMOBILE, GetMobileNumber);
        params.put(Constant.POTPCODE, OTPCode);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_OTP VALIDATION" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                          Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
//                        RT_Phone_number.setVisibility(View.GONE);
//                        RT_OTP.setVisibility(View.VISIBLE);
//                        GotoOTP(GetMobileNumber);
                        if(navigation.equalsIgnoreCase("LoginPage")){

                            GetProfiles(Constant.USER_ID);

                       /*     Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);*/
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                            intent.putExtra("MOBILE", et_phone_number.getText().toString());
                            startActivity(intent);
                        }
                    } else {
                        message = GetMobileNumber + " " + message;
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.CHECKOTP, params, true);
    }


    private void GetProfiles(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Profile Edit" + jsonObject);

                    String status = jsonObject.getString("status");
//                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        JSONObject result_profile = jsonObject.getJSONObject("result");
                        ProfileImage= result_profile.getString("file");
                        HomeAddresss = result_profile.getString("address1");
                        WorkAddress = result_profile.getString("address2");

                        if(!ProfileImage.equalsIgnoreCase("") && !HomeAddresss.equalsIgnoreCase("") && !WorkAddress.equalsIgnoreCase("")) {
                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(SignupActivity.this, MyProfileUpdateActivity.class);
                            intent.putExtra("Navigation","FromAccount");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_Profile" + e);
                }
            }
        }, activity, Constant.GET_USER_DETAILS, params, true);
    }

}
