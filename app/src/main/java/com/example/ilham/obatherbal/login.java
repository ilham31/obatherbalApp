package com.example.ilham.obatherbal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    TextView registUser;
    EditText email,password;
    Button signIn;
    String getEmail,getPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        registUser = (TextView) findViewById(R.id.registerUser);
        signIn = (Button) findViewById(R.id.signInButton);


        registUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,register.class);
                startActivity(i);
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/user/signin";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("email",email.getText().toString());
                    jsonBody.put("password",password.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("item","email pass"+getEmail+getPassword);

                JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url,jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean responseStatus = response.getBoolean("success");
                            if (responseStatus)
                            {
                                String token = response.getString("token");
                                Log.d("response token","response token ="+token);
                                SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                preferences.edit().putString("token", token).commit();
                                onBackPressed();
                            }
                            else
                            {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        parseVolleyError(error);
                    }
                })
                {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };
                MySingleton.getInstance(login.this).addToRequestQueue(loginRequest);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            JSONArray errors = data.getJSONArray("errors");
            JSONObject jsonMessage = errors.getJSONObject(0);
            String message = jsonMessage.getString("message");
            Log.d("error login","error login ="+message);
        } catch (JSONException e) {
        } catch (UnsupportedEncodingException errorr) {
        }
    }
}
