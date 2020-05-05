package com.priyo.testreport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.priyo.testreport.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Clickhandler clickhandler;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clickhandler = new Clickhandler(this);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setClicks(clickhandler);


    }

    public class Clickhandler {
        Context c;

        Clickhandler(Context c) {
            this.c = c;
        }

        public void clickViewReportsBtn(View view) {
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();

            builder.setTitleText("Enter a date range");

            MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

            materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

            materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                @Override
                public void onPositiveButtonClick(Pair<Long, Long> selection) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");


                    Date dateD = new Date(selection.first );
                    Date date2 = new Date(selection.second );

                    String startDate = dateFormat.format(dateD);

                    String endDate = dateFormat.format(date2);

                    Log.i("Prio", startDate +":" + endDate);

                    Intent i = new Intent ( getApplicationContext(), TestCases.class);
                    i.putExtra("from",startDate);
                    i.putExtra("to",endDate);
                    startActivity(i);
                }
            });

            /*Pair<Long, Long> pair = materialDatePicker.getSelection();

             */

        }
    }

}
