package com.example.ilham.obatherbal;

import android.content.SharedPreferences;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    EditText email,password,name;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = (EditText) findViewById(R.id.emailRegister);
        password = (EditText) findViewById(R.id.passwordRegister);
        name = (EditText) findViewById(R.id.nameUser);
        register = (Button) findViewById(R.id.RegisterButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ci.apps.cs.ipb.ac.id/jamu/api/user/signup";
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("email",email.getText().toString());
                    jsonBody.put("password",password.getText().toString());
                    jsonBody.put("name",name.getText().toString());
                    Log.d("input","input ="+email.getText().toString()+password.getText().toString()+name.getText().toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest registerRequest = new JsonObjectRequest(Request.Method.POST, url,jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean responseStatus = response.getBoolean("success");
                            if (responseStatus)
                            {
                                Toast.makeText(getApplicationContext(), response.getString("message"),
                                        Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), response.getString("message"),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
                MySingleton.getInstance(register.this).addToRequestQueue(registerRequest);
            }
        });
            }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
