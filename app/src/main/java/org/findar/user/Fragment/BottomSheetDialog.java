package org.findar.user.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import org.findar.user.Activity.AddPhotosActivity;

import org.findar.user.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetDialog extends BottomSheetDialogFragment {
    AddPhotosActivity addPhotosActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,
                container, false);
        this.addPhotosActivity = AddPhotosActivity.addPhotosActivity;

        Button camera_button = v.findViewById(R.id.camera_button);
        Button gallery_button = v.findViewById(R.id.gallery_button);

        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(getActivity(), "Algorithm Shared", Toast.LENGTH_SHORT).show();

                addPhotosActivity.goToCamera();

                dismiss();
            }
        });

        gallery_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              //  Toast.makeText(getActivity(), "Course Shared", Toast.LENGTH_SHORT).show();
                addPhotosActivity.goToGallery();
                dismiss();
            }
        });
        return v;
    }
}

