package org.findar.user.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.findar.user.Helper.Constant;
import org.findar.user.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SplashActivity extends AppCompatActivity {

    TextView txt_login,sign_up;
    String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        txt_login = findViewById(R.id.txt_login);
        sign_up = findViewById(R.id.sign_up);
        txt_login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        });
        sign_up.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
            startActivity(intent);
        });

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//To do//
                            return;
                        }

// Get the Instance ID token//
                        String token = task.getResult().getToken();
                        String msg = getString(R.string.fcm_token) + token;
                        Log.d(TAG, msg);
                        System.out.println("****** Fcm Token :: "+token);

                        Constant.DEVICEID = token;

                    }
                });

    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences GetUser_id = getApplicationContext().getSharedPreferences("Findar", MODE_PRIVATE);
        Constant.USER_ID = GetUser_id.getString("USER_ID", "");
        Log.d("USER_ID", "USER_ID" + Constant.USER_ID);
        if (!Constant.USER_ID.equals("")) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}