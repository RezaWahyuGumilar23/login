package com.example.admin.retrotrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RetrofitTrain extends AppCompatActivity implements View.OnClickListener{
    EditText eTmail, pass;
    Button btnlog;
    int idTamp;
    String passTamp, authTamp, emTamp;
    TextView tv_respond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_train);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eTmail = (EditText) findViewById(R.id.eTemail);
        pass = (EditText) findViewById(R.id.eTpass);
        tv_respond = (TextView) findViewById(R.id.tv_respond);
        btnlog = (Button) findViewById(R.id.btnLogin);
        btnlog.setOnClickListener(this);
        tv_respond.setVisibility(View.INVISIBLE);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://private-988f7-trainretrofit.apiary-mock.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UserApi user_api = retrofit.create(UserApi.class);
        Call<Users> call = user_api.getUsers();
        call.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Response<Users> response, Retrofit retrofit) {
                int status = response.code();
                tv_respond.setText(String.valueOf(status));
                    for (Users.UserItem user : response.body().getUsers()) {
                        idTamp = user.getId();
                        emTamp = user.getEmail();
                        passTamp = user.getPassword();
                        authTamp = user.getToken_auth();
                    }
            }

            @Override
            public void onFailure(Throwable t) {

                tv_respond.setText(String.valueOf(t));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_retrofit_train, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                ambil();
                break;
        }
    }
    private void ambil() {
        if (emTamp.equals(eTmail.getText().toString()) && passTamp.equals(pass.getText().toString())) {
            Intent in = new Intent(RetrofitTrain.this, LoginSuccess.class);
            startActivity(in);
            Toast.makeText(RetrofitTrain.this, "Welcome "+emTamp, Toast.LENGTH_SHORT).show();

            SharedPreferences set_shared_preference = getSharedPreferences("loginsucces", MODE_PRIVATE);
            SharedPreferences.Editor sp_editor = set_shared_preference.edit();
            sp_editor.putString("email", emTamp);
            sp_editor.putString("token_authentication", authTamp);
            sp_editor.commit();

            finish();
        } else {
            Toast.makeText(RetrofitTrain.this, "Failed Log In", Toast.LENGTH_SHORT).show();
        }
    }
}
