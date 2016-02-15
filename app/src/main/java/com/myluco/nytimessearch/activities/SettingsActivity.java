package com.myluco.nytimessearch.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.myluco.nytimessearch.R;
import com.myluco.nytimessearch.model.Settings;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SettingsActivity extends AppCompatActivity {
    private Settings localSettings;
    private EditText etDate;
    private RadioGroup rgSortOrder;
    private CheckBox cbArts;
    private CheckBox cbFashion;
    private CheckBox cbSports;
    private DatePickerDialog datePickerDialog;
    private Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        localSettings = (Settings) getIntent().getSerializableExtra("settings");

        getViews();
        setupListeners();
        populate();
    }




    private void getViews() {
        etDate = (EditText)findViewById(R.id.etDate);
        rgSortOrder = (RadioGroup)findViewById(R.id.rgSortOrder);
        cbArts = (CheckBox) findViewById(R.id.cbArts);
        cbFashion = (CheckBox)findViewById(R.id.cbFashion);
        cbSports = (CheckBox)findViewById(R.id.cbSports);
        btSave = (Button) findViewById(R.id.btSave);
    }


    private void populate() {
        if (localSettings.getYear() != 0 ) {
            etDate.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(localSettings.getMonth() + 1).append("-").append(localSettings.getDay()).append("-")
                    .append(localSettings.getYear()));
        }

        etDate.setInputType(InputType.TYPE_NULL);

        setSortOrder();
        setNewsDesk();


    }

    private void setSortOrder() {
        if (localSettings.isOldest()) {
            rgSortOrder.check(R.id.rbOldest);
        } else {
           rgSortOrder.check(R.id.rbNewest);
        }

    }

    private void setNewsDesk() {
        if (localSettings.artSet()) {
            cbArts.setChecked(true);
        } else {
            cbArts.setChecked(false);
        }

        if (localSettings.fashionSet()){
            cbFashion.setChecked(true);
        } else {
            cbFashion.setChecked(false);
        }

        if (localSettings.sportSet()) {
            cbSports.setChecked(true);
        }else {
            cbSports.setChecked(false);
        }

    }

    private void setupListeners() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave(v);
            }
        });
        setupNewsDeskListener();
        setupSortOrderListener();
        setupDateListener();
    }

    private void setupSortOrderListener() {
         rgSortOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected

                if (checkedId == R.id.rbOldest) {
                    localSettings.setOldest(true);

                } else {
                    localSettings.setOldest(false);
                }

            }
        });
    }

    private void setupNewsDeskListener() {
        cbArts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localSettings.setArts(isChecked);
            }
        });
        cbFashion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localSettings.setFashion(isChecked);
            }
        });
        cbSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                localSettings.setSports(isChecked);
            }
        });
    }


    private void setupDateListener() {
        final Calendar newCalendar = new GregorianCalendar();
        etDate.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          if (localSettings.getYear() != 0) {
                                              datePickerDialog.updateDate(
                                                      localSettings.getYear(),
                                                      localSettings.getMonth(),
                                                      localSettings.getDay());
                                          }

                                          datePickerDialog.show();
                                      }
                                  }

        );


        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int month, int day) {

                newCalendar.set(year, month, day);
                newCalendar.set(GregorianCalendar.HOUR_OF_DAY, 23);
                newCalendar.set(GregorianCalendar.MINUTE, 59);
                newCalendar.set(GregorianCalendar.SECOND, 0);
                etDate.setText(new StringBuilder()
                        // Month is 0 based, just add 1
                        .append(month + 1).append("-").append(day).append("-")
                        .append(year).append(" "));
                localSettings.setDay(day);
                localSettings.setMonth(month);
                localSettings.setYear(year);
            }

        },newCalendar.get(GregorianCalendar.YEAR), newCalendar.get(GregorianCalendar.MONTH), newCalendar.get(GregorianCalendar.DAY_OF_MONTH));

    }
    public void onSave(View view) {
//        settings = localSettings;
        Intent data = new Intent();
        data.putExtra("settings",localSettings);
        setResult(RESULT_OK, data);
        Log.v("Debug",localSettings.toString());
        finish();
    }

}
