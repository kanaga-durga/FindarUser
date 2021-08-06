package org.findar.user.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.findar.user.Helper.Constant;
import org.findar.user.Helper.UICommon;
import org.findar.user.R;

public class DescribeProblemActivity extends AppCompatActivity {

    TextView get_txt_sub_problem,txt_enter_order_no,txt_describe_continue,describe_skip,textViewOrder;
    EditText et_describe_detail_problem;

    Activity activity;
    UICommon uicommon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe_problem);

        activity = this;

        View includedLayout = findViewById(R.id.back_arrow);
        uicommon = new UICommon();
        uicommon.BackButtonPress(includedLayout,DescribeProblemActivity.this);

        get_txt_sub_problem = findViewById(R.id.get_txt_sub_problem);
        txt_enter_order_no = findViewById(R.id.txt_enter_order_no);
        textViewOrder = findViewById(R.id.textViewOrder);
        txt_describe_continue = findViewById(R.id.txt_describe_continue);
        describe_skip = findViewById(R.id.describe_skip);
        et_describe_detail_problem = findViewById(R.id.et_describe_detail_problem);

        get_txt_sub_problem.setText(Constant.SPL_NAME);
        System.out.println("SUBPROBLEM : "+Constant.SPL_NAME);
        if (Constant.SPL_NAME.equals("other") || Constant.SPL_NAME.equals("Other")){
            describe_skip.setVisibility(View.GONE);
        }

        textViewOrder.setOnClickListener(v -> {
            Context context= this;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setTitle(getResources().getString(R.string.do_you_want_add_order_bo));

            alertDialogBuilder
                    .setMessage(getResources().getString(R.string.demo_content))
                    .setCancelable(false)
                    .setPositiveButton("Ok", (dialog, id) -> dialog.cancel());
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });
        txt_describe_continue.setOnClickListener(v -> {
            Constant.DESCRIBE_PROBLEM = et_describe_detail_problem.getText().toString();
            Constant.ORDERNO = txt_enter_order_no.getText().toString();
            Intent intent = new Intent(getApplicationContext(),AddPhotosActivity.class);
            startActivity(intent);
        });

    }
}