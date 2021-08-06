package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import org.findar.user.Helper.UICommon;
import org.findar.user.R;

public class TrackOrderActivity extends AppCompatActivity {
    UICommon uicommon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,TrackOrderActivity.this);
    }
}