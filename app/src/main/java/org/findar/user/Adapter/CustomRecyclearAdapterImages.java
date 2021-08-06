package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import org.findar.user.Activity.AddPhotosActivity;
import org.findar.user.Activity.ImageFullScreen;
import org.findar.user.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
public class CustomRecyclearAdapterImages extends RecyclerView.Adapter<CustomRecyclearAdapterImages.ViewHolder> {

        private final Context context;
        private final List<String> addPhoto_gs;
        private final ArrayList<String> addPhoto_gsList;
        AddPhotosActivity addPhotosActivity;
        @SuppressLint("StaticFieldLeak")
        public static CustomRecyclearAdapterImages customRecyclerAdapterPhotos;

        public CustomRecyclearAdapterImages(Context context, List addPhoto_gs) {
            this.context = context;
            this.addPhoto_gs = addPhoto_gs;
            addPhoto_gsList = new ArrayList<String>();
            this.addPhotosActivity = AddPhotosActivity.addPhotosActivity;
            customRecyclerAdapterPhotos=this;
        }

        @Override
        public CustomRecyclearAdapterImages.ViewHolder onCreateViewHolder(ViewGroup parentin, int viewType) {
            View v = LayoutInflater.from(parentin.getContext()).inflate(R.layout.item_images, parentin, false);
            CustomRecyclearAdapterImages.ViewHolder viewHolder = new CustomRecyclearAdapterImages.ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final CustomRecyclearAdapterImages.ViewHolder holder, final int position) {
            String ImageURL = addPhoto_gs.get(position);
            Picasso.get().load(ImageURL).into(holder.add_photo_img);

            holder.add_photo_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageFullScreen.class);
                    intent.putExtra("IMAGEURL",ImageURL);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return addPhoto_gs.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public ImageView add_photo_img;
            public ViewHolder(View itemView) {
                super(itemView);
                add_photo_img = itemView.findViewById(R.id.add_photo_img);
            }
            @Override
            public void onClick(View v) {
            }
        }
    }

