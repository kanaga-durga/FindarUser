package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.findar.user.Activity.SubProblemsActivity;
import org.findar.user.Helper.Constant;
import org.findar.user.Model.SubProblems_GS;
import org.findar.user.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerAdapterSubProblems extends RecyclerView.Adapter<CustomRecyclerAdapterSubProblems.ViewHolder> {

    private final Context context;
    private final List<SubProblems_GS> subproblems_Gs;
    private final ArrayList<String> subproblems_GsList;
    SubProblemsActivity subproblemsActivity;
    @SuppressLint("StaticFieldLeak")
    public static CustomRecyclerAdapterSubProblems customRecyclerAdaptersubProblems;


    public CustomRecyclerAdapterSubProblems(Context context, List subproblems_Gs) {
        this.context = context;
        this.subproblems_Gs = subproblems_Gs;
        subproblems_GsList = new ArrayList<String>();
       this.subproblemsActivity = SubProblemsActivity.subProblemsActivity;
        customRecyclerAdaptersubProblems=this;
    }

    @Override
    public CustomRecyclerAdapterSubProblems.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.recycle_subproblems, parentin, false);
        CustomRecyclerAdapterSubProblems.ViewHolder viewHolder = new CustomRecyclerAdapterSubProblems.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclerAdapterSubProblems.ViewHolder holder, final int position) {




        holder.problems_id.setText(subproblems_Gs.get(position).getSL_ID());
        holder.txt_Sub_problems_name.setText(subproblems_Gs.get(position).getSL_NAME());

        holder.LL_Sub_Problems.setOnClickListener(v -> {
                Constant.SPL_ID = subproblems_Gs.get(position).getSL_ID();
                Constant.SPL_NAME = subproblems_Gs.get(position).getSL_NAME();
                subproblemsActivity.gotoSubProblem();
        });

    }

    @Override
    public int getItemCount() {
        return subproblems_Gs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView problems_id;
        public TextView txt_Sub_problems_name;
        public LinearLayout LL_Sub_Problems;



        public ViewHolder(View itemView) {
            super(itemView);
            txt_Sub_problems_name = itemView.findViewById(R.id.txt_Sub_problems_name);
            problems_id = itemView.findViewById(R.id.problems_id);
            LL_Sub_Problems = itemView.findViewById(R.id.LL_Sub_Problems);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}