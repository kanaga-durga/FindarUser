package org.findar.user.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import org.findar.user.Activity.Service_Types_Activity;
import org.findar.user.Helper.Constant;
import org.findar.user.Model.Service_GS;
import org.findar.user.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerAdapterService extends RecyclerView.Adapter<CustomRecyclerAdapterService.ViewHolder> {

    private final Context context;
    private final List<Service_GS> serviceGs;
    private final ArrayList<String> serviceGsList;
    Service_Types_Activity serviceTypesActivity;
    public static CustomRecyclerAdapterService customRecyclerAdapterService;


    public CustomRecyclerAdapterService(Context context, List serviceGs) {
        this.context = context;
        this.serviceGs = serviceGs;
        serviceGsList = new ArrayList<String>();
       this.serviceTypesActivity = Service_Types_Activity.serviceTypesActivity;
        customRecyclerAdapterService=this;
    }

    @Override
    public CustomRecyclerAdapterService.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.recycle_service, parentin, false);
        CustomRecyclerAdapterService.ViewHolder viewHolder = new CustomRecyclerAdapterService.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclerAdapterService.ViewHolder holder, final int position) {

        holder.service_id.setText(serviceGs.get(position).getSL_ID());
        holder.service_name.setText(serviceGs.get(position).getSL_NAME());
        String ImageURL = serviceGs.get(position).getSL_IMG();
        Picasso.get().load(ImageURL).into(holder.service_img);

        holder.service_img.setOnClickListener(v -> {
            Log.d("qwerty", "Result_Login" +serviceGs.get(position).getSL_ID());
            Constant.SL_ID = serviceGs.get(position).getSL_ID();
            serviceTypesActivity.gotoProblem();
        });

    }

    @Override
    public int getItemCount() {
        return serviceGs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView service_id;
        public TextView service_name;
        public ImageView service_img;
        public RelativeLayout service_over;



        public ViewHolder(View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.service_name);
            service_id = itemView.findViewById(R.id.service_id);
            service_img = itemView.findViewById(R.id.service_img);
            service_over = itemView.findViewById(R.id.service_over);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}