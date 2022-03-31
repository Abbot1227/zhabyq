package com.example.zhabyq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CheckQrActivity extends AppCompatActivity implements Animation.AnimationListener {

    private String mJSONURLString = "https://stat.gov.kz/api/juridical/counter/api/?bin=";
    private String phoneNumberS, iinS;
    //150940005230 &lang=ru

    private TextView entry_place, biin1, entrynum1, scan_time, expiry_time, phone_number, iin, status_display;
    private Context mContext;
    private CountDownTimer countDownTimer;
    private long timeLeft = 300000; // 5 минут
    private boolean timerRunning;
    private Animation anim;
    private CardView card;
    private ImageView image_info;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String IIN = "iiN";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_qr);

        mContext = getApplicationContext();
        entry_place = findViewById(R.id.entry_place);
        biin1 = findViewById(R.id.biin1);
        entrynum1 = findViewById(R.id.entrynum1);
        scan_time = findViewById(R.id.scan_time);
        expiry_time = findViewById(R.id.expiry_time);
        phone_number = findViewById(R.id.phone_number);
        iin = findViewById(R.id.iin);
        card = findViewById(R.id.card);
        status_display = findViewById(R.id.status_display);
        image_info = findViewById(R.id.image_info);


        String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date());


        Intent intent = getIntent();
        String biin = intent.getStringExtra("intentData").substring(56, 68);
        String enter = intent.getStringExtra("intentData").substring(71);
        String entry = enter.substring(0, enter.indexOf('&'));

        mJSONURLString = mJSONURLString + biin + "&lang=ru";
        anim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.animations);
        anim.setAnimationListener(this);


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject object = response.getJSONObject("obj");

                            String name = object.getString("name");

                            entry_place.append(name);
                            entrynum1.append(entry);
                            biin1.append(biin);
                            scan_time.append(currentDate);

                            loadData();

                            card.startAnimation(anim);
                            status_display.startAnimation(anim);
                            image_info.startAnimation(anim);


                            startStop();
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Put something here (maybe Toast message)
                    }
                }
        );

        // Add JsonArrayRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    public void closePass(View view) {
        finish();
    }

    public void startStop() {
        if (timerRunning) {
            stopTimer();
        }
        else {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        timerRunning = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    public void updateTimer() {
        int minute = (int) timeLeft / 60000;
        int second = (int) timeLeft % 60000 / 1000;
        int millisecond = (int) timeLeft % 1000;

        String timeLeftText = (String.format("%02d", minute) + ":" + String.format("%02d", second) + ":" + String.format("%03d", millisecond));

        expiry_time.setText(timeLeftText);
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        phoneNumberS = sharedPreferences.getString(PHONE_NUMBER, "+71234567890");
        iinS = sharedPreferences.getString(IIN, "020202505050");

        phone_number.append(phoneNumberS);
        iin.append(iinS);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == anim) {

        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
