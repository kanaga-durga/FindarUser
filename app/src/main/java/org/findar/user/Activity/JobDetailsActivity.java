package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Adapter.CustomRecyclearAdapterCustomer;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.Companies;
import org.findar.user.Model.Customer_GS;

import org.findar.user.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobDetailsActivity extends AppCompatActivity {
    Bitmap mbitmap=null;
    UICommon uicommon;
    String TAG = "JobDetailsActivity";
    List<Companies> companiesList;
    TextView textCompany,textStatus,textCallout,textPerHour,textTime;
    RatingBar ratingBar,ratingBarServices,ratingBarPunctuality,ratingBarFriendly;
    TextView textAboutContent,textSpecContent;
    List<Customer_GS> customerGsList;
    RecyclerView recycleViewCustomer;
    CustomRecyclearAdapterCustomer mAdapterCustomer;
    ImageView imageViewFull,imageView;
    public static JobDetailsActivity jobDetailsActivity;
    TextView txt_book;
    TextView textName,textRating;
    RatingBar ratingBarDialog;
    String CompanyName =  "",StartRating = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);
        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,JobDetailsActivity.this);

        jobDetailsActivity = this;

//        ImageView mimageView=(ImageView) findViewById(R.id.imageViewFull);

        textCompany = findViewById(R.id.textCompany);
        textStatus = findViewById(R.id.textStatus);
        textCallout = findViewById(R.id.textCallout);
        textPerHour = findViewById(R.id.textPerHour);
        ratingBar = findViewById(R.id.ratingBar);
        ratingBarServices = findViewById(R.id.ratingBarServices);
        ratingBarPunctuality = findViewById(R.id.ratingBarPunctuality);
        ratingBarFriendly = findViewById(R.id.ratingBarFriendly);
        textTime = findViewById(R.id.textTime);
        textAboutContent = findViewById(R.id.textAboutContent);
        textSpecContent = findViewById(R.id.textSpecContent);

        imageViewFull = findViewById(R.id.imageViewFull);
        imageView = findViewById(R.id.imageView);
        txt_book = findViewById(R.id.txt_book);

        recycleViewCustomer = findViewById(R.id.recycleViewCustomer);



        GetCompanyDetails(Constant.USER_ID,Constant.COMPANYID);

        txt_book.setOnClickListener(v->{

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.text);
            textName = (TextView)dialog.findViewById(R.id.textName);

            textName.setText(CompanyName);

            ratingBarDialog = (RatingBar)dialog.findViewById(R.id.ratingBar);
            textRating = (TextView)dialog.findViewById(R.id.textRating);

            if(!StartRating.equalsIgnoreCase("")){
            ratingBarDialog.setRating(Float.parseFloat(StartRating));
            textRating.setText("("+String.valueOf(Float.parseFloat(StartRating)+")"));
            }

           // text.setText("Android custom dialog example!");
            ImageView image = (ImageView) dialog.findViewById(R.id.image);
          //  image.setImageResource(R.drawable.ic_launcher);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoToBookNow(Constant.USER_ID, Constant.JOBID , Constant.COMPANYID);
                }
            });
            dialog.show();

            Button dialogButtonCancel = (Button)dialog.findViewById(R.id.dialogButtonCancel);
            dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        });
    }

    @SuppressLint("SetTextI18n")
    private void GetCompanyDetails(String userId,String companyId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);
        params.put(Constant.P_COMPANYID,companyId);
        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Company_Details" + jsonObject);
                    String status = jsonObject.getString("status");
//                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        JSONObject result_company = jsonObject.getJSONObject("result");
                       // JSONArray potential_jobs = result_job.getJSONArray("potential_jobs");
                        Log.d(TAG, "Result_Company_Details" + result_company);
                        Log.d(TAG, "Result_Company_Details" + result_company.length());
                        companiesList = new ArrayList<Companies>();
                      //  for(int i=0;i<result_company.length();i++){
                            Companies company = new Companies();
                           // company.setCompanyId(result_company.getString("company_id"));
                            company.setCompanyName(result_company.getString("company_name"));
                            company.setCompanyprofileimage(result_company.getString("company_profile_image"));
                            company.setStarrating(result_company.getString("star_rating"));
                         
                            company.setCalloutprice(result_company.getString("call_out_price"));
                            company.setPriceperhour(result_company.getString("price_per_hour"));
                            company.setAvailabletime(result_company.getString("available_time"));

                            company.setCompany_cover_image(result_company.getString("company_cover_image"));
                            company.setAvailabity(result_company.getString("availabity"));
                            company.setStar_rating_services(result_company.getString("star_rating_services"));
                            company.setStar_rating_punctuality(result_company.getString("star_rating_punctuality"));
                            company.setStar_rating_friendliness(result_company.getString("star_rating_friendliness"));
                            company.setAbout_us_description(result_company.getString("about_us_description"));
                            company.setSpecializations(result_company.getString("specializations"));
                            companiesList.add(company);


                        // Picasso.get().load("http://staging.findar.co.za/images/d519c6a21116b6a4871190f8beccd6e5.jpg").into(imageViewFull);

                            textCompany.setText(result_company.getString("company_name"));
                            CompanyName = result_company.getString("company_name");

                            textStatus.setText(result_company.getString("availabity"));
                            textCallout.setText(result_company.getString("call_out_price"));
                            textPerHour.setText(result_company.getString("price_per_hour"));
                            textTime.setText(result_company.getString("available_time"));

                        if(result_company.getString("star_rating")!=""&&result_company.getString("star_rating")!=null)
                        {
                            ratingBar.setRating(Float.parseFloat(result_company.getString("star_rating")));
                            StartRating = result_company.getString("star_rating");

                        }

                        if(result_company.getString("star_rating_services")!=""&&result_company.getString("star_rating_services")!=null)
                            ratingBarServices.setRating(Float.parseFloat(result_company.getString("star_rating_services")));

                        if(result_company.getString("star_rating_punctuality")!=""&&result_company.getString("star_rating_punctuality")!=null)
                            ratingBarPunctuality.setRating(Float.parseFloat(result_company.getString("star_rating_punctuality")));

                        if(result_company.getString("star_rating_friendliness")!=""&&result_company.getString("star_rating_friendliness")!=null)
                            ratingBarFriendly.setRating(Float.parseFloat(result_company.getString("star_rating_friendliness")));

                        textAboutContent.setText(result_company.getString("about_us_description"));
                        textSpecContent.setText(result_company.getString("specializations"));

                        Transformation transformation = new RoundedTransformationBuilder()
                                .borderColor(Color.WHITE)
                                .borderWidthDp(2)
                                .cornerRadiusDp(10)
                                .oval(false)
                                .build();

//                        Picasso.get()
//                                .load("http://staging.findar.co.za/images/d519c6a21116b6a4871190f8beccd6e5.jpg")
//                                .fit()
//                                .transform(transformation)
//                                .into(imageViewFull);

                        if(!result_company.getString("company_profile_image").equalsIgnoreCase(""))
                        Picasso.get()
                                .load(result_company.getString("company_profile_image"))
                                .fit()
                                .transform(transformation)
                                .into(imageViewFull);


                        JSONArray customer_feeds = result_company.getJSONArray("customer_feedback");
                        customerGsList = new ArrayList<Customer_GS>();
                        for (int i = 0; i < customer_feeds.length(); i++) {
                            Customer_GS cusObj = new Customer_GS();
                            JSONObject customerRecord = customer_feeds.getJSONObject(i);
                            cusObj.setCustomer_image(customerRecord.getString("customer_image"));
                            cusObj.setCustomer_feedback_description(customerRecord.getString("customer_feedback_description"));
                            cusObj.setCustomer_name(customerRecord.getString("customer_name"));
                            cusObj.setCustomer_rating(customerRecord.getString("customer_rating"));
                            customerGsList.add(cusObj);
                        }

                        recycleViewCustomer.setVisibility(View.VISIBLE);
                        recycleViewCustomer.setHasFixedSize(true);
                        LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(JobDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false);
                        recycleViewCustomer.setLayoutManager(layoutManagerSubProblems);
                        mAdapterCustomer = new CustomRecyclearAdapterCustomer(JobDetailsActivity.this, customerGsList);
                        recycleViewCustomer.setAdapter(mAdapterCustomer);

                       // }


                    }
                } catch (JSONException e) {
                    Log.d(TAG, "Result_JobList" + e);
                }
            }
        }, JobDetailsActivity.this, Constant.COMPANYDETAILS, params, true);
    }

    private void GoToBookNow(String userid,String jobID,String companyId){
        Map<String, String> params = new HashMap<>();
        params.put(Constant.P_COMPANY_ID, companyId);
        params.put(Constant.P_JOB_ID, jobID);
        params.put(Constant.PUSER_ID, userid);


        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Book Nown"+jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        JSONObject result_obj = jsonObject.getJSONObject("result");
                        Intent intent = new Intent(JobDetailsActivity.this,MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(JobDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException ignored) {

                }
            }
        }, JobDetailsActivity.this, Constant.BOOKJOB, params, true);
    }
}