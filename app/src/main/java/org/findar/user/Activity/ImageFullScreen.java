package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.findar.user.Helper.UICommon;
import org.findar.user.R;

public class ImageFullScreen extends AppCompatActivity {

    UICommon uicommon;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,ImageFullScreen.this);

        imageView = findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            String imageUrl = extras.getString("IMAGEURL");
            Picasso.get().load(imageUrl).into(imageView);
        }
    }
}