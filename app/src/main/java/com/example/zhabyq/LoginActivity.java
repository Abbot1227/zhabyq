package com.example.zhabyq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText edit_phone, enter_iin;
    private String phone_num;
    private CheckBox checkBox;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String IIN = "iiN";

    private String phoneNumberS, iinS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edit_phone = findViewById(R.id.edit_phone);
        enter_iin = findViewById(R.id.enter_iin);
        checkBox = findViewById(R.id.cb_policy);

        loadData();
    }

    public void startQrActivityTest(View view){
        if (edit_phone.getText().length() != 12 & enter_iin.getText().length() != 12){
            Toast.makeText(getApplicationContext(), "Неправильный ИИН или номер телефона", Toast.LENGTH_SHORT).show();
        }
        else if (!checkBox.isChecked()) {
            Toast.makeText(getApplicationContext(), "Примите условия работы", Toast.LENGTH_SHORT).show();
        }
        else{
            //VariablesForApplication.setPhoneNumber(edit_phone.getText().toString());
            //VariablesForApplication.setIin(enter_iin.getText().toString());

            saveData();

            Intent intent = new Intent(this, QrReaderActivity.class);
            startActivity(intent);
        }

    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PHONE_NUMBER, edit_phone.getText().toString());
        editor.putString(IIN, enter_iin.getText().toString());

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        phoneNumberS = sharedPreferences.getString(PHONE_NUMBER, "");
        iinS = sharedPreferences.getString(IIN, "");

        edit_phone.append(phoneNumberS);
        enter_iin.append(iinS);
    }
}