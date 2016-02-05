package com.example.admin.retrotrain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LoginSuccess extends AppCompatActivity {

    Button loguot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences get_shared_preference = getSharedPreferences("loginsucces", MODE_PRIVATE);
        get_shared_preference.getString("email","");
        get_shared_preference.getString("token_authentication","");

        loguot = (Button)findViewById(R.id.btnLogout);
        loguot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginSuccess.this, RetrofitTrain.class);
                startActivity(i);

                SharedPreferences preferences =getSharedPreferences("loginsucces", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
                }

            });
        }
    }
