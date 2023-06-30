package com.example.foodeasy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Register extends AppCompatActivity {

    TextView signup;
    LinearLayout signin;
    TextInputEditText firstname,lastname,email,phone,password;

    private final String url="https://192.168.1.9/android/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).hide();

        signup=findViewById(R.id.txt_signup);
        signin=findViewById(R.id.txt_signin);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.passowrd);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(firstname.getText().toString().trim().isEmpty()){
                    firstname.setError("Enter First Name");
                }
                else if(lastname.getText().toString().trim().isEmpty()){
                    lastname.setError("Enter Last Name");
                }
                else if(email.getText().toString().trim().isEmpty()){
                    email.setError("Enter valid Email");
                }
                else if(phone.getText().toString().trim().isEmpty()){
                    phone.setError("Enter Valid Phone");
                }
                else if(password.getText().toString().trim().length()<6){
                    password.setError("Password should have minumum six characters");
                }
                else{

                    HttpsTrustManager.allowAllSSL();
                    StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("exist")){
                                Toast.makeText(Register.this, "email exists", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Register.this, "Inserted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this,Login.class));
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Register.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    ){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                             Map<String,String> map=new HashMap<String,String>();
                             map.put("first_name",firstname.getText().toString().trim());
                             map.put("last_name",lastname.getText().toString().trim());
                             map.put("email",email.getText().toString().trim());
                             map.put("phone",phone.getText().toString().trim());
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
}