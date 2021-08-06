package org.findar.user.Helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.findar.user.R;

import java.util.Map;


public class ApiConfig extends Application {

    public static final String TAG = ApiConfig.class.getSimpleName();
    static ApiConfig mInstance;
    static boolean isDialogOpen = false;
    static RequestQueue mRequestQueue;


    public static String VolleyErrorMessage(VolleyError error) {
        String message = "";
        try {
            if (error instanceof NetworkError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ServerError) {
                message = "The server could not be found. Please try again after some time!!";
            } else if (error instanceof AuthFailureError) {
                message = "Cannot connect to Internet...Please check your connection!";
            } else if (error instanceof ParseError) {
                message = "Parsing error! Please try again after some time!!";
            } else if (error instanceof TimeoutError) {
                message = "Connection TimeOut! Please check your internet connection.";
            } else
                message = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


    @SuppressLint("DefaultLocale")
    public static String StringFormat(String number) {
        return String.format("%.2f", Double.parseDouble(number));
    }


    public static void RequestToVolleyPost(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final boolean isprogress) {
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        final ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        progressDisplay.hideProgress();
        if (ApiConfig.isConnected(activity)) {
            if (isprogress)
                progressDisplay.showProgress();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
                callback.onSuccess(true, response);
                if (isprogress)
                    progressDisplay.hideProgress();

            },
                    error -> {
                        if (isprogress)
                            progressDisplay.hideProgress();
                        callback.onSuccess(false, "");
                        String message = VolleyErrorMessage(error);
                        if (!message.equals(""))
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }) {


                @Override
                protected Map<String, String> getParams() {
                    return params;
                }
            };

            ApiConfig.getInstance().getRequestQueue().getCache().clear();
            ApiConfig.getInstance().addToRequestQueue(stringRequest);
        }

    }

    public static void RequestToVolleyGet(final VolleyCallback callback, final Activity activity, final String url, final Map<String, String> params, final boolean isprogress) {
        if (ProgressDisplay.mProgressBar != null) {
            ProgressDisplay.mProgressBar.setVisibility(View.GONE);
        }
        final ProgressDisplay progressDisplay = new ProgressDisplay(activity);
        progressDisplay.hideProgress();
        if (ApiConfig.isConnected(activity)) {
            if (isprogress)
                progressDisplay.showProgress();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                callback.onSuccess(true, response);
                if (isprogress)
                    progressDisplay.hideProgress();

            },
                    error -> {
                        if (isprogress)
                            progressDisplay.hideProgress();
                        callback.onSuccess(false, "");
                        String message = VolleyErrorMessage(error);
                        if (!message.equals(""))
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
                    }) {


                @Override
                protected Map<String, String> getParams() {
                    return params;
                }
            };

//            stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
            ApiConfig.getInstance().getRequestQueue().getCache().clear();
            ApiConfig.getInstance().addToRequestQueue(stringRequest);
//            mRequestQueue.add(stringRequest);
        }

    }

    public static Boolean isConnected(final Activity activity) {
        boolean check = false;
        try {
            ConnectivityManager ConnectionManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                check = true;
            } else {
                try {
                    if (!isDialogOpen) {
                        @SuppressLint("InflateParams") View sheetView = activity.getLayoutInflater().inflate(R.layout.dialog_no_internet, null);
                        ViewGroup parentViewGroup = (ViewGroup) sheetView.getParent();
                        if (parentViewGroup != null) {
                            parentViewGroup.removeAllViews();
                        }

                        final Dialog mBottomSheetDialog = new Dialog(activity);
                        mBottomSheetDialog.setContentView(sheetView);
                        mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        mBottomSheetDialog.show();
                        isDialogOpen = true;
                        Button btnRetry = sheetView.findViewById(R.id.btnRetry);
                        mBottomSheetDialog.setCancelable(false);

                        btnRetry.setOnClickListener(view -> {
                            if (isConnected(activity)) {
                                isDialogOpen = false;
                                mBottomSheetDialog.dismiss();
                            }
                        });
                    }
                } catch (Exception ignored) {

                }
            }
        } catch (Exception ignored) {

        }
        return check;
    }

    public static synchronized ApiConfig getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        }

        return mRequestQueue;
    }

    //
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
}
