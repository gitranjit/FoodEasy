package com.example.foodeasy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {

    LinearLayout signup;
    TextView signin,forgot_password;
    TextInputEditText email,password;

    private final String url="https://192.168.1.9/android/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        signup=findViewById(R.id.txt_signup);
        signin=findViewById(R.id.txt_signin);
        forgot_password=findViewById(R.id.fogotpassword);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);

        final String login_status = "login_status" ;
        SharedPreferences sharedpreferences = this.getSharedPreferences(login_status, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if(sharedpreferences.getBoolean(login_status,false)){
            startActivity(new Intent(Login.this,Marketplace.class));
            finish();
        }


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
                finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                if(email.getText().toString().trim().isEmpty()){
                    email.setError("Enter valid Email");
                }
                else if(password.getText().toString().trim().isEmpty()){
                    password.setError("Password should have minumum six characters");
                }
                else{

                    HttpsTrustManager.allowAllSSL();
                    StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(Objects.equals(response, "success")){
                                editor.putBoolean(login_status,true);
                                editor.putString("email",email.getText().toString().trim());
                                editor.apply();
                                startActivity(new Intent(Login.this,Marketplace.class));
                                finish();
                            }

                            Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    ){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map=new HashMap<String,String>();
                            map.put("email",email.getText().toString().trim());
                            map.put("password",password.getText().toString().trim());

                            return map;

                        }
                    };
                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}