package com.example.talent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private EditText etName, etEmail, etPassword, etRepeatPassword;
    private String name, email, password, repeatPassword;
    private String URL="http://192.168.43.186/login/register.php";
    private Button btnRegister;
    private TextView tvStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRepeatPassword = findViewById(R.id.etRepeatPassword);
        tvStatus = findViewById(R.id.tvStatus);
        btnRegister = findViewById(R.id.btnRegister);
        name = email = password = repeatPassword = "";
    }

    public void save(View view) {
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        repeatPassword = etRepeatPassword.getText().toString().trim();
        if(!password.equals(repeatPassword)){
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }else if(!name.equals("") && !email.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(Register.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                        btnRegister.setClickable(false);
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (response.equals("failure")) {
                        Toast.makeText(Register.this, "Something went wrong. Email is already taken", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("name", name);
                    data.put("email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}