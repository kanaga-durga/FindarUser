package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import org.findar.user.Activity.JobDetailsActivity;
import org.findar.user.Model.Customer_GS;
import org.findar.user.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class CustomRecyclearAdapterCustomer extends RecyclerView.Adapter<CustomRecyclearAdapterCustomer.ViewHolder> {
    private final Context context;
    private final List<Customer_GS> customer_Gs;
    JobDetailsActivity jobDetailsActivity;
    CustomAdapterCompanies mAdapterCompanies;
    @SuppressLint("StaticFieldLeak")
    public static CustomRecyclearAdapterCustomer customRecyclearAdapterCustomer;

    public CustomRecyclearAdapterCustomer(Context context, List customer_Gs) {
        this.context = context;
        this.customer_Gs = customer_Gs;

        this.jobDetailsActivity = JobDetailsActivity.jobDetailsActivity;
        customRecyclearAdapterCustomer=this;
    }

    @Override
    public CustomRecyclearAdapterCustomer.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.item_customer, parentin, false);
        CustomRecyclearAdapterCustomer.ViewHolder viewHolder = new CustomRecyclearAdapterCustomer.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclearAdapterCustomer.ViewHolder holder, final int position) {
        holder.textFeedback.setText(customer_Gs.get(position).getCustomer_feedback_description());
        holder.textName.setText(" - " + customer_Gs.get(position).getCustomer_name());
        holder.textRating.setText(" * "+customer_Gs.get(position).getCustomer_rating());

        if(!customer_Gs.get(position).getCustomer_image().equalsIgnoreCase(""))
        Picasso.get()
                .load(customer_Gs.get(position).getCustomer_image())
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return customer_Gs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textFeedback,textName,textRating;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textFeedback = itemView.findViewById(R.id.textFeedback);
            textName = itemView.findViewById(R.id.textName);
            textRating = itemView.findViewById(R.id.textRating);
            imageView = itemView.findViewById(R.id.imageView);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }

}