package org.findar.user.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.R;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class SettingsActivity extends AppCompatActivity {
    String TAG = "TAG_SettingsActivity";
    Activity activity;

    NavigationView nav_view;
    public ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    MaterialButton nav_logoutbtn;
    UICommon uicommon;
    TextView txt_s_change_pass, txt_s_terms, txt_s_about_us, txt_s_legal, txt_s_privacy, txt_s_set_home_work;
    TextView textViewName,textViewEmail,textViewVersion;
    ImageView imageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        activity = SettingsActivity.this;
        uicommon = new UICommon();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_toggle);
        nav_view = findViewById(R.id.nav_view);
        txt_s_change_pass = findViewById(R.id.txt_s_change_pass);
        txt_s_terms = findViewById(R.id.txt_s_terms);
        txt_s_about_us = findViewById(R.id.txt_s_about_us);
        txt_s_legal = findViewById(R.id.txt_s_legal);
        txt_s_privacy = findViewById(R.id.txt_s_privacy);
        txt_s_set_home_work = findViewById(R.id.txt_s_set_home_work);

        txt_s_change_pass.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        });

        txt_s_terms.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, TermsActivity.class);
            startActivity(intent);
        });

        //setUpNavigationView();

        nav_logoutbtn = findViewById(R.id.nav_logoutbtn);


        // setUpNavigationView();
        uicommon.setUpNavigationView(this,nav_view,drawer);

        nav_logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"Log out0",Toast.LENGTH_LONG).show();
                uicommon.GotoLogout(SettingsActivity.this);
            }
        });

        View headerView = nav_view.getHeaderView(0);
        textViewName = (TextView) headerView.findViewById(R.id.textViewName);
        textViewEmail = (TextView) headerView.findViewById(R.id.textViewEmail);
        imageViewProfile  = (ImageView) headerView.findViewById(R.id.imageViewProfile);

        textViewVersion = (TextView) headerView.findViewById(R.id.textViewVersion);

        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            String version = pInfo.versionName;
            textViewVersion.setText(getResources().getString(R.string.version) + " : "+ version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        textViewName.setText(Constant.USER_F_NAME);
        textViewEmail.setText(Constant.USER_EMAIL);

        if(!Constant.USER_P_FILE.equalsIgnoreCase(""))
            Picasso.get()
                    .load(Constant.USER_P_FILE)
                    .fit()
                    .into(imageViewProfile);
    }
    @SuppressLint("NonConstantResourceId")
    private void setUpNavigationView() {
        nav_view.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_order_history:
                    Intent intent1 = new Intent(SettingsActivity.this, OrderHistoryActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.nav_reviews:
                    Intent intent2 = new Intent(SettingsActivity.this, ReviewsActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.nav_my_profile:
                    Intent intent3 = new Intent(SettingsActivity.this, MyProfileActivity.class);
                    startActivity(intent3);
                    return true;
                case R.id.nav_settings:
                    Intent intent4 = new Intent(SettingsActivity.this, SettingsActivity.class);
                    startActivity(intent4);
                    return true;
                case R.id.nav_legal:
                    Intent intent5 = new Intent(SettingsActivity.this, LegalActivity.class);
                    startActivity(intent5);
                    return true;
                case R.id.nav_logout:
                    GotoLogout();
                    return true;
                default:
            }
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawers();
            }
            return true;

        });
    }

    public void GotoLogout() {
        SharedPreferences User_ID_Remove = getSharedPreferences("Findar", Context.MODE_PRIVATE);
        SharedPreferences.Editor User_ID_Remove_editor = User_ID_Remove.edit();
        User_ID_Remove_editor.putString("USER_ID", "");
        User_ID_Remove_editor.apply();
        Constant.USER_ID = "";
//        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SettingsActivity.this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}