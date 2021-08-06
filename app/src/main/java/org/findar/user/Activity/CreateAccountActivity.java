package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class CreateAccountActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*?[A-Z])" +         //at least 1 upper case letter
                    "(?=.*?[a-z])" +         //at least 1 lower case letter
                    "(?=.*?[0-9])" +         //at least 1 digit
                    ".{8,}" +               //at least 4 characters
                    "$");

    String TAG = "TAG_CreateAccountActivity";
    EditText DatePicker, et_first_name, et_last_name, et_email, et_password, et_c_password;
    Calendar myCalendar;
    RelativeLayout RL_Create, RL_terms;
    TextView txt_create, txt_terms_continue,login;
    String Mobile;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    UICommon uicommon;
    ImageView eye_icon_p,eye_icon_cp;
    int flag = 0,flagcp=0;
    TextView txt_terms_content,txt_terms_content3;
    String full_description="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        activity = this;

        View backV = findViewById(R.id.back_arrow);
     //   uicommon = new UICommon();
       // uicommon.BackButtonPress(includedLayout,CreateAccountActivity.this);



        DatePicker = findViewById(R.id.DatePicker);
        RL_Create = findViewById(R.id.RL_Create);
        RL_terms = findViewById(R.id.RL_terms);
        txt_create = findViewById(R.id.txt_create);
        txt_terms_continue = findViewById(R.id.txt_terms_continue);
        txt_terms_content = findViewById(R.id.txt_terms_content1);
        txt_terms_content3 = findViewById(R.id.txt_terms_content3);
        login = findViewById(R.id.login);

        et_first_name = findViewById(R.id.et_first_name);
        et_last_name = findViewById(R.id.et_last_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_c_password = findViewById(R.id.et_cpassword);

        eye_icon_p = findViewById(R.id.eye_icon_p);
        eye_icon_cp = findViewById(R.id.eye_icon_cp);

        Mobile = getIntent().getStringExtra("MOBILE");

        ImageView backArrow = (ImageView)backV.findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(activity,"Back Pressed",Toast.LENGTH_LONG).show();
                // activity.finish();

                if (RL_Create.getVisibility() == View.VISIBLE) {
                    // Its visible
                    finish();
                } else {
                    // Either gone or invisible
                    RL_Create.setVisibility(View.VISIBLE);
                    RL_terms.setVisibility(View.VISIBLE);
                }
            }
        });

        login.setOnClickListener(v -> {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        txt_terms_continue.setOnClickListener(v -> {
            /*Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);*/

            Intent intent = new Intent(CreateAccountActivity.this, MyProfileUpdateActivity.class);
            intent.putExtra("Navigation","FromAccount");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

        eye_icon_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
                if(flag==0) {
                    flag = 1;
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                   // eye_icon_p.setImageResource(R.drawable.pass_icon);
                }
                else{
                    flag = 0;
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                   // eye_icon_p.setImageResource(R.drawable.pass_icon);
                }
            }
        });

        eye_icon_cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_LONG).show();
                if(flagcp==0) {
                    flagcp = 1;
                    et_c_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    // eye_icon_cp.setImageResource(R.drawable.pass_icon);
                }
                else{
                    flagcp = 0;
                    et_c_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    // eye_icon_cp.setImageResource(R.drawable.pass_icon);
                }
            }
        });

        txt_create.setOnClickListener(v -> {
            if (et_first_name.toString().length() == 0) {
                et_first_name.setError(Constant.Please_enter_this_field);
                et_first_name.requestFocus();
            } else if (et_last_name.toString().length() == 0) {
                et_last_name.setError(Constant.Please_enter_this_field);
                et_last_name.requestFocus();
            } else if (et_email.toString().length() == 0) {
                et_email.setError(Constant.Please_enter_this_field);
                et_email.requestFocus();
            } else if (!et_email.getText().toString().trim().matches(Constant.emailPattern)) {
                et_email.setError(Constant.Invalid_Email);
                et_email.requestFocus();
            } else if (DatePicker.toString().length() == 0) {
                DatePicker.setError(Constant.Please_enter_this_field);
                DatePicker.requestFocus();
            } else if (!validatePassword()) {
                return;
            }
            else if (et_c_password.toString().length() == 0) {
                et_c_password.setError(Constant.Please_enter_this_field);
                et_c_password.requestFocus();
            } else if (et_password.getText().toString().equals(et_c_password.getText().toString())) {
                String F_name = et_first_name.getText().toString();
                String L_name = et_first_name.getText().toString();
                String Email = et_email.getText().toString();
                String DOB = DatePicker.getText().toString();
                String Password = et_password.getText().toString();
                GotoRegister(F_name, L_name, Email, DOB, Password, Mobile);
            } else {
                et_c_password.setError(Constant.Password_not_match);
                et_c_password.requestFocus();
            }
        });


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

        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        DatePicker.setOnClickListener(v -> new DatePickerDialog(CreateAccountActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void GotoRegister(String f_name, String l_name, String email, String DOB, String password, String mobile) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PFIRST_NAME, f_name);
        params.put(Constant.PLAST_NAME, l_name);
        params.put(Constant.PEMAIL, email);
        params.put(Constant.PDOB, DOB);
        params.put(Constant.PPASSWORD, password);
        params.put(Constant.PMOBILE, mobile);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Register" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        String userid = result_obj.getString("user_id");
                        SharedPreferences User_ID_Save = getSharedPreferences("Findar", Context.MODE_PRIVATE);
                        SharedPreferences.Editor User_ID_Save_editor = User_ID_Save.edit();
                        User_ID_Save_editor.putString("USER_ID", userid);
                        User_ID_Save_editor.apply();
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                        RL_Create.setVisibility(View.GONE);

                        GoToTermsAndConditions();

                        RL_terms.setVisibility(View.VISIBLE);
                    } else {
                        message = email + " " + message;
                        et_email.requestFocus();
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.REGISTER, params, true);
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DatePicker.setText(sdf.format(myCalendar.getTime()));
    }
    private boolean validatePassword() {
        String passwordInput = et_password.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            et_password.setError(Constant.Please_enter_this_field);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            et_password.setError(Constant.Invalid_Password);
            return false;
        } else {
            et_password.setError(null);
            return true;
        }

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
                        txt_terms_content.setText(short_description);
                        RL_terms.setVisibility(View.VISIBLE);
                        txt_terms_continue.setVisibility(View.VISIBLE);
                        JSONObject jsonObj = result_obj.getJSONObject("full_description");
                        full_description = jsonObj.getString("client");
                    } else {
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ignored) {
                    Log.d(TAG, "Terms Exception" + ignored);
                }
            }
        }, activity, Constant.TERMS, params, true);
    }

}
