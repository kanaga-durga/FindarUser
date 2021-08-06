package org.findar.user.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import org.findar.user.Activity.LegalActivity;
import org.findar.user.Activity.LoginActivity;
import org.findar.user.Activity.MainActivity;
import org.findar.user.Activity.MyProfileActivity;
import org.findar.user.Activity.OrderHistoryActivity;
import org.findar.user.Activity.ReviewsActivity;
import org.findar.user.Activity.SettingsActivity;
import org.findar.user.Activity.SignupActivity;
import org.findar.user.Activity.SplashActivity;
import org.findar.user.R;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class UICommon {

    public void BackButtonPress(View v, Activity activity){
        ImageView backArrow = (ImageView)v.findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(activity,"Back Pressed",Toast.LENGTH_LONG).show();
                activity.finish();
            }
        });

    }


    /******************* Navigation Menu **********************/
    @SuppressLint("NonConstantResourceId")
    public void setUpNavigationView(Activity activity, NavigationView nav_view, DrawerLayout drawer) {
        nav_view.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {


                case R.id.nav_home:
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    return true;
                case R.id.nav_order_history:
                    Intent intent1 = new Intent(activity, OrderHistoryActivity.class);
                    activity.startActivity(intent1);
                    return true;
                case R.id.nav_reviews:
                    Intent intent2 = new Intent(activity, ReviewsActivity.class);
                    activity.startActivity(intent2);
                    return true;
                case R.id.nav_my_profile:
                    Intent intent3 = new Intent(activity, MyProfileActivity.class);
                    activity.startActivity(intent3);
                    return true;
                case R.id.nav_settings:
                    Intent intent4 = new Intent(activity, SettingsActivity.class);
                    activity.startActivity(intent4);
                    return true;
                case R.id.nav_legal:
                    Intent intent5 = new Intent(activity, LegalActivity.class);
                    activity.startActivity(intent5);
                    return true;
                case R.id.nav_logout:
                    Log.d("HAHAHA", "hi1");
                    //  GotoLogout();
                    return true;
                default:
            }
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
            }
            return true;

        });
    }

    public void GotoLogout(Activity activity) {
        SharedPreferences User_ID_Remove = activity.getSharedPreferences("Findar", Context.MODE_PRIVATE);
        SharedPreferences.Editor User_ID_Remove_editor = User_ID_Remove.edit();
        User_ID_Remove_editor.putString("USER_ID", "");
        User_ID_Remove_editor.putString("MOBILENO", "");
        User_ID_Remove_editor.apply();
        Constant.USER_ID = "";
        Constant.MOBILE_NO = "";
//        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    public  void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}

