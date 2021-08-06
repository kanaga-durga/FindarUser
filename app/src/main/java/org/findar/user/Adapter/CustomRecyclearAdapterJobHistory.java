package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.findar.user.Activity.JobInformation;
import org.findar.user.Activity.MainActivity;
import org.findar.user.Helper.Constant;
import org.findar.user.Model.JobHistory_GS;

import org.findar.user.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomRecyclearAdapterJobHistory extends RecyclerView.Adapter<CustomRecyclearAdapterJobHistory.ViewHolder> {

    private final Context context;
    private final List<JobHistory_GS> jobHistory_gs;
    MainActivity mainActivity;
    @SuppressLint("StaticFieldLeak")
    public static CustomAdapterCompanies customAdapterCompanies;
    String jobId;

    public CustomRecyclearAdapterJobHistory(Context context, List jobHistory_gs) {
        this.context = context;
        this.jobHistory_gs = jobHistory_gs;
    }

    @Override
    public CustomRecyclearAdapterJobHistory.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.item_job_history, parentin, false);
        CustomRecyclearAdapterJobHistory.ViewHolder viewHolder = new CustomRecyclearAdapterJobHistory.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclearAdapterJobHistory.ViewHolder holder, final int position) {
        if(!jobHistory_gs.get(position).getPlumber_name().equalsIgnoreCase("null"))
        holder.textCompanyname.setText(jobHistory_gs.get(position).getPlumber_name());

        holder.textStatus.setText(jobHistory_gs.get(position).getPlumbing_state());

                if(jobHistory_gs.get(position).getPlumbing_state().equalsIgnoreCase("Upcoming")) {
                    holder.textStatus.setBackground(context.getDrawable(R.drawable.history_upcoming_round));
                    holder.textStatus.setTextColor(context.getColor(R.color.text_blue));
                }
                else if(jobHistory_gs.get(position).getPlumbing_state().equalsIgnoreCase("On Route")) {
                    holder.textStatus.setBackground(context.getDrawable(R.drawable.history_route_round));
                    holder.textStatus.setTextColor(context.getColor(R.color.textViolet));
                }

                else if(jobHistory_gs.get(position).getPlumbing_state().equalsIgnoreCase("Cancelled")) {
                    holder.textStatus.setBackground(context.getDrawable(R.drawable.history_cancel_round));
                    holder.textStatus.setTextColor(context.getColor(R.color.logout_color1));
                }

                else if(jobHistory_gs.get(position).getPlumbing_state().equalsIgnoreCase("Completed")) {
                    holder.textStatus.setBackground(context.getDrawable(R.drawable.history_complete_round));
                    holder.textStatus.setTextColor(context.getColor(R.color.textGreen));
                }

                else if(jobHistory_gs.get(position).getPlumbing_state().equalsIgnoreCase("Busy Working")) {
                    holder.textStatus.setBackground(context.getDrawable(R.drawable.history_busy_round));
                    holder.textStatus.setTextColor(context.getColor(R.color.textOrange));
                }

                holder.textRating.setText(jobHistory_gs.get(position).getCompany_name());
                if(!jobHistory_gs.get(position).getPlumber_profile_picture().equalsIgnoreCase("") && jobHistory_gs.get(position).getPlumber_profile_picture()!=null)
                    Picasso.get().load(jobHistory_gs.get(position).getPlumber_profile_picture()).into(holder.imageView);

                holder.relativeLayout.setOnClickListener(v->{
                    Constant.JOBID = jobHistory_gs.get(position).getJob_id();
                    Constant.COMPANYID =  jobHistory_gs.get(position).getCompanyId();
                    Constant.JOBSTATUS = jobHistory_gs.get(position).getPlumbing_state();

                    Intent intent = new Intent(context, JobInformation.class);
                    context.startActivity(intent);

                });

        }
    @Override
    public int getItemCount() {
        return jobHistory_gs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textCompanyname;
        public TextView textRating;
        public ImageView imageView;
        public TextView textStatus;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            textCompanyname = itemView.findViewById(R.id.textCompanyname);
            textStatus = itemView.findViewById(R.id.textStatus);
            textRating = itemView.findViewById(R.id.textRating);
            imageView = itemView.findViewById(R.id.imageView);
            relativeLayout = itemView.findViewById(R.id.recyclearView);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}