package org.findar.user.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.findar.user.Activity.MainActivity;
import org.findar.user.Helper.ApiConfig;
import org.findar.user.Helper.Constant;
import org.findar.user.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Set_Date_Time_Fragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    int day, day1, month, year, hour, minute;
    int my_day, my_Year, my_Hour, myMinute;
    TextView txt_date, txt_time,txt_done, txt_set_date_time_soon,txt_set_date_time_continue;
    String myMonth, my_day1;
    RelativeLayout RL_DATE_TIME_PIC;

    ImageView back_arrow;
    String TAG = "TAG_CREATEJOBFRAGMENT";
    public Set_Date_Time_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set__date__time_, container, false);
        RelativeLayout RL_DATE_TIME = view.findViewById(R.id.RL_DATE_TIME);
        txt_date = view.findViewById(R.id.txt_date);
        txt_time = view.findViewById(R.id.txt_time);
        txt_done = view.findViewById(R.id.txt_done);
        txt_set_date_time_soon = view.findViewById(R.id.txt_set_date_time_soon);
        txt_set_date_time_continue = view.findViewById(R.id.txt_set_date_time_continue);
        RL_DATE_TIME_PIC = view.findViewById(R.id.RL_DATE_TIME_PIC);

        back_arrow = view.findViewById(R.id.back_arrow);

        txt_set_date_time_continue.setOnClickListener(v -> RL_DATE_TIME_PIC.setVisibility(View.VISIBLE));

        txt_set_date_time_soon.setOnClickListener(v -> {
//            Intent intent = new Intent(getContext(), Service_Types_Activity.class);
//            startActivity(intent);
            Constant.ASSOONAS = "1";
            GotoCreateJob(Constant.USER_ID, Constant.SL_ID,Constant.PL_ID,  Constant.SPL_ID,  Constant.DESCRIBE_PROBLEM ,  Constant.ORDERNO, Constant.ADDRESS, Constant.PROVINCE, Constant.CITY, Constant.SUBURB,
                     Constant.POSTALCODE, Constant.ASSOONAS, Constant.JOBDATE, Constant.JOBTIME, Constant.LATITUDE, Constant.LONGITUDE, Constant.FILECOUNT, Constant.FILE, Constant.FILENAME);

        });

        txt_done.setOnClickListener(v -> {
            if (txt_date.getText().toString().equals("") || txt_time.getText().toString().equals("")){
                txt_time.setError(Constant.DatePick_Error);
            }else {
//                Intent intent = new Intent(getContext(), Service_Types_Activity.class);
//                startActivity(intent);
                Constant.ASSOONAS = "0";
                GotoCreateJob(Constant.USER_ID, Constant.SL_ID,Constant.PL_ID,  Constant.SPL_ID,  Constant.DESCRIBE_PROBLEM ,  Constant.ORDERNO, Constant.ADDRESS, Constant.PROVINCE, Constant.CITY, Constant.SUBURB,
                        Constant.POSTALCODE, Constant.ASSOONAS, Constant.JOBDATE, Constant.JOBTIME, Constant.LATITUDE, Constant.LONGITUDE, Constant.FILECOUNT, Constant.FILE, Constant.FILENAME);
            }
        });
        RL_DATE_TIME.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            day1 = calendar.get(Calendar.DAY_OF_WEEK);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), Set_Date_Time_Fragment.this, year, month, day);
            datePickerDialog.show();
        });

        back_arrow.setOnClickListener(v->{
           // Toast.makeText(getActivity(), "Back", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
//            recycleViewAddress.setVisibility(View.GONE);
//            txt_whats_location_continue.setVisibility(View.GONE);
//            set_location_map.setVisibility(View.VISIBLE);
//
//            linear_welcome.setVisibility(View.GONE);
//            linear_setAddress.setVisibility(View.VISIBLE);
//            //  }
        });


        return view;
    }

    @Override
    public void onDateSet(DatePicker view, int s_year, int s_month, int s_dayOfMonth) {
        my_Year = year;
        my_day = day;
        
        if (day1 == 0){
            my_day1 = "Sun";
        } else if (day1 == 1){
            my_day1 = "Mon";
        } else if (day1 == 2){
            my_day1 = "Tue";
        } else if (day1 == 3){
            my_day1 = "Wed";
        } else if (day1 == 4){
            my_day1 = "Thu";
        } else if (day1 == 5){
            my_day1 = "Fri";
        } else if (day1 == 6){
            my_day1 = "Sat";
        }

        if (month == 0){
            myMonth = "Jan";
        } else if (month == 1){
            myMonth = "Feb";
        } else if (month == 2){
            myMonth = "Mar";
        } else if (month == 3){
            myMonth = "Apr";
        } else if (month == 4){
            myMonth = "May";
        } else if (month == 5){
            myMonth = "Jun";
        } else if (month == 6){
            myMonth = "Jul";
        } else if (month == 7){
            myMonth = "Aug";
        } else if (month == 8){
            myMonth = "Sep";
        } else if (month == 9){
            myMonth = "Oct";
        } else if (month == 10){
            myMonth = "Nov";
        } else if (month == 11){
            myMonth = "Dec";
        }

            Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), Set_Date_Time_Fragment.this, hour, minute, DateFormat.is24HourFormat(getContext()));
        timePickerDialog.show();
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        my_Hour = hourOfDay;
        myMinute = minute;
        Time tme = new Time(my_Hour,myMinute,0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("hh:mm a");
        String  new_time =formatter.format(tme);

        txt_date.setText(my_day1 + ", " + my_day + " " + myMonth + " " + my_Year);
        txt_time.setText(new_time);
        Constant.JOBDATE = txt_date.getText().toString();
        Constant.JOBTIME = new_time;
    }

    private void GotoCreateJob(String user_id, String services, String problems, String subproblems, String additional_info, String order_number,String address,String province,String city,String suburb,
    String postal_code,String as_soon_as,String jobs_date,String jobs_time,String latitude,String longitude,String filecount,String file,String file_name) {
        Map<String, String> params = new HashMap<>();
        /*params.put(Constant.PUSER_ID, user_id);
        params.put(Constant.P_SERVICES, services);
        params.put(Constant.P_PROBLEMS, problems);
        params.put(Constant.P_SUBPROBLEMS, subproblems);
        params.put(Constant.P_ADDITIONALINFO, additional_info);
        params.put(Constant.P_ORDERNO, "");
        params.put(Constant.P_ADDRESS, address);
        params.put(Constant.P_PROVINCE, province);
        params.put(Constant.P_CITY, city);
        params.put(Constant.P_SUBURB, suburb);
        params.put(Constant.P_POSTALCODE, postal_code);
        params.put(Constant.P_ASSOONAS, as_soon_as);
        params.put(Constant.P_JOBDATE, jobs_date);
        params.put(Constant.P_JOBTIME, jobs_time);
        params.put(Constant.P_LATITUDE, latitude);
        params.put(Constant.P_LOGITUDE, longitude);
        //params.put(Constant.P_FILECOUNT, filecount);
        params.put(Constant.P_FILE, file);
        params.put(Constant.P_FILENAME, file_name);*/

        params.put("user_id",user_id);
        params.put("services",services);
        params.put("problems",problems);
        params.put("subproblems",subproblems);
        params.put("additional_info",additional_info);
        params.put("order_number","");
        params.put("address",address);
        params.put("province",province);
        params.put("city",city);
        params.put("suburb",suburb);
        params.put("postal_code",postal_code);
        params.put("as_soon_as",as_soon_as);
        params.put("jobs_date",jobs_date);
        params.put("jobs_time",jobs_time);
        params.put("latitude",latitude);
        params.put("longitude",longitude);
        params.put("file",file);
        params.put("file_name",file_name);

        System.out.println("Constant.PUSER_ID : "+ user_id);
        System.out.println("Constant.P_SERVICES : "+ services);
        System.out.println("Constant.P_PROBLEMS : "+ problems);
        System.out.println("Constant.P_SUBPROBLEMS : "+ subproblems);
        System.out.println("Constant.P_ADDITIONALINFO : "+ additional_info);
        System.out.println("Constant.P_ORDERNO : "+ order_number);
        System.out.println("Constant.P_ADDRESS : "+ address);
        System.out.println("Constant.P_PROVINCE : "+ province);
        System.out.println("Constant.P_CITY : "+ city);
        System.out.println("Constant.P_SUBURB : "+ suburb);
        System.out.println("Constant.P_POSTALCODE : "+ postal_code);
        System.out.println("Constant.P_ASSOONAS : "+ as_soon_as);
        System.out.println("Constant.P_JOBDATE : "+ jobs_date);
        System.out.println("Constant.P_JOBTIME : "+ jobs_time);
        System.out.println("Constant.P_LATITUDE : "+ latitude);
        System.out.println("Constant.P_LOGITUDE : "+ longitude);
        System.out.println("Constant.P_FILECOUNT : "+ filecount);
        System.out.println("Constant.P_FILE : "+ file);
        System.out.println("Constant.P_FILENAME : "+ file_name);

       // System.out.println("File Content"+Constant.FILE);

        ApiConfig.RequestToVolleyPost((result, response) -> {
            Log.d(TAG, "Result_CreateJOB Response" + response);
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d(TAG, "Result_CreateJOB" + jsonObject);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");

                    if (status.equals("1")) {
                        JSONObject result_obj = jsonObject.getJSONObject("result");

                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        getActivity().startActivity(intent);

                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //Toast.makeText(getActivity(), "Job Created", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                    getActivity().startActivity(intent);
                    System.out.println("Exception Result_CreateJOB"+e.getMessage());
                }
            }
        }, getActivity(), Constant.CREATEJOB, params, true);
    }

}