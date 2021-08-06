package org.findar.user.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import org.findar.user.Activity.AddressMapActivity;
import org.findar.user.Activity.MapActivity;
import org.findar.user.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ConfirmAddress extends DialogFragment implements
        View.OnClickListener, OnMapReadyCallback {

    public Dialog d;

    Double Lat;
    Double Long;
    String state,country,feature,Address,city,postalCode;
    TextView myAddress;
    Button SelectBtn;
    Button ChangeBtn;
    AddressMapActivity addressMapActivity;
    @SuppressLint("StaticFieldLeak")
    public static org.findar.user.Adapter.ConfirmAddress confirmAddress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Lat = getArguments().getDouble("lat");
        Long = getArguments().getDouble("long");
        Address = getArguments().getString("address");
        city = getArguments().getString("city");
        state = getArguments().getString("state");
        country = getArguments().getString("country");
//        feature = getArguments().getString("feature");
        postalCode = getArguments().getString("postalCode");

        this.addressMapActivity = AddressMapActivity.addressMapActivity;
        confirmAddress=this;

    }
    MapFragment mapFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_confirm_address, container, false);
        myAddress=(TextView)v.findViewById(R.id.myAddress);
        SelectBtn=(Button) v.findViewById(R.id.Select);
        ChangeBtn=(Button) v.findViewById(R.id.Change);



        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapp);
        mapFragment.getMapAsync(this);
        // Toast.makeText(getActivity(),mNum,Toast.LENGTH_LONG).show();

        SelectBtn.setOnClickListener(v1 -> {

           // this.addressMapActivity = AddressMapActivity.addressMapActivity;
//            System.out.println("AddressActivity"+(AddressMapActivity) getActivity() + " ~ "+Lat + " ~ "+Long);
            //addressMapActivity.GotoNextPage();
            System.out.println("Activity Name"+getActivity().getComponentName());
            if(getActivity().getComponentName().toString().contains("AddressMapActivity"))
                addressMapActivity.GotoNextPage();
            else
                ((MapActivity) getActivity()).GotoNextPage();

//                Toast.makeText(getActivity(),myAddress.getText().toString()+"Lat="+Lat+"Long"+Long,Toast.LENGTH_LONG).show();
//                getFragmentManager().beginTransaction().remove(mapFragment).commit();
//                dismiss();
//                Intent i= new Intent(getContext(), AddMainActivity.class);
//                i.putExtra("Address", String.valueOf(myAddress));
//                i.putExtra("city", String.valueOf(city));
//                i.putExtra("postalcode", String.valueOf(postalCode));
//                i.putExtra("lat",Lat);
//                i.putExtra("lan",Long);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                getContext().startActivity(i);

        });



        ChangeBtn.setOnClickListener(v12 -> {
            getFragmentManager().beginTransaction().remove(mapFragment).commit();
            dismiss();
        });
        getDialog().setCanceledOnTouchOutside(true);
        return v;

    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        getFragmentManager().beginTransaction().remove(mapFragment).commit();

    }

//    @Override
//    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);
//        dismiss();
//    }

    @Override
    public void onClick(View v) {

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        String Addressnew = Address+" ,"+ city+" ,"+state+" ,"+country+" ,"+postalCode;
        myAddress.setText(Addressnew);
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(Lat,Long));

        markerOptions.title("");
        googleMap.clear();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                new LatLng(Lat,Long), 16f);
        googleMap.animateCamera(location);
        googleMap.addMarker(markerOptions);
        Log.d("status","success");
    }

}