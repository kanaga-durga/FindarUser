package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Adapter.CustomRecyclearAdapterImages;
import org.findar.user.Adapter.CustomRecyclearAdapterReasons;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.Reasons;
import org.findar.user.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobInformation extends AppCompatActivity {
    UICommon uicommon;
    String TAG = "JobInformation";
    public static JobInformation jobInformation;
    TextView textDateTime,textStatus,textAddress,textCompany,textProblemContent,textDesContent;
    ImageView imageView1;
    RelativeLayout parentLayout;
    TextView txt_cancel;
    ArrayList<Reasons> reasonsArrayList;
    CustomRecyclearAdapterReasons mAdapterReasons;
    Activity activity;
    ArrayList<String> imagesList;
    RecyclerView recycleViewImages;
    ImageView imageChat;
    TextView textViewYou,textViewService;
    RelativeLayout RelativeYou,RelativeService;
    View viewYou,viewService;
    TextView txt_track;
    LinearLayout LinearTab,LinearTabLine,txt_trackLinear;
    RelativeLayout linearTimer;
    ImageView imageViewcall;
    String contactno;
    TextView textTimer,et_coc,et_notes;
    RelativeLayout relativeHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_information);
        View includedLayout = findViewById(R.id.back_arrow);

        activity = JobInformation.this;
        jobInformation = JobInformation.this;

        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,JobInformation.this);

        jobInformation = this;
        textDateTime = findViewById(R.id.textDateTime);
        textStatus = findViewById(R.id.textStatus);
        textAddress = findViewById(R.id.textAddress);
        textCompany = findViewById(R.id.textCompany);
        textProblemContent = findViewById(R.id.textProblemContent);
        textDesContent = findViewById(R.id.textDesContent);

        imageView1 = findViewById(R.id.imageView1);
        parentLayout = findViewById(R.id.parentLayout);

        txt_cancel = findViewById(R.id.txt_cancel);

        recycleViewImages = findViewById(R.id.recycleViewImages);
        imageChat = findViewById(R.id.imageChat);

        textViewYou = findViewById(R.id.textViewYou);
        textViewService  = findViewById(R.id.textViewService);
        RelativeYou = findViewById(R.id.RelativeYou);
        RelativeService = findViewById(R.id.RelativeService);
        viewYou = findViewById(R.id.viewYou);
        viewService = findViewById(R.id.viewService);

        txt_track = findViewById(R.id.txt_track);
        txt_trackLinear = findViewById(R.id.txt_trackLinear);
        LinearTab = findViewById(R.id.LinearTab);
        LinearTabLine = findViewById(R.id.LinearTabLine);
        linearTimer = findViewById(R.id.linearTimer);

        imageViewcall = findViewById(R.id.imageViewcall);

        textTimer = findViewById(R.id.textTimer);
        et_coc = findViewById(R.id.et_coc);
        et_notes = findViewById(R.id.et_notes);

        relativeHead = findViewById(R.id.relativeHead);

        GetJobInformation(Constant.USER_ID,Constant.JOBID);


        txt_cancel.setOnClickListener(v->{
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog_cancel);
            //dialog.setTitle("Title...");
            Button dialogCancelButton = (Button) dialog.findViewById(R.id.dialogButtonCancel);
            Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    //GoToCancel(Constant.USER_ID, Constant.JOBID , Constant.COMPANYID);
                }
            });

            dialogButtonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    GetCancelReason(Constant.USER_ID);

                    //GoToCancel(Constant.USER_ID, Constant.JOBID , Constant.COMPANYID);
                }
            });

            dialog.show();

        });

        imageChat.setOnClickListener(v->{
            Intent intentChat = new Intent(JobInformation.this,ChatActivity.class);
            startActivity(intentChat);
        });

        textViewYou.setOnClickListener(v->{
            RelativeYou.setVisibility(View.VISIBLE);
            RelativeService.setVisibility(View.GONE);
            textViewYou.setTextColor(getResources().getColor(R.color.primary_color));
            viewYou.setBackgroundColor(getResources().getColor(R.color.primary_color));
            textViewService.setTextColor(getResources().getColor(R.color.terms_border));
            viewService.setBackgroundColor(getResources().getColor(R.color.terms_border));
        });
        textViewService.setOnClickListener(v->{
            RelativeYou.setVisibility(View.GONE);
            RelativeService.setVisibility(View.VISIBLE);
            textViewService.setTextColor(getResources().getColor(R.color.primary_color));
            viewService.setBackgroundColor(getResources().getColor(R.color.primary_color));

            textViewYou.setTextColor(getResources().getColor(R.color.terms_border));
            viewYou.setBackgroundColor(getResources().getColor(R.color.terms_border));
        });

        txt_trackLinear.setOnClickListener(v->{
            if(Constant.JOBSTATUS_MSG.equalsIgnoreCase("Current") || Constant.JOBSTATUS_MSG.equalsIgnoreCase("On Route")) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
            }
        });
        imageViewcall.setOnClickListener(v->{
            if(!contactno.equalsIgnoreCase("") && !contactno.equalsIgnoreCase("null")) {
                callPhoneNumber(contactno);
            }
            else
                Toast.makeText(getApplicationContext(),R.string.error_message_phone,Toast.LENGTH_LONG).show();
        });

        txt_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobInformation.this,TrackOrderActivity.class);
                startActivity(intent);
            }
        });


    }



    public void callPhoneNumber(String contactno)
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(JobInformation.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" +Long.parseLong(contactno)));
                startActivity(callIntent);
            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" +Long.parseLong(contactno)));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(!contactno.equalsIgnoreCase("")) {
                    callPhoneNumber(contactno);
                }

            } else {
                Log.e(TAG, "Permission not Granted");
            }
        }
    }



    private void DisplayReasonDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_reason_dialog);
        //dialog.setTitle("Title...");
        ImageView dialogCancelButton = (ImageView) dialog.findViewById(R.id.imageCancel);
        RecyclerView recycleViewReason = (RecyclerView)dialog.findViewById(R.id.recycleViewReason);

        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //GoToCancel(Constant.USER_ID, Constant.JOBID , Constant.COMPANYID);
            }
        });

        System.out.println("Reason List Size ::"+reasonsArrayList.size());
        recycleViewReason.setVisibility(View.VISIBLE);
        recycleViewReason.setHasFixedSize(true);
        LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(this);
        recycleViewReason.setLayoutManager(layoutManagerSubProblems);
        CustomRecyclearAdapterReasons mAdapterReasons = new CustomRecyclearAdapterReasons(JobInformation.this, reasonsArrayList);
        recycleViewReason.setAdapter(mAdapterReasons);

        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void GetJobInformation(String userId,String jobid) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);
        params.put(Constant.P_JOB_ID,jobid);
        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                parentLayout.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_JobInformation" + jsonObject);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        JSONObject result_company = jsonObject.getJSONObject("result");
                        textDateTime.setText(result_company.getString("job_date_time"));
                        Constant.JOBSTATUS_MSG = result_company.getString("job_status");
                        textStatus.setText(result_company.getString("job_status"));
                        textAddress.setText(result_company.getString("user_location_address"));
                        textCompany.setText(result_company.getString("company_name"));
                        Constant.COMPANYNAME = result_company.getString("company_name");
                        textProblemContent.setText(result_company.getString("problem_name")+"\n"+result_company.getString("sub_problem_name"));
                        textDesContent.setText(result_company.getString("problem_description"));
                        contactno = result_company.getString("contact_number");
                        JSONArray result_images = result_company.getJSONArray("images");
                        imagesList = new ArrayList<String>();
                        if (result_images.length() != 0) {
                            for (int j = 0; j < result_images.length(); j++) {
                                //JSONObject imageRecord = result_images.getJSONObject(j);
                               imagesList.add(result_images.get(j).toString());
                            }
                        }

                        if(!result_company.getString("plumber_profile_picture").equalsIgnoreCase("") && result_company.getString("plumber_profile_picture")!=null)
                            // if(!result_company.getString("plumber_profile_picture").equalsIgnoreCase("")){
//                            imageView = findViewById(R.id.imageView);
//                            Picasso.get()
//                                .load(result_company.getString("plumber_profile_picture"))
//                                .fit()
//                                .into(imageView1);

                            System.out.println("ImageView"+imageView1);
                        System.out.println("Profile Picture "+result_company.getString("plumber_profile_picture"));
                      //  Picasso.get().load(result_company.getString("plumber_profile_picture")).into(imageView1);


                        if(result_company.getString("job_status").equalsIgnoreCase("Upcoming")){
                            txt_track.setClickable(false);
                            txt_track.setFocusable(false);
                            txt_track.setAlpha(0.8f);
                            txt_trackLinear.setAlpha(0.6f);
                          //  txt_track.setBackground(getResources().getDrawable(R.drawable.button_transparent));
                            LinearTab.setVisibility(View.GONE);
                            LinearTabLine.setVisibility(View.GONE);
                            linearTimer.setVisibility(View.GONE);

                            relativeHead.setBackground(getResources().getDrawable(R.drawable.history_upcoming_round));
                            textStatus.setTextColor(getResources().getColor(R.color.text_blue));
                        }
                        else if(result_company.getString("job_status").equalsIgnoreCase("Current")||Constant.JOBSTATUS_MSG.equalsIgnoreCase("On Route")){
                            LinearTab.setVisibility(View.GONE);
                            LinearTabLine.setVisibility(View.GONE);
                            linearTimer.setVisibility(View.GONE);

                            relativeHead.setBackground(getResources().getDrawable(R.drawable.history_route_round));
                            textStatus.setTextColor(getResources().getColor(R.color.textViolet));
                        }
                        else if(result_company.getString("job_status").equalsIgnoreCase("Completed")){
                            if(!result_company.getString("time_taken").equalsIgnoreCase("null"))
                            textTimer.setText(result_company.getString("time_taken"));

                            if(!result_company.getString("coc_number").equalsIgnoreCase("null"))
                                et_coc.setText(result_company.getString("time_taken"));

                            if(!result_company.getString("notes").equalsIgnoreCase("null"))
                                et_notes.setText(result_company.getString("notes"));

                            txt_track.setVisibility(View.GONE);
                            txt_cancel.setVisibility(View.GONE);

                            relativeHead.setBackground(getResources().getDrawable(R.drawable.history_complete_round));
                            textStatus.setTextColor(getResources().getColor(R.color.textGreen));
                        }

                        else if(result_company.getString("job_status").equalsIgnoreCase("Busy Working")) {
                            if (!result_company.getString("time_taken").equalsIgnoreCase("null"))
                                textTimer.setText(result_company.getString("time_taken"));

                            if (!result_company.getString("coc_number").equalsIgnoreCase("null"))
                                et_coc.setText(result_company.getString("time_taken"));

                            if (!result_company.getString("notes").equalsIgnoreCase("null"))
                                et_notes.setText(result_company.getString("notes"));

                            txt_track.setVisibility(View.GONE);
                            txt_cancel.setVisibility(View.GONE);

                            relativeHead.setBackground(getResources().getDrawable(R.drawable.history_busy_round));
                            textStatus.setTextColor(getResources().getColor(R.color.textOrange));
                        }

                        else if(result_company.getString("job_status").equalsIgnoreCase("Cancelled")) {
                            LinearTab.setVisibility(View.GONE);
                            LinearTabLine.setVisibility(View.GONE);
                            linearTimer.setVisibility(View.GONE);

                            relativeHead.setBackground(getResources().getDrawable(R.drawable.history_cancel_round));
                            textStatus.setTextColor(getResources().getColor(R.color.logout_color1));

                        }
                        }

//                        System.out.println("Image Size ::"+imagesList.size());

                    if(imagesList!=null) {
                        recycleViewImages.setVisibility(View.VISIBLE);
                        recycleViewImages.setHasFixedSize(true);
                        LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(JobInformation.this, LinearLayoutManager.HORIZONTAL, false);
                        recycleViewImages.setLayoutManager(layoutManagerSubProblems);
                        CustomRecyclearAdapterImages mAdapterImages = new CustomRecyclearAdapterImages(JobInformation.this, imagesList);
                        recycleViewImages.setAdapter(mAdapterImages);
                    }





                } catch (JSONException e) {
                    Log.d(TAG, "Result_JobInformation" + e);
                }
            }
        }, JobInformation.this, Constant.JOBINFORMATION, params, true);
    }


    /******************** GET CANCEL REASON LIST ************************/
    @SuppressLint("SetTextI18n")
    private void GetCancelReason(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, userId);
        reasonsArrayList = new ArrayList<Reasons>();
        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Reasons" + jsonObject);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")) {
                        //JSONObject result_reasons = jsonObject.getJSONObject("result");
                        JSONArray result_reasons = jsonObject.getJSONArray("result");
                        for (int i = 0; i < result_reasons.length(); i++) {
                            JSONObject reasonsJSONObject = result_reasons.getJSONObject(i);
                            Reasons reasons = new Reasons();
                            reasons.setId(reasonsJSONObject.getString("reason_id"));
                            reasons.setDescription(reasonsJSONObject.getString("reason_description"));
                            reasonsArrayList.add(reasons);
                        }

                        DisplayReasonDialog();
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "Result_JobInformation" + e);
                }
            }
        }, JobInformation.this, Constant.REASONCANCEL, params, true);
    }

    /************************* ***********************/
    public void GoToCancelJob(){
        Map<String, String> params = new HashMap<>();
        params.put(Constant.PUSER_ID, Constant.USER_ID);
        params.put(Constant.P_COMPANYID, Constant.COMPANYID);
        params.put(Constant.P_JOB_ID, Constant.JOBID);
        params.put(Constant.P_REASON_ID, Constant.REASONID);

        System.out.println("Constant.PUSER_ID"+Constant.USER_ID);
        System.out.println("Constant.P_COMPANYID"+Constant.COMPANYID);
        System.out.println("Constant.P_JOB_ID"+Constant.JOBID);
        System.out.println("Constant.P_REASON_ID"+Constant.REASONID);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_Job_Cancel" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    if (status.equals("1")) {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "Result_JobInformation" + e);
                }
            }
        }, JobInformation.this, Constant.CANCELJOB, params, true);

    }
}