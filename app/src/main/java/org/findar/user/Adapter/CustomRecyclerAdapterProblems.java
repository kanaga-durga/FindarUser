package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.findar.user.Activity.ProblemsActivity;
import org.findar.user.Helper.Constant;
import org.findar.user.Model.Problems_GS;
import org.findar.user.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerAdapterProblems extends RecyclerView.Adapter<CustomRecyclerAdapterProblems.ViewHolder> {

    private final Context context;
    private final List<Problems_GS> problems_Gs;
    private final ArrayList<String> problems_GsList;
    ProblemsActivity problemsActivity;
    @SuppressLint("StaticFieldLeak")
    public static CustomRecyclerAdapterProblems customRecyclerAdapterProblems;


    public CustomRecyclerAdapterProblems(Context context, List problems_Gs) {
        this.context = context;
        this.problems_Gs = problems_Gs;
        problems_GsList = new ArrayList<String>();
       this.problemsActivity = ProblemsActivity.problemsActivity;
        customRecyclerAdapterProblems=this;
    }

    @Override
    public CustomRecyclerAdapterProblems.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.recycle_service, parentin, false);
        CustomRecyclerAdapterProblems.ViewHolder viewHolder = new CustomRecyclerAdapterProblems.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclerAdapterProblems.ViewHolder holder, final int position) {




        holder.service_id.setText(problems_Gs.get(position).getSL_ID());
        holder.service_name.setText(problems_Gs.get(position).getSL_NAME());
        String ImageURL = problems_Gs.get(position).getSL_IMG();
        Picasso.get().load(ImageURL).into(holder.service_img);

        holder.service_over.setOnClickListener(v -> {
            Constant.PL_ID = problems_Gs.get(position).getSL_ID();
            Constant.PL_NAME = problems_Gs.get(position).getSL_NAME();
            Constant.PL_IMG = problems_Gs.get(position).getSL_IMG();
            problemsActivity.gotoSubProblem();
        });

    }

    @Override
    public int getItemCount() {
        return problems_Gs.size();
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