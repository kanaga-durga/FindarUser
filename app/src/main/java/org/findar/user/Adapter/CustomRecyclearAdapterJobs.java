package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.findar.user.Activity.MainActivity;
import org.findar.user.Helper.Constant;
import org.findar.user.Model.Potential_GS;

import org.findar.user.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclearAdapterJobs extends RecyclerView.Adapter<CustomRecyclearAdapterJobs.ViewHolder> {

    private final Context context;
    private final List<Potential_GS> subproblems_Gs;
    private final ArrayList<String> subproblems_GsList;
    MainActivity mainActivity;
    CustomAdapterCompanies mAdapterCompanies;
    @SuppressLint("StaticFieldLeak")
    public static CustomRecyclearAdapterJobs customRecyclerAdaptersubAddress;

    public CustomRecyclearAdapterJobs(Context context, List subproblems_Gs) {
        this.context = context;
        this.subproblems_Gs = subproblems_Gs;
        subproblems_GsList = new ArrayList<String>();
        this.mainActivity = MainActivity.mainActivity;
        customRecyclerAdaptersubAddress=this;
    }

    @Override
    public CustomRecyclearAdapterJobs.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.item_recycle, parentin, false);
        CustomRecyclearAdapterJobs.ViewHolder viewHolder = new CustomRecyclearAdapterJobs.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclearAdapterJobs.ViewHolder holder, final int position) {
        if(position == 0){
            holder.textPotential.setText(R.string.potentialString);
            holder.textPotential.setVisibility(View.VISIBLE);
        }
        else if(position==Constant.POTENTIAL_END){
            holder.textPotential.setText(R.string.yourjobString);
            holder.textPotential.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.textPotential.setVisibility(View.GONE);
        }
        holder.textCompany.setText(subproblems_Gs.get(position).getProblems()+" - "+subproblems_Gs.get(position).getSubproblems());
        holder.recycleViewPotential.setHasFixedSize(true);
        LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
       // LinearLayoutManager layoutManagerSubProblems = new LinearLayoutManager(mainActivity);
        holder.recycleViewPotential.setLayoutManager(layoutManagerSubProblems);
        System.out.println("GET COMPANY LIST"+position+ "::"+ subproblems_Gs.get(position).getCompaniesList());



        mAdapterCompanies = new CustomAdapterCompanies(mainActivity, subproblems_Gs.get(position).getCompaniesList(),subproblems_Gs.get(position).getJob_id());
        holder.recycleViewPotential.setAdapter(mAdapterCompanies);

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
        return subproblems_Gs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textCompany;
        public RecyclerView recycleViewPotential;
        public TextView textPotential;
        public  int flag=0;
//        public TextView text_address;
//        public LinearLayout linear_address;
//        public ImageView imageIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            textCompany = itemView.findViewById(R.id.textJob);
            textPotential = itemView.findViewById(R.id.textPotential);
            recycleViewPotential = itemView.findViewById(R.id.recycleViewPotential);
//            text_location = itemView.findViewById(R.id.text_location);
//            imageIcon = itemView.findViewById(R.id.imageIcon);
//            linear_address = itemView.findViewById(R.id.linear_address);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}