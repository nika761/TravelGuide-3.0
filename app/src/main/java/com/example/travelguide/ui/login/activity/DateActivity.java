package com.example.travelguide.ui.login.activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.travelguide.R;
import com.example.travelguide.helper.HelperUI;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateActivity extends AppCompatActivity {

    private TextView dateHead, nickHead, dateOfBirth;
    private EditText eNickName;
    private String birthDate, nickName;
    private Button save;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        initUI();
        setClickListeners();
    }

    private void initUI() {
        dateHead = findViewById(R.id.enter_date_of_birth_head);
        dateOfBirth = findViewById(R.id.enter_birth_date);
        nickHead = findViewById(R.id.enter_nickName_head);
        eNickName = findViewById(R.id.enter_nickName);
        save = findViewById(R.id.enter_info_save);
    }

    void setClickListeners() {

        save.setOnClickListener(v -> {
            if (dateOfBirth.getText().toString().isEmpty()) {
                dateOfBirth.setBackground(getResources().getDrawable(R.drawable.bg_fields_warning));
                dateHead.setText("* Birth Date ");
                dateHead.setTextColor(getResources().getColor(R.color.red));
                YoYo.with(Techniques.Shake)
                        .duration(300)
                        .playOn(dateOfBirth);
            } else {
                dateOfBirth.setBackground(getResources().getDrawable(R.drawable.bg_signup_fields));
                dateHead.setText(" Birth Date ");
                dateHead.setTextColor(getResources().getColor(R.color.black));
                birthDate = dateOfBirth.getText().toString();
            }
            nickName = HelperUI.checkEditTextData(eNickName, nickHead, getString(R.string.nick_name), HelperUI.BLACK, HelperUI.BACKGROUND_DEF_BLACK);
        });

        dateOfBirth.setOnClickListener(v -> showDatePickerDialog());

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d("dates", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

            String dayString = day < 10 ? "0" + day : String.valueOf(day);
            String monthString = month < 10 ? "0" + month : String.valueOf(month);


            Date currentDate = new Date();
            int age = currentDate.getYear() - year;
            if (age < 18) {
                Toast.makeText(this, "18 წელზე მეტის", Toast.LENGTH_SHORT).show();
            } else {
                String date = year + "/" + monthString + "/" + dayString;
                dateOfBirth.setText(date);
            }
        };
    }

    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

}
