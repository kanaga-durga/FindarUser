package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Adapter.CircleTransform;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.R;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class MyProfileUpdateActivity extends AppCompatActivity {


    String TAG = "TAG_MyProfileUpdateActivity";
    Activity activity;

    EditText et_p_first_name, et_p_last_name, et_p_email, et_p_phone_number, DatePicker_p, et_p_home_address, et_p_work_address;
    TextView txt_continue_p;
    static CircularImageView img_et_profile;
    ImageView img_et_camera;
    String Home_address, Work_address;
    String image;
    UICommon uicommon;

    String navigationStr = "";
    SharedPreferences User_Profile_Save;

    String ImageFileUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_update);
        View includedLayout = findViewById(R.id.back_arrow);
//        View backArrow = findViewById(R.id.back_arrow1);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbar.setTitle("");
//        toolbar.setSubtitle("");


        User_Profile_Save = getSharedPreferences("Findar", Context.MODE_PRIVATE);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,MyProfileUpdateActivity.this);

        System.out.println("**** Image File"+ Constant.USER_P_FILE);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            navigationStr = extras.getString("Navigation");
            System.out.println("**** Navigation ::"+navigationStr);
            if(navigationStr.equalsIgnoreCase("FromAccount"))
                includedLayout.setVisibility(View.GONE);
            else
                System.out.println("**** Navigation Else");


        }

        activity = MyProfileUpdateActivity.this;
        et_p_first_name = findViewById(R.id.et_p_first_name);
        et_p_last_name = findViewById(R.id.et_p_last_name);
        et_p_email = findViewById(R.id.et_p_email);
        et_p_phone_number = findViewById(R.id.et_p_phone_number);
        DatePicker_p = findViewById(R.id.DatePicker_p);
        et_p_home_address = findViewById(R.id.et_p_home_address);
        et_p_work_address = findViewById(R.id.et_p_work_address);
        txt_continue_p = findViewById(R.id.txt_continue_p);
        img_et_profile = findViewById(R.id.img_et_profile);
        img_et_camera = findViewById(R.id.img_et_camera);


        if(!User_Profile_Save.getString("USER_PROFILE_IMAGE","").equalsIgnoreCase("")){
            byte[] decodedString = Base64.decode(User_Profile_Save.getString("USER_PROFILE_IMAGE",""), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
            img_et_profile.setImageBitmap(decodedByte);

            Constant.USER_P_FILE = User_Profile_Save.getString("USER_PROFILE_IMAGE","");
            Constant.USER_P_FILE_NAME = User_Profile_Save.getString("USER_PROFILE_FILENAME","");
        }
        else {
           // img_et_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_cover_image));
            Constant.USER_P_FILE = User_Profile_Save.getString("USER_PROFILE_IMAGE","");
            Constant.USER_P_FILE_NAME = User_Profile_Save.getString("USER_PROFILE_FILENAME","");
        }

        img_et_camera.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        });

        et_p_home_address.setOnClickListener(v -> {
            if (et_p_first_name.getText().toString().equals("")) {
                et_p_first_name.setError(Constant.Please_enter_this_field);
                et_p_first_name.requestFocus();
            } else if (et_p_last_name.getText().toString().equals("")) {
                et_p_last_name.setError(Constant.Please_enter_this_field);
                et_p_last_name.requestFocus();
            } else if (et_p_email.getText().toString().equals("")) {
                et_p_email.setError(Constant.Please_enter_this_field);
                et_p_email.requestFocus();
            } else if (et_p_phone_number.getText().toString().equals("")) {
                et_p_phone_number.setError(Constant.Please_enter_this_field);
                et_p_phone_number.requestFocus();
            } else if (DatePicker_p.getText().toString().equals("")) {
                DatePicker_p.setError(Constant.Please_enter_this_field);
                DatePicker_p.requestFocus();
            } else {
                Constant.USER_F_NAME = et_p_first_name.getText().toString();
                Constant.USER_L_NAME = et_p_last_name.getText().toString();
                Constant.USER_EMAIL = et_p_email.getText().toString();
                Constant.USER_MOBILE = et_p_phone_number.getText().toString();
                Constant.USER_DOB = DatePicker_p.getText().toString();
            //    GotoUpdateWOA("HOME");
                GoToUpdateWOACheck("HOME");
            }

        });
        et_p_work_address.setOnClickListener(v -> {
            if (et_p_first_name.getText().toString().equals("")) {
                et_p_first_name.setError(Constant.Please_enter_this_field);
                et_p_first_name.requestFocus();
            } else if (et_p_last_name.getText().toString().equals("")) {
                et_p_last_name.setError(Constant.Please_enter_this_field);
                et_p_last_name.requestFocus();
            } else if (et_p_email.getText().toString().equals("")) {
                et_p_email.setError(Constant.Please_enter_this_field);
                et_p_email.requestFocus();
            } else if (et_p_phone_number.getText().toString().equals("")) {
                et_p_phone_number.setError(Constant.Please_enter_this_field);
                et_p_phone_number.requestFocus();
            } else if (DatePicker_p.getText().toString().equals("")) {
                DatePicker_p.setError(Constant.Please_enter_this_field);
                DatePicker_p.requestFocus();
            } else {
                Constant.USER_F_NAME = et_p_first_name.getText().toString();
                Constant.USER_L_NAME = et_p_last_name.getText().toString();
                Constant.USER_EMAIL = et_p_email.getText().toString();
                Constant.USER_MOBILE = et_p_phone_number.getText().toString();
                Constant.USER_DOB = DatePicker_p.getText().toString();
                //GotoUpdateWOA("WORK");
                GoToUpdateWOACheck("WORK");
            }
        });

        txt_continue_p.setOnClickListener(v -> {
            if (et_p_first_name.getText().toString().equals("")) {
                et_p_first_name.setError(Constant.Please_enter_this_field);
                et_p_first_name.requestFocus();
            } else if (et_p_last_name.getText().toString().equals("")) {
                et_p_last_name.setError(Constant.Please_enter_this_field);
                et_p_last_name.requestFocus();
            } else if (et_p_email.getText().toString().equals("")) {
                et_p_email.setError(Constant.Please_enter_this_field);
                et_p_email.requestFocus();
            } else if (et_p_phone_number.getText().toString().equals("")) {
                et_p_phone_number.setError(Constant.Please_enter_this_field);
                et_p_phone_number.requestFocus();
            } else if (DatePicker_p.getText().toString().equals("")) {
                DatePicker_p.setError(Constant.Please_enter_this_field);
                DatePicker_p.requestFocus();
            }
           // else if (User_Profile_Save.getString("USER_PROFILE_IMAGE","").equalsIgnoreCase("")) {
            else if(Constant.USER_P_FILE.equalsIgnoreCase("")){
                Toast.makeText(getApplicationContext(),R.string.error_image,Toast.LENGTH_LONG).show();
            }
            else if( Constant.Home_Address.equalsIgnoreCase("")){
                et_p_home_address.setError(Constant.Please_enter_this_field);
                et_p_home_address.setError(Constant.Please_enter_this_field);
            }
            else if( Constant.WORK_Address.equalsIgnoreCase("")){
                et_p_work_address.setError(Constant.Please_enter_this_field);
                et_p_work_address.setError(Constant.Please_enter_this_field);
            }
            else {
                Constant.USER_F_NAME = et_p_first_name.getText().toString();
                Constant.USER_L_NAME = et_p_last_name.getText().toString();
                Constant.USER_EMAIL = et_p_email.getText().toString();
                Constant.USER_MOBILE = et_p_phone_number.getText().toString();
                Constant.USER_DOB = DatePicker_p.getText().toString();
                GotoUpdateAll();
            }
        });

        SharedPreferences GetUser_id = getApplicationContext().getSharedPreferences("Findar", MODE_PRIVATE);
        Constant.USER_ID = GetUser_id.getString("USER_ID", "");
        GetProfiles(Constant.USER_ID);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                    image = ConvertBitmapToString(resizedBitmap);
                    Constant.USER_P_FILE = image;
                   // Constant.USER_P_FILE_NAME = "asasas.jpg";
                    Constant.USER_P_FILE_NAME =  System.currentTimeMillis()+"_photo.jpg";

                    SharedPreferences User_Profile_Save = getSharedPreferences("Findar", Context.MODE_PRIVATE);
                    SharedPreferences.Editor User_Profile_Save_editor = User_Profile_Save.edit();
                    User_Profile_Save_editor.putString("USER_PROFILE_IMAGE", image);
                    User_Profile_Save_editor.putString("USER_PROFILE_FILENAME", Constant.USER_P_FILE_NAME);
                    User_Profile_Save_editor.commit();

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    //method to convert the selected image to base64 encoded string

    public static String ConvertBitmapToString(Bitmap bitmap){

        Bitmap immagex=bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        byte[] imageAsBytes = Base64.decode(imageEncoded.getBytes(), Base64.DEFAULT);

        img_et_profile.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    private void GotoUpdateAll() {
       String get_file4name = Constant.USER_P_FILE.substring(0, 4);
        Map<String, String> params = new HashMap<>();
        if (get_file4name.equals("http")) {
            params.put(Constant.PID, Constant.USER_ID);
            params.put(Constant.PUSER_ID, Constant.USER_ID);
            params.put(Constant.PEMAIL, Constant.USER_EMAIL);
            params.put(Constant.P_USER_DETAIL_ID, Constant.USER_DETAIL_ID);
            params.put(Constant.PFIRST_NAME, Constant.USER_F_NAME);
            params.put(Constant.PLAST_NAME, Constant.USER_L_NAME);
            params.put(Constant.PDOB, Constant.USER_DOB);
            params.put(Constant.PMOBILE, Constant.USER_MOBILE);
            params.put(Constant.P_ADDRESS_ID_1, Constant.Home_ID);
            params.put(Constant.P_ADDRESS_1, Constant.Home_Address);
            params.put(Constant.P_PROVINCE_1, Constant.Home_Province_ID);
            params.put(Constant.P_CITY_1, Constant.Home_City_ID);
            params.put(Constant.P_SUBURB_1, Constant.Home_Suburb_ID);
            params.put(Constant.P_POST_1, Constant.Home_Postal);
            params.put(Constant.P_ADDRESS_ID_2, Constant.WORK_ID);
            params.put(Constant.P_ADDRESS_2, Constant.WORK_Address);
            params.put(Constant.P_PROVINCE_2, Constant.WORK_Province_ID);
            params.put(Constant.P_CITY_2, Constant.WORK_City_ID);
            params.put(Constant.P_SUBURB_2, Constant.WORK_Suburb_ID);
            params.put(Constant.P_POST_2, Constant.WORK_Postal);
        } else {
            if(Constant.USER_P_FILE.equalsIgnoreCase(""))
                Constant.USER_P_FILE = User_Profile_Save.getString("USER_PROFILE_IMAGE","");
        if(Constant.USER_P_FILE_NAME.equalsIgnoreCase(""))
            Constant.USER_P_FILE_NAME = User_Profile_Save.getString("USER_PROFILE_FILENAME","");

        System.out.println("****** Image File From Local ::"+Constant.USER_P_FILE);

            //Map<String, String> params = new HashMap<>();
            params.put(Constant.PID, Constant.USER_ID);
            params.put(Constant.PUSER_ID, Constant.USER_ID);
            params.put(Constant.PEMAIL, Constant.USER_EMAIL);
            params.put(Constant.P_USER_DETAIL_ID, Constant.USER_DETAIL_ID);
            params.put(Constant.PFIRST_NAME, Constant.USER_F_NAME);
            params.put(Constant.PLAST_NAME, Constant.USER_L_NAME);
            params.put(Constant.PDOB, Constant.USER_DOB);
            params.put(Constant.PMOBILE, Constant.USER_MOBILE);
            params.put(Constant.PFILE, Constant.USER_P_FILE);
            params.put(Constant.PFILE_NAME, Constant.USER_P_FILE_NAME);
            params.put(Constant.P_ADDRESS_ID_1, Constant.Home_ID);
            params.put(Constant.P_ADDRESS_1, Constant.Home_Address);
            params.put(Constant.P_PROVINCE_1, Constant.Home_Province_ID);
            params.put(Constant.P_CITY_1, Constant.Home_City_ID);
            params.put(Constant.P_SUBURB_1, Constant.Home_Suburb_ID);
            params.put(Constant.P_POST_1, Constant.Home_Postal);
            params.put(Constant.P_ADDRESS_ID_2, Constant.WORK_ID);
            params.put(Constant.P_ADDRESS_2, Constant.WORK_Address);
            params.put(Constant.P_PROVINCE_2, Constant.WORK_Province_ID);
            params.put(Constant.P_CITY_2, Constant.WORK_City_ID);
            params.put(Constant.P_SUBURB_2, Constant.WORK_Suburb_ID);
            params.put(Constant.P_POST_2, Constant.WORK_Postal);
        }


        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Profile Update" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        if(navigationStr.equalsIgnoreCase("FromAccount")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }


                        SharedPreferences User_Profile_Save = getSharedPreferences("Findar", Context.MODE_PRIVATE);
                        SharedPreferences.Editor User_Profile_Save_editor = User_Profile_Save.edit();
                        User_Profile_Save_editor.putString("USER_PROFILE_IMAGE", "");
                        User_Profile_Save_editor.putString("USER_PROFILE_FILENAME", "");
                        User_Profile_Save_editor.commit();

                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_Profile" + e.toString());
                }
            }
        }, activity, Constant.UPDATE_USER_DETAILS, params, true);
    }

    private void GoToUpdateWOACheck(String getHorW){
        Intent intent = new Intent(getApplicationContext(), AddressMapActivity.class);
        intent.putExtra("SET_ADDRESS", getHorW);
        startActivity(intent);
    }

    private void GotoUpdateWOA(String getHorW) {
//        Log.d("ID_ID_ID",Constant.USER_ID+Constant.USER_EMAIL+Constant.USER_DETAIL_ID+Constant.USER_F_NAME+Constant.USER_L_NAME+Constant.USER_DOB+Constant.USER_MOBILE+Constant.USER_P_FILE+Constant.USER_P_FILE_NAME);
        String get_file4name = "";
        if(!Constant.USER_P_FILE.equalsIgnoreCase(""))
            Constant.USER_P_FILE.substring(0, 4);

        Map<String, String> params = new HashMap<>();
        if (get_file4name.equals("http")) {
            params.put(Constant.PID, Constant.USER_ID);
            params.put(Constant.PUSER_ID, Constant.USER_ID);
            params.put(Constant.PEMAIL, Constant.USER_EMAIL);
            params.put(Constant.P_USER_DETAIL_ID, Constant.USER_DETAIL_ID);
            params.put(Constant.PFIRST_NAME, Constant.USER_F_NAME);
            params.put(Constant.PLAST_NAME, Constant.USER_L_NAME);
            params.put(Constant.PDOB, Constant.USER_DOB);
            params.put(Constant.PMOBILE, Constant.USER_MOBILE);
        } else {
            params.put(Constant.PID, Constant.USER_ID);
            params.put(Constant.PUSER_ID, Constant.USER_ID);
            params.put(Constant.PEMAIL, Constant.USER_EMAIL);
            params.put(Constant.P_USER_DETAIL_ID, Constant.USER_DETAIL_ID);
            params.put(Constant.PFIRST_NAME, Constant.USER_F_NAME);
            params.put(Constant.PLAST_NAME, Constant.USER_L_NAME);
            params.put(Constant.PDOB, Constant.USER_DOB);
            params.put(Constant.PMOBILE, Constant.USER_MOBILE);
            params.put(Constant.PFILE, Constant.USER_P_FILE);
            params.put(Constant.PFILE_NAME, Constant.USER_P_FILE_NAME);
        }


        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Profile" + jsonObject);

                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        Intent intent = new Intent(getApplicationContext(), AddressMapActivity.class);
                        intent.putExtra("SET_ADDRESS", getHorW);
                        startActivity(intent);
                    } else if (status.equals("0")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_Profile" + e.toString());
                }
            }
        }, activity, Constant.UPDATE_USER_DETAILS, params, true);
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
                        Constant.USER_DETAIL_ID = result_profile.getString("usersdetailid");
                        Constant.USER_EMAIL = result_profile.getString("email");
                        Constant.USER_DOB = result_profile.getString("dob");
                        Constant.USER_MOBILE = result_profile.getString("mobile_phone");
                        Constant.USER_F_NAME = result_profile.getString("name");
                        Constant.USER_L_NAME = result_profile.getString("surname");
                        Constant.USER_P_FILE = result_profile.getString("file");
                        ImageFileUrl = result_profile.getString("file");
                        Constant.Home_Postal = result_profile.getString("postal_code1");
                        Constant.Home_ID = result_profile.getString("addressid1");
                        Constant.Home_Address = result_profile.getString("address1");
                        Constant.Home_Province_ID = result_profile.getString("provinceid1");
                        Constant.Home_Province_Name = result_profile.getString("province1");
                        Constant.Home_City_ID = result_profile.getString("cityid1");
                        Constant.Home_City_Name = result_profile.getString("city1");
                        Constant.Home_Suburb_ID = result_profile.getString("suburbid1");
                        Constant.Home_Suburb_Name = result_profile.getString("suburb1");
                        Constant.WORK_Postal = result_profile.getString("postal_code2");
                        Constant.WORK_ID = result_profile.getString("addressid2");
                        Constant.WORK_Address = result_profile.getString("address2");
                        Constant.WORK_Province_Name = result_profile.getString("province2");
                        Constant.WORK_City_Name = result_profile.getString("city2");
                        Constant.WORK_Suburb_Name = result_profile.getString("suburb2");


                        if (Constant.USER_P_FILE.equals("null") || Constant.USER_P_FILE.equalsIgnoreCase("")) {
                            Constant.USER_P_FILE = "";
                            Constant.USER_P_FILE_NAME = "";
//                            Picasso.get().load(Constant.USER_P_FILE).into(img_et_profile);
                        }

                        else {
                            if(!User_Profile_Save.getString("USER_PROFILE_IMAGE", "").equalsIgnoreCase("")) {
                                Constant.USER_P_FILE = User_Profile_Save.getString("USER_PROFILE_IMAGE", "");
                                Constant.USER_P_FILE_NAME = User_Profile_Save.getString("USER_PROFILE_FILENAME", "");
                                Log.d("filefile", Constant.USER_P_FILE);
                                System.out.println("File Url ::"+User_Profile_Save.getString("USER_PROFILE_IMAGE", ""));

                                if(!Constant.USER_P_FILE.contains("http")){
                                    byte[] decodedString = Base64.decode( Constant.USER_P_FILE, Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    img_et_profile.setImageBitmap(decodedByte);
                                }
                                else
                                Picasso.get().load(Constant.USER_P_FILE).placeholder(R.drawable.back_arrow).transform(new CircleTransform()).into(img_et_profile);
//                            Picasso.get().load(new File(file)).transform(new CircleTransform()).into(img_my_profile);
                            }
                            else
                            {
                                System.out.println("File Url Else::");
                                if(!result_profile.getString("file").equalsIgnoreCase("")) {
                                    Picasso.get().load(result_profile.getString("file")).placeholder(R.drawable.back_arrow).transform(new CircleTransform()).into(img_et_profile);
                                    Constant.USER_P_FILE = result_profile.getString("file");
                                }
                            }
                        }



                        if (Constant.Home_ID.equals("null")) {
                            Constant.Home_ID = "";
                        }
                        if (Constant.Home_Address.equals("null")) {
                            Constant.Home_Address = "";
                        } else {
                            Home_address = Constant.Home_Address;
                        }
                        if (Constant.Home_Province_Name.equals("null")) {
                            Constant.Home_Province_Name = "";
                        } else {
                            Home_address = Constant.Home_Address + ", " + Constant.Home_Province_Name;
                        }
                        if (Constant.Home_City_Name.equals("null")) {
                            Constant.Home_City_Name = "";
                        } else {
                            Home_address = Constant.Home_Address + ", " + Constant.Home_Province_Name + ", " + Constant.Home_City_Name;
                        }
                        if (Constant.Home_Suburb_Name.equals("null")) {
                            Constant.Home_Suburb_Name = "";
                        } else {
                            Home_address = Constant.Home_Address + ", " + Constant.Home_Province_Name + ", " + Constant.Home_City_Name + ", " + Constant.Home_Suburb_Name;
                        }
                        if (Constant.Home_Postal.equals("null")) {
                            Constant.Home_Postal = "";
                        } else {
                            Home_address = Constant.Home_Address + ", " + Constant.Home_Province_Name + ", " + Constant.Home_City_Name + ", " + Constant.Home_Suburb_Name + ", " + Constant.Home_Postal;
                        }
                        if (Constant.WORK_ID.equals("null")) {
                            Constant.WORK_ID = "";
                        }
                        if (Constant.WORK_Address.equals("null")) {
                            Constant.WORK_Address = "";
                        } else {
                            Work_address = Constant.WORK_Address;
                        }
                        if (Constant.WORK_Province_Name.equals("null")) {
                            Constant.WORK_Province_Name = "";
                        } else {
                            Work_address = Constant.WORK_Address + ", " + Constant.WORK_Province_Name;
                        }
                        if (Constant.WORK_City_Name.equals("null")) {
                            Constant.WORK_City_Name = "";
                        } else {
                            Work_address = Constant.WORK_Address + ", " + Constant.WORK_Province_Name + ", " + Constant.WORK_City_Name;
                        }
                        if (Constant.WORK_Suburb_Name.equals("null")) {
                            Constant.WORK_Suburb_Name = "";
                        } else {
                            Work_address = Constant.WORK_Address + ", " + Constant.WORK_Province_Name + ", " + Constant.WORK_City_Name + ", " + Constant.WORK_Suburb_Name;
                        }
                        if (Constant.WORK_Postal.equals("null")) {
                            Constant.WORK_Postal = "";
                        } else {
                            Work_address = Constant.WORK_Address + ", " + Constant.WORK_Province_Name + ", " + Constant.WORK_City_Name + ", " + Constant.WORK_Suburb_Name + ", " + Constant.WORK_Postal;
                        }

                        et_p_first_name.setText(Constant.USER_F_NAME);
                        et_p_last_name.setText(Constant.USER_L_NAME);
                        et_p_email.setText(Constant.USER_EMAIL);
                        et_p_phone_number.setText(Constant.USER_MOBILE);
                        et_p_home_address.setText(Home_address);
                        et_p_work_address.setText(Work_address);
                        DatePicker_p.setText(Constant.USER_DOB);
                    }

                } catch (JSONException e) {
                    Log.d(TAG, "Result_Profile" + e);
                }
            }
        }, activity, Constant.GET_USER_DETAILS, params, true);
    }
}