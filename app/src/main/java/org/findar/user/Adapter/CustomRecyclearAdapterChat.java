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

import org.findar.user.Activity.MainActivity;
import org.findar.user.Helper.Constant;
import org.findar.user.Model.Chat_GS;

import org.findar.user.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomRecyclearAdapterChat extends RecyclerView.Adapter<CustomRecyclearAdapterChat.ViewHolder> {

    private final Context context;
    public  List<Chat_GS> chat_gsList;
    MainActivity mainActivity;
    @SuppressLint("StaticFieldLeak")
    public static CustomAdapterCompanies customAdapterCompanies;
    String jobId;

    public CustomRecyclearAdapterChat(Context context, List chat_gsList) {
        this.context = context;
        this.chat_gsList = chat_gsList;
    }

    @Override
    public CustomRecyclearAdapterChat.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.item_chat, parentin, false);
        CustomRecyclearAdapterChat.ViewHolder viewHolder = new CustomRecyclearAdapterChat.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclearAdapterChat.ViewHolder holder, final int position) {
        System.out.println("Message ::"+chat_gsList.get(position).getMessage());
        if(!chat_gsList.get(position).getFrom_id().equalsIgnoreCase(Constant.USER_ID)) {
            holder.RelativeParentLeft.setVisibility(View.VISIBLE);
            holder.RelativeParentRight.setVisibility(View.GONE);
            holder.textName.setText(chat_gsList.get(position).getName());
            holder.textContent.setText(chat_gsList.get(position).getMessage());
            holder.textDate.setText(chat_gsList.get(position).getCreated_at());
        }
        else
        {
            holder.RelativeParentLeft.setVisibility(View.GONE);
            holder.RelativeParentRight.setVisibility(View.VISIBLE);
            holder.textContent1.setText(chat_gsList.get(position).getMessage());
            holder.textDate1.setText(chat_gsList.get(position).getCreated_at());
        }
    }

    @Override
    public int getItemCount() {
        return chat_gsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout RelativeParentLeft,RelativeParentRight;
        public TextView textName;
        public TextView textContent,textContent1;
        public ImageView imageView;
        public TextView textDate,textDate1;
        public ViewHolder(View itemView) {
            super(itemView);
            RelativeParentLeft  = itemView.findViewById(R.id.RelativeParentLeft);
            RelativeParentRight = itemView.findViewById(R.id.RelativeParentRight);
            textName = itemView.findViewById(R.id.textName);
            textContent = itemView.findViewById(R.id.textContent);
            textContent1 = itemView.findViewById(R.id.textContent1);
            textDate = itemView.findViewById(R.id.textDate);
            textDate1 = itemView.findViewById(R.id.textDate1);
        }
        @Override
        public void onClick(View v) {
//            Toast.makeText(itemView.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        }


    }
    public void setItems(List<Chat_GS> chat_gsList) {
        this.chat_gsList = chat_gsList;
    }

}