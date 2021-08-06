
package org.findar.user.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.findar.user.Adapter.CustomRecyclerAdapterPhotos;
import org.findar.user.Fragment.BottomSheetDialog;
import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.Model.AddPhoto_GS;

import org.findar.user.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AddPhotosActivity extends AppCompatActivity {

    LinearLayout LL_Take_Photo;
    String image;

    RecyclerView recycleViewPhotos;
    RecyclerView.Adapter mAdapterPhotos;
    List<AddPhoto_GS> addPhotoGs;
    RecyclerView.LayoutManager layoutManagerPhotos;
    Activity activity;
    public static AddPhotosActivity addPhotosActivity;
    int imageCount=0;
    TextView textPhotoLeft;
    UICommon uicommon;
    TextView txt_describe_continue,describe_skip;
    private static final int CAMERA_REQUEST = 1;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String fileContent = "",fileName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photos);

        addPhotosActivity = AddPhotosActivity.this;
        activity = AddPhotosActivity.this;

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,AddPhotosActivity.this);

        LL_Take_Photo = findViewById(R.id.LL_Take_Photo);
        textPhotoLeft = findViewById(R.id.textPhotoLeft);

        recycleViewPhotos = (RecyclerView) findViewById(R.id.recycleViewPhotos);
        recycleViewPhotos.setHasFixedSize(true);
        layoutManagerPhotos = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycleViewPhotos.setLayoutManager(layoutManagerPhotos);
        addPhotoGs = new ArrayList<>();

        txt_describe_continue = findViewById(R.id.txt_describe_continue);
        describe_skip = findViewById(R.id.describe_skip);

        System.out.println("SUBPROBLEM : "+Constant.SPL_NAME);
        if (Constant.SPL_NAME.equals("other") || Constant.SPL_NAME.equals("Other")){
            describe_skip.setVisibility(View.GONE);
        }

        LL_Take_Photo.setOnClickListener(v -> {
            if(imageCount<5) {
//                Intent intent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, 0);

//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);

                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");
            }
            else {
                Toast.makeText(getApplicationContext(),R.string.messagePhoto,Toast.LENGTH_LONG).show();
            }
        });

        txt_describe_continue.setOnClickListener(v->{


            if(addPhotoGs!=null) {
                for (int i = 0; i < addPhotoGs.size(); i++) {
                    if(i!=(addPhotoGs.size()-1))
                    fileContent+=addPhotoGs.get(i).getAP_BASE_64()+",";

                    fileName+=addPhotoGs.get(i).getAP_NAME()+",";

                    System.out.println("filecontent "+fileContent);

                }
            }

            //Toast.makeText(getApplicationContext(),fileContent,Toast.LENGTH_LONG).show();

            //Constant.FILECOUNT =String.valueOf(imageCount);
            Constant.FILE =fileContent;
            Constant.FILENAME = fileName;

            Intent mainIntent = new Intent(AddPhotosActivity.this,MapActivity.class);
            mainIntent.putExtra("NAVIGATION","ADDPHOTO");
            startActivity(mainIntent);
        });

    }

    public void goToCamera(){

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }


    }

    public void goToGallery(){
                        Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Uri targetUri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                    image = ConvertBitmapToString(resizedBitmap);
                    AddPhoto_GS addPhoto_gs = new AddPhoto_GS();
                    addPhoto_gs.setAP_ID("1");
                    addPhoto_gs.setAP_BASE_64(image);
                    addPhoto_gs.setAP_NAME(System.currentTimeMillis()+"_photo.jpg");
                    addPhoto_gs.setAP_IMAGEID(imageCount);

                    addPhotoGs.add(addPhoto_gs);
                    imageCount++;
                    textPhotoLeft.setText((5-imageCount)+" Photos Left");

                    mAdapterPhotos = new CustomRecyclerAdapterPhotos(AddPhotosActivity.this, addPhotoGs);
                    recycleViewPhotos.setAdapter(mAdapterPhotos);
//                    Constant.USER_P_FILE = image;
//                    Constant.USER_P_FILE_NAME = "asasas.jpg";
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap bitmap;
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                image = ConvertBitmapToString(resizedBitmap);
                AddPhoto_GS addPhoto_gs = new AddPhoto_GS();
                addPhoto_gs.setAP_ID("1");
                addPhoto_gs.setAP_BASE_64(image);
                addPhoto_gs.setAP_NAME(System.currentTimeMillis()+"_photo.jpg");
                addPhoto_gs.setAP_IMAGEID(imageCount);

                addPhotoGs.add(addPhoto_gs);
                imageCount++;
                textPhotoLeft.setText((5-imageCount)+" Photos Left");

                mAdapterPhotos = new CustomRecyclerAdapterPhotos(AddPhotosActivity.this, addPhotoGs);
                recycleViewPhotos.setAdapter(mAdapterPhotos);
//                    Constant.USER_P_FILE = image;
//                    Constant.USER_P_FILE_NAME = "asasas.jpg";
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void GoToImageDelete(){
        imageCount--;
        textPhotoLeft.setText((5-imageCount)+" Photos Left");
    }

    //method to convert the selected image to base64 encoded string

    public static String ConvertBitmapToString(Bitmap bitmap){

        Bitmap immagex=bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

//        byte[] imageAsBytes = Base64.decode(imageEncoded.getBytes(), Base64.DEFAULT);

//        img_et_profile.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }


}



