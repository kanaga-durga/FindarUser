package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.findar.user.Activity.MapActivity;
import org.findar.user.Helper.Constant;
import org.findar.user.Model.Address_GS;

import org.findar.user.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerAdapterAddress extends RecyclerView.Adapter<CustomRecyclerAdapterAddress.ViewHolder> {

    private final Context context;
    private final List<Address_GS> subproblems_Gs;
    private final ArrayList<String> subproblems_GsList;
    MapActivity mapActivity;
    @SuppressLint("StaticFieldLeak")
    public static CustomRecyclerAdapterAddress customRecyclerAdaptersubAddress;


    public CustomRecyclerAdapterAddress(Context context, List subproblems_Gs) {
        this.context = context;
        this.subproblems_Gs = subproblems_Gs;
        subproblems_GsList = new ArrayList<String>();
        this.mapActivity = MapActivity.mapActivity;
        customRecyclerAdaptersubAddress=this;
    }

    @Override
    public CustomRecyclerAdapterAddress.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.recycle_address, parentin, false);
        CustomRecyclerAdapterAddress.ViewHolder viewHolder = new CustomRecyclerAdapterAddress.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclerAdapterAddress.ViewHolder holder, final int position) {
        holder.text_address.setText(subproblems_Gs.get(position).getAd_TITLE());
        holder.text_location.setText(subproblems_Gs.get(position).getAD_ADDRESS());
        holder.imageIcon.setImageResource(subproblems_Gs.get(position).getIMAGEID());

        holder.linear_address.setOnClickListener(v -> {
//            Constant.SL_ID = subproblems_Gs.get(position).getAD_ID();
//            Constant.SL_NAME = subproblems_Gs.get(position).getAD_NAME();
            Constant.ADDRESS = subproblems_Gs.get(position).getAD_ADDRESS();
            Constant.PROVINCE = subproblems_Gs.get(position).getPROVICE();
            Constant.CITY = subproblems_Gs.get(position).getCITY();
            Constant.SUBURB = subproblems_Gs.get(position).getSUBURB();
            Constant.POSTALCODE = subproblems_Gs.get(position).getAD_POSTALCODE();


            mapActivity.gotoSetAddress(subproblems_Gs.get(position).getAD_ADDRESS());
        });

    }

    @Override
    public int getItemCount() {
        return subproblems_Gs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text_location;
        public TextView text_address;
        public LinearLayout linear_address;
        public ImageView imageIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            text_address = itemView.findViewById(R.id.text_address);
            text_location = itemView.findViewById(R.id.text_location);
            imageIcon = itemView.findViewById(R.id.imageIcon);
            linear_address = itemView.findViewById(R.id.linear_address);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}