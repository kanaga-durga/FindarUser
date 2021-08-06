package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.findar.user.Activity.JobDetailsActivity;
import org.findar.user.Activity.JobInformation;
import org.findar.user.Activity.MainActivity;
import org.findar.user.Helper.Constant;
import org.findar.user.Model.Companies;

import org.findar.user.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterCompanies extends RecyclerView.Adapter<CustomAdapterCompanies.ViewHolder> {

    private final Context context;
    private final List<Companies> companies_Gs;
    private final ArrayList<String> subproblems_GsList;
    MainActivity mainActivity;
    @SuppressLint("StaticFieldLeak")
    public static CustomAdapterCompanies customAdapterCompanies;
    String jobId;

    public CustomAdapterCompanies(Context context, List companies_Gs,String jobId) {
        this.context = context;
        this.companies_Gs = companies_Gs;
        subproblems_GsList = new ArrayList<String>();
        this.mainActivity = MainActivity.mainActivity;
        customAdapterCompanies=this;
        this.jobId = jobId;
    }

    @Override
    public CustomAdapterCompanies.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.item_jobs, parentin, false);
        CustomAdapterCompanies.ViewHolder viewHolder = new CustomAdapterCompanies.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomAdapterCompanies.ViewHolder holder, final int position) {
        if(companies_Gs.size()==1){
            if(companies_Gs.get(position).getCompanyId().equalsIgnoreCase("")) {
                holder.relativeDummy.setVisibility(View.VISIBLE);
                holder.relativeContent.setVisibility(View.GONE);
              //  holder.textHeadJob.setText(R.string.potentialString);
            }
            else
            {
               // holder.textHeadJob.setText(R.string.yourjobString);
                holder.relativeContent.setVisibility(View.VISIBLE);
                holder.relativeDummy.setVisibility(View.GONE);
                holder.textCompany.setText(companies_Gs.get(position).getCompanyName());
                holder.textDate.setText(companies_Gs.get(position).getJobdatetime());
                holder.textCallout.setText(companies_Gs.get(position).getCalloutprice());
                holder.textPerHour.setText(companies_Gs.get(position).getPriceperhour());
                holder.textTime.setText(companies_Gs.get(position).getAvailabletime());
                if(!companies_Gs.get(position).getCompanyprofileimage().equalsIgnoreCase("") && companies_Gs.get(position).getCompanyprofileimage()!=null)
                    Picasso.get().load(companies_Gs.get(position).getCompanyprofileimage()).into(holder.imageView);

                if(companies_Gs.get(position).getStarrating()!=""&&companies_Gs.get(position).getStarrating()!=null)
                    holder.ratingBar.setRating(Float.parseFloat(companies_Gs.get(position).getStarrating()));
                // holder.ratingBar.setNumStars(Integer.parseInt(companies_Gs.get(position).getStarrating()));

                holder.relativeContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Constant.COMPANYID = companies_Gs.get(position).getCompanyId();
                        Constant.JOBID = jobId;
                        if(companies_Gs.get(position).getTitle().equalsIgnoreCase("0")) {
                            Intent intent = new Intent(mainActivity, JobDetailsActivity.class);
                            mainActivity.startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(mainActivity, JobInformation.class);
                            mainActivity.startActivity(intent);
                        }
                    }
                });


            }
        }
        else {
            holder.relativeContent.setVisibility(View.VISIBLE);
            holder.relativeDummy.setVisibility(View.GONE);
            holder.textCompany.setText(companies_Gs.get(position).getCompanyName());
            holder.textDate.setText(companies_Gs.get(position).getJobdatetime());
            holder.textCallout.setText(companies_Gs.get(position).getCalloutprice());
            holder.textPerHour.setText(companies_Gs.get(position).getPriceperhour());
            holder.textTime.setText(companies_Gs.get(position).getAvailabletime());
            if(!companies_Gs.get(position).getCompanyprofileimage().equalsIgnoreCase("") && companies_Gs.get(position).getCompanyprofileimage()!=null)
                Picasso.get().load(companies_Gs.get(position).getCompanyprofileimage()).into(holder.imageView);

            if(companies_Gs.get(position).getStarrating()!=""&&companies_Gs.get(position).getStarrating()!=null)
               holder.ratingBar.setRating(Float.parseFloat(companies_Gs.get(position).getStarrating()));
              // holder.ratingBar.setNumStars(Integer.parseInt(companies_Gs.get(position).getStarrating()));

            holder.relativeContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constant.COMPANYID = companies_Gs.get(position).getCompanyId();
                    Constant.JOBID = jobId;
                    if(companies_Gs.get(position).getTitle().equalsIgnoreCase("0")) {
                        Intent intent = new Intent(mainActivity, JobDetailsActivity.class);
                        mainActivity.startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(mainActivity, JobInformation.class);
                        mainActivity.startActivity(intent);
                    }
                }
            });
        }

        if(companies_Gs.get(position).getTitle().equalsIgnoreCase("0")){
            holder.textCancel.setVisibility(View.GONE);
            holder.textStatus.setVisibility(View.GONE);
        }
        else{
            holder.textCancel.setVisibility(View.VISIBLE);
            holder.textStatus.setVisibility(View.VISIBLE);
        }
//        holder.text_location.setText(subproblems_Gs.get(position).getAD_ADDRESS());
//        holder.imageIcon.setImageResource(subproblems_Gs.get(position).getIMAGEID());

//        holder.linear_address.setOnClickListener(v -> {
////            Constant.SL_ID = subproblems_Gs.get(position).getAD_ID();
////            Constant.SL_NAME = subproblems_Gs.get(position).getAD_NAME();
//            Constant.ADDRESS = subproblems_Gs.get(position).getAD_ADDRESS();
//            Constant.PROVINCE = subproblems_Gs.get(position).getPROVICE();
//            Constant.CITY = subproblems_Gs.get(position).getCITY();
//            Constant.SUBURB = subproblems_Gs.get(position).getSUBURB();
//            Constant.POSTALCODE = subproblems_Gs.get(position).getAD_POSTALCODE();
//
//
//            mapActivity.gotoSetAddress(subproblems_Gs.get(position).getAD_ADDRESS());
//        });

    }

    @Override
    public int getItemCount() {
        return companies_Gs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textCompany;
        public RecyclerView recycleViewPotential;
        public RelativeLayout relativeDummy,relativeContent;
        public TextView textDate,textCallout,textPerHour,textTime;
        public RatingBar ratingBar;
//        public LinearLayout linear_address;
        public ImageView imageView;
        public TextView textHeadJob,textCancel,textStatus;


        public ViewHolder(View itemView) {
            super(itemView);
            textCompany = itemView.findViewById(R.id.textCompany);
            relativeContent = itemView.findViewById(R.id.relativeContent);
            relativeDummy = itemView.findViewById(R.id.relativeDummy);

           // recycleViewPotential = itemView.findViewById(R.id.recycleViewPotential);
            textDate = itemView.findViewById(R.id.textDate);
            textCallout = itemView.findViewById(R.id.textCallout);
            textPerHour = itemView.findViewById(R.id.textPerHour);
            textTime = itemView.findViewById(R.id.textTime);

            ratingBar = itemView.findViewById(R.id.ratingBar);
            imageView = itemView.findViewById(R.id.imageView);
            textCancel = itemView.findViewById(R.id.textCancel);
            textStatus = itemView.findViewById(R.id.textStatus);
//            linear_address = itemView.findViewById(R.id.linear_address);

           // textHeadJob = itemView.findViewById(R.id.textPotential);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}