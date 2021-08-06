package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.findar.user.Activity.JobInformation;
import org.findar.user.Helper.Constant;
import org.findar.user.Model.Reasons;
import org.findar.user.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclearAdapterReasons extends RecyclerView.Adapter<CustomRecyclearAdapterReasons.ViewHolder>{
        private final Context context;
        private final List<Reasons> reasonsList;

        JobInformation jobInformation;
        @SuppressLint("StaticFieldLeak")
        public static CustomRecyclearAdapterReasons customRecyclerAdaptersubAddress;

        public CustomRecyclearAdapterReasons(Context context, List reasonsList) {
            this.context = context;
            this.reasonsList = reasonsList;
            customRecyclerAdaptersubAddress=this;
            this.jobInformation = JobInformation.jobInformation;
        }

        @Override
        public CustomRecyclearAdapterReasons.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
            View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.item_reasons, parentin, false);
            CustomRecyclearAdapterReasons.ViewHolder viewHolder = new CustomRecyclearAdapterReasons.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomRecyclearAdapterReasons.ViewHolder holder, final int position) {
            holder.text_description.setText(reasonsList.get(position).getDescription());
            holder.linearLayout.setOnClickListener(v -> {
                Constant.REASONID = reasonsList.get(position).getId();
                jobInformation.GoToCancelJob();
            });

        }

        @Override
        public int getItemCount() {
            return reasonsList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public TextView text_description;
            public LinearLayout linearLayout;
            public ViewHolder(View itemView) {
                super(itemView);
                text_description = itemView.findViewById(R.id.text_description);
                linearLayout = itemView.findViewById(R.id.linearLayout);
            }
            @Override
            public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
            }
        }

    }


