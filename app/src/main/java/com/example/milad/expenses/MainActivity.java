package com.example.milad.expenses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.milad.expenses.volley.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    String url = "http://192.168.219.2:8085/test";

    //private TextView txtResponse;
//    private String jsonResponse;
    private static final String TAG = "MainActivity";
    private EditText username;
    private EditText password;
    private Button Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //txtResponse = (TextView) findViewById(R.id.name);
        pDialog = new ProgressDialog(MainActivity.this);


        //font
        Typeface d_n = Typeface.createFromAsset(getAssets(), "fonts/danstevis.ttf");
        TextView a = (TextView) findViewById(R.id.main_textview);
        a.setTypeface(d_n);
        //end font load
        TextView b = (TextView) findViewById(R.id.appname);
        b.setTypeface(d_n);
        /////
        TextView c = (TextView) findViewById(R.id.twitter);
        c.setTypeface(d_n);
        /////
        TextView d = (TextView) findViewById(R.id.google);
        d.setTypeface(d_n);
        /////
        username  = (EditText)findViewById(R.id.Em) ;
        username.setTypeface(d_n);

        /////
         password = (EditText) findViewById(R.id.Pass);
        password.setTypeface(d_n);
        /////
        Register = (Button) findViewById(R.id.Register);
        Register.setTypeface(d_n);
        /////
        TextView h = (TextView) findViewById(R.id.SingIn);
        h.setTypeface(d_n);
        /////
        TextView i = (TextView) findViewById(R.id.Forget);
        i.setTypeface(d_n);
        ////
        TextView j = (TextView) findViewById(R.id.or);
        j.setTypeface(d_n);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pass;
                user = username.getText().toString();
                pass = password.getText().toString();
                makePostReq(user,pass);

            }
        });

    }

//    private void makeStringReq() {
//        showProgressDialog();
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                url, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d(TAG, response.toString());
//
//                try {
//                    // Parsing json object response
//                    // response will be a json object
//                    String name = response.getString("name");
//
//
//                    jsonResponse = "";
//                    jsonResponse += "Name: " + name + "\n\n";
//
//
//                    //txtResponse.setText(jsonResponse);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq);
//
//
//    }

    /**
     * Making json object request
     */
    private void makePostReq(final String username, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject arg = new JSONObject(response);
                            String user = arg.getString("user");
                            String status = arg.getString("status");
                            if(status != "false"){
                                Intent intent = new Intent(MainActivity.this,table.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();
                            }
//
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
}
