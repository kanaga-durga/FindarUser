package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.findar.user.Activity.AddPhotosActivity;
import org.findar.user.Model.AddPhoto_GS;
import org.findar.user.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomRecyclerAdapterPhotos extends RecyclerView.Adapter<CustomRecyclerAdapterPhotos.ViewHolder> {

    private final Context context;
    private final List<AddPhoto_GS> addPhoto_gs;
    private final ArrayList<String> addPhoto_gsList;
    AddPhotosActivity addPhotosActivity;
    @SuppressLint("StaticFieldLeak")
    public static CustomRecyclerAdapterPhotos customRecyclerAdapterPhotos;


    public CustomRecyclerAdapterPhotos(Context context, List addPhoto_gs) {
        this.context = context;
        this.addPhoto_gs = addPhoto_gs;
        addPhoto_gsList = new ArrayList<String>();
        this.addPhotosActivity = AddPhotosActivity.addPhotosActivity;
        customRecyclerAdapterPhotos=this;
    }

    @Override
    public CustomRecyclerAdapterPhotos.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
        View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.recycle_add_photo, parentin, false);
        CustomRecyclerAdapterPhotos.ViewHolder viewHolder = new CustomRecyclerAdapterPhotos.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomRecyclerAdapterPhotos.ViewHolder holder, final int position) {

        holder.add_photo_id.setText(addPhoto_gs.get(position).getAP_ID());
        holder.add_photo_name.setText(addPhoto_gs.get(position).getAP_NAME());
        String ImageURL = addPhoto_gs.get(position).getAP_BASE_64();
//        Log.d("TATA",ImageURL);
        byte[] imageAsBytes = Base64.decode(ImageURL.getBytes(), Base64.DEFAULT);
//        Picasso.get().load(ImageURL).into(holder.add_photo_img);

        holder.add_photo_img.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        holder.img_et_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int actualPosition = holder.getAdapterPosition();
                addPhoto_gs.remove(actualPosition);
                notifyItemRemoved(actualPosition);
                notifyItemRangeChanged(actualPosition, addPhoto_gs.size());
                addPhotosActivity.GoToImageDelete();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addPhoto_gs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView add_photo_id;
        public TextView add_photo_name;
        public ImageView add_photo_img;
        public TextView img_et_camera;

        public ViewHolder(View itemView) {
            super(itemView);
            add_photo_id = itemView.findViewById(R.id.add_photo_id);
            add_photo_name = itemView.findViewById(R.id.add_photo_name);
            add_photo_img = itemView.findViewById(R.id.add_photo_img);
            img_et_camera = itemView.findViewById(R.id.img_et_camera);
        }
        @Override
        public void onClick(View v) {
        }
    }

}