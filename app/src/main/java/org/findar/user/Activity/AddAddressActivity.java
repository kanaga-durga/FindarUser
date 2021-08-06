package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.City_GS;
import org.findar.user.Model.Province_GS;
import org.findar.user.Model.Suburb_GS;
import org.findar.user.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddAddressActivity extends AppCompatActivity {

    String TAG = "TAG_AddAddressActivity";
    @SuppressLint("StaticFieldLeak")
    public static AddAddressActivity addAddressActivity;
    Activity activity;

    EditText et_physical_address, et_postal_code;
    TextView txt_save, txt_select_province, txt_select_city, txt_select_suburb;
    Spinner spin_province, spin_city, spin_suburb;

    private ArrayList<Province_GS> provinceGsArrayList;
    private final ArrayList<String> province_array = new ArrayList<>();

    private ArrayList<City_GS> cityGsArrayList;
    private final ArrayList<String> city_array = new ArrayList<>();

    private ArrayList<Suburb_GS> suburbGsArrayList;
    private final ArrayList<String> suburb_array = new ArrayList<>();

    String pid, p_name, cid, c_name, sid, s_name, str_address, str_province, str_postal, lat, lan, set_address;
    UICommon uicommon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,AddAddressActivity.this);

//        ImageView backArrow = (ImageView)findViewById(R.id.back_arrow);
//        backArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AddAddressActivity.this, MyProfileUpdateActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });

        addAddressActivity = AddAddressActivity.this;
        activity = AddAddressActivity.this;


        et_physical_address = findViewById(R.id.et_physical_address);
        et_postal_code = findViewById(R.id.et_postal_code);
        spin_province = findViewById(R.id.spin_province);
        spin_city = findViewById(R.id.spin_city);
        spin_suburb = findViewById(R.id.spin_suburb);
        txt_save = findViewById(R.id.txt_save);
        txt_select_province = findViewById(R.id.txt_select_province);
        txt_select_city = findViewById(R.id.txt_select_city);
        txt_select_suburb = findViewById(R.id.txt_select_suburb);

        str_address = getIntent().getStringExtra("Address");
        str_province = getIntent().getStringExtra("country");
        str_postal = getIntent().getStringExtra("postalcode");

        System.out.println("Postal Code"+str_postal);

        lat = getIntent().getStringExtra("lat");
        lan = getIntent().getStringExtra("lan");
        set_address = getIntent().getStringExtra("SET_ADDRESS");

        if (str_address.equals("null") || !str_address.equals("")) {
            et_physical_address.setText(str_address);
        } else {
            et_physical_address.setText("");
        }

        if (str_postal.equals("null") || str_postal.equals("")) {
            et_postal_code.setText("");

        } else {
            et_postal_code.setText(str_postal);
        }


        provinceGsArrayList = new ArrayList<>();
        province_array.add(0, "Select Province");
        ArrayAdapter<String> ProvinceArrayAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_item, province_array);
        ProvinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spin_province.setAdapter(ProvinceArrayAdapter);

        cityGsArrayList = new ArrayList<>();
        city_array.add(0, "Select City");
        ArrayAdapter<String> cityArrayAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_item, city_array);
        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spin_city.setAdapter(cityArrayAdapter);

        suburbGsArrayList = new ArrayList<>();
        suburb_array.add(0, "Select Suburb");
        ArrayAdapter<String> suburbArrayAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_item, suburb_array);
        suburbArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spin_suburb.setAdapter(suburbArrayAdapter);


        spin_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                txt_select_province.setVisibility(View.GONE);
                if (position == 0) {
                    pid = "0";
                    p_name = "Select Province";
                } else {
                    position = position - 1;
                    Province_GS province = provinceGsArrayList.get(position);
                    pid = province.getP_ID();
                    p_name = province.getP_NAME();
                    GetCity(Constant.USER_ID, pid);
                }
                Toast.makeText(AddAddressActivity.this, position + "==" + pid + "==" + p_name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.d("cardStatusString", "arg0" + arg0);

            }
        });
        spin_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                txt_select_city.setVisibility(View.GONE);
                if (position == 0) {
                    cid = "0";
                    c_name = "Select City";
                } else {
                    position = position - 1;
                    City_GS city = cityGsArrayList.get(position);
                    cid = city.getC_ID();
                    c_name = city.getC_NAME();
                    GetSuburb(Constant.USER_ID, pid, cid);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.d("cardStatusString", "arg0" + arg0);

            }
        });

        spin_suburb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                txt_select_suburb.setVisibility(View.GONE);
                if (position == 0) {
                    sid = "0";
                    s_name = "Select Province";
                } else {
                    position = position - 1;
                    Suburb_GS suburb = suburbGsArrayList.get(position);
                    sid = suburb.getS_ID();
                    s_name = suburb.getS_NAME();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.d("cardStatusString", "arg0" + arg0);

            }
        });

        txt_save.setOnClickListener(v -> {
            if (et_physical_address.getText().toString().equals("")) {
                et_physical_address.setError(Constant.Please_enter_this_field);
                et_physical_address.requestFocus();
            } else if (pid.equals("0")) {
                txt_select_province.setVisibility(View.VISIBLE);
            } else if (cid.equals("0")) {
                txt_select_city.setVisibility(View.VISIBLE);
            } else if (sid.equals("0")) {
                txt_select_suburb.setVisibility(View.VISIBLE);
            } else if (et_postal_code.getText().toString().equals("")) {
                et_postal_code.setError(Constant.Please_enter_this_field);
                et_postal_code.requestFocus();
            } else if (set_address.equals("HOME")) {
                Constant.Home_Address = et_physical_address.getText().toString();
                Constant.Home_Province_ID = pid;
                Constant.Home_Province_Name = p_name;
                Constant.Home_City_ID = cid;
                Constant.Home_City_Name = c_name;
                Constant.Home_Suburb_ID = sid;
                Constant.Home_Suburb_Name = s_name;
                Constant.Home_Postal = et_postal_code.getText().toString();

                SharedPreferences User_Profile_Save = getSharedPreferences("Findar", Context.MODE_PRIVATE);
                SharedPreferences.Editor User_Profile_Save_editor = User_Profile_Save.edit();
                User_Profile_Save_editor.putString("USER_HOME_ADDRESS", et_physical_address.getText().toString());
                User_Profile_Save_editor.commit();

                Goto_Home_Address_Update();
            } else if (set_address.equals("WORK")) {
                Constant.WORK_Address = et_physical_address.getText().toString();
                Constant.WORK_Province_ID = pid;
                Constant.WORK_Province_Name = p_name;
                Constant.WORK_City_ID = cid;
                Constant.WORK_City_Name = c_name;
                Constant.WORK_Suburb_ID = sid;
                Constant.WORK_Suburb_Name = s_name;
                Constant.WORK_Postal = et_postal_code.getText().toString();

                SharedPreferences User_Profile_Save = getSharedPreferences("Findar", Context.MODE_PRIVATE);
                SharedPreferences.Editor User_Profile_Save_editor = User_Profile_Save.edit();
                User_Profile_Save_editor.putString("USER_WORK_ADDRESS", et_physical_address.getText().toString());
                User_Profile_Save_editor.commit();

                Goto_WORK_Address_Update();
            }
            else if(set_address.equals("")){
                Constant.ADDRESS = et_physical_address.getText().toString();
                Constant.PROVINCE = p_name;
                Constant.CITY = c_name;
                Constant.SUBURB = s_name;
                Constant.POSTALCODE = et_postal_code.getText().toString();
                finish();
                MapActivity.getInstance().gotoSetAddress(et_physical_address.getText().toString());
            }
        });

        SharedPreferences GetUser_id = getApplicationContext().getSharedPreferences("Findar", MODE_PRIVATE);
        Constant.USER_ID = GetUser_id.getString("USER_ID", "");

        GetProvinceList(Constant.USER_ID);
    }

    private void Goto_WORK_Address_Update() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PID, Constant.USER_ID);
        params.put(Constant.PUSER_ID, Constant.USER_ID);
        params.put(Constant.PEMAIL, Constant.USER_EMAIL);
        params.put(Constant.P_USER_DETAIL_ID, Constant.USER_DETAIL_ID);
        params.put(Constant.PMOBILE, Constant.USER_MOBILE);
        params.put(Constant.P_ADDRESS_ID_2, Constant.WORK_ID);
        params.put(Constant.P_ADDRESS_2, Constant.WORK_Address);
        params.put(Constant.P_PROVINCE_2, Constant.WORK_Province_ID);
        params.put(Constant.P_CITY_2, Constant.WORK_City_ID);
        params.put(Constant.P_SUBURB_2, Constant.WORK_Suburb_ID);
        params.put(Constant.P_POST_2, Constant.WORK_Postal);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Profile" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        Intent intent = new Intent(getApplicationContext(), MyProfileUpdateActivity.class);
                        intent.putExtra("Navigation","FromAccount");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_Profile" + e);
                }
            }
        }, activity, Constant.UPDATE_USER_DETAILS, params, true);
    }

    private void Goto_Home_Address_Update() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PID, Constant.USER_ID);
        params.put(Constant.PUSER_ID, Constant.USER_ID);
        params.put(Constant.PEMAIL, Constant.USER_EMAIL);
        params.put(Constant.P_USER_DETAIL_ID, Constant.USER_DETAIL_ID);
        params.put(Constant.PMOBILE, Constant.USER_MOBILE);
        params.put(Constant.P_ADDRESS_ID_1, Constant.Home_ID);
        params.put(Constant.P_ADDRESS_1, Constant.Home_Address);
        params.put(Constant.P_PROVINCE_1, Constant.Home_Province_ID);
        params.put(Constant.P_CITY_1, Constant.Home_City_ID);
        params.put(Constant.P_SUBURB_1, Constant.Home_Suburb_ID);
        params.put(Constant.P_POST_1, Constant.Home_Postal);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Profile" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        Intent intent = new Intent(getApplicationContext(), MyProfileUpdateActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_Profile" + e);
                }
            }
        }, activity, Constant.UPDATE_USER_DETAILS, params, true);
    }

    private void GetSuburb(String userId, String pid, String cid) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);
        params.put(Constant.PPROVINCE_ID, pid);
        params.put(Constant.PCITY_ID, cid);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Login" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        Log.d(TAG, "Result_Login_length" + result_obj);
                        JSONArray suburbdata_arr = result_obj.getJSONArray("suburbdata");

                        suburbGsArrayList.clear();
                        for (int i = 0; i < suburbdata_arr.length(); i++) {
                            Suburb_GS suburbGs = new Suburb_GS();

                            try {
                                JSONObject suburbdata_Object = suburbdata_arr.getJSONObject(i);
                                suburbGs.setS_ID(suburbdata_Object.getString("id"));
                                suburbGs.setS_NAME(suburbdata_Object.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            suburbGsArrayList.add(suburbGs);


                        }
                        for (int i = 0; i < suburbGsArrayList.size(); i++) {
                            suburb_array.add(suburbGsArrayList.get(i).getS_NAME());
                        }
                        ArrayAdapter<String> suburbArrayAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_item, suburb_array);
                        suburbArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        spin_suburb.setAdapter(suburbArrayAdapter);

                    }
                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.GET_SUBURB_LIST, params, true);
    }

    private void GetCity(String userId, String pid) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);
        params.put(Constant.PPROVINCE_ID, pid);


        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Login" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        Log.d(TAG, "Result_Login_length" + result_obj);
                        JSONArray citydata_arr = result_obj.getJSONArray("citydata");

                        cityGsArrayList.clear();
                        for (int i = 0; i < citydata_arr.length(); i++) {
                            City_GS cityGs = new City_GS();

                            try {
                                JSONObject citydata_Object = citydata_arr.getJSONObject(i);
                                cityGs.setC_ID(citydata_Object.getString("id"));
                                cityGs.setC_NAME(citydata_Object.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            cityGsArrayList.add(cityGs);


                        }
                        for (int i = 0; i < cityGsArrayList.size(); i++) {
                            city_array.add(cityGsArrayList.get(i).getC_NAME());
                        }
                        ArrayAdapter<String> cityArrayAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_item, city_array);
                        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        spin_city.setAdapter(cityArrayAdapter);
                        suburbGsArrayList.clear();
                        Suburb_GS suburbGs = new Suburb_GS();
                        suburbGs.setS_ID("0");
                        suburbGs.setS_NAME("Select City");
                        suburbGsArrayList.add(suburbGs);
                        ArrayAdapter<String> suburbArrayAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_item, suburb_array);
                        suburbArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        spin_suburb.setAdapter(suburbArrayAdapter);

                    }
                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.GET_CITY_LIST, params, true);
    }

    private void GetProvinceList(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Login" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        Log.d(TAG, "Result_Login_length" + result_obj);
                        JSONArray provincedata_arr = result_obj.getJSONArray("provincedata");

                        provinceGsArrayList.clear();
                        for (int i = 0; i < provincedata_arr.length(); i++) {
                            Province_GS provinceGs = new Province_GS();

                            try {
                                JSONObject provincedata_Object = provincedata_arr.getJSONObject(i);
                                provinceGs.setP_ID(provincedata_Object.getString("id"));
                                provinceGs.setP_NAME(provincedata_Object.getString("name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            provinceGsArrayList.add(provinceGs);


                        }
                        for (int i = 0; i < provinceGsArrayList.size(); i++) {
                            province_array.add(provinceGsArrayList.get(i).getP_NAME());
                        }

                        ArrayAdapter<String> provinceArrayAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_item, province_array);
                        provinceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        spin_province.setAdapter(provinceArrayAdapter);
                        if (!str_province.equals("null") || !str_province.equals("")) {
                            spin_province.setSelection(provinceArrayAdapter.getPosition(str_province));
                        }

                        cityGsArrayList.clear();
                        City_GS cityGs = new City_GS();
                        cityGs.setC_ID("0");
                        cityGs.setC_NAME("Select City");
                        cityGsArrayList.add(cityGs);
                        ArrayAdapter<String> cityArrayAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_item, city_array);
                        cityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        spin_city.setAdapter(cityArrayAdapter);

                        suburbGsArrayList.clear();
                        Suburb_GS suburbGs = new Suburb_GS();
                        suburbGs.setS_ID("0");
                        suburbGs.setS_NAME("Select City");
                        suburbGsArrayList.add(suburbGs);
                        ArrayAdapter<String> suburbArrayAdapter = new ArrayAdapter<>(AddAddressActivity.this, android.R.layout.simple_spinner_item, suburb_array);
                        suburbArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        spin_suburb.setAdapter(suburbArrayAdapter);

                    }
                } catch (JSONException ignored) {

                }
            }
        }, activity, Constant.GET_PROVINCE_LIST, params, true);
    }

}