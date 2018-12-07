package com.amirahmed.egyptiantaxauthority;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ddg";
    CardView card1,card2,card3;

    String GET_JSON_DATA_HTTP_URL;

    RequestQueue requestQueue;

    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tinyDB = new TinyDB(getApplicationContext());

        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);

        Toolbar mToolbar = findViewById(R.id.toolbar_actionbar);

        TextView textView = mToolbar.findViewById(R.id.toolbartext);
        textView.setText("الرئيسية");

        card1.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,AddCar.class);
            startActivity(intent);


        });


        card2.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,CarsListActivity.class);
            startActivity(intent);

            tinyDB.putString("Type","New");

        });


        card3.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this,CarsListActivity.class);
            startActivity(intent);

            tinyDB.putString("Type","Edit");

        });


        //JSON_DATA_WEB_CALL();
        //JSON_DATA_WEB_CALL_HEADER_BODY();

    }


    public void JSON_DATA_WEB_CALL_HEADER_BODY()
    {

        String link = "http://nano-school.com/api/teacher/day_subjects";

        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, link,null,

                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                showMessage(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                showMessage(error.toString());

            }

        }) {

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("email", "N");
                params.put("password", "N");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("token","ymE20l9vfXM1aZB815VVzzBG1gNTgEA0US01Fw5iOxAcTLQOssmJe6eu2XGqkF7IluD0lTi9a0zGZZoolcp1kPCvkswfKvJkYWsI");
                return headers;
            }

            @Override
            public byte[] getBody() {
                HashMap<String, String> params2 = new HashMap<>();
                params2.put("day_id", "29");
                return new JSONObject(params2).toString().getBytes();
            }




            @Override
            protected Response<JSONObject> parseNetworkResponse (NetworkResponse response) {
                try {
                    // solution 1:
                    String jsonString = new String(response.data, "UTF-8");
                    return Response.success(new JSONObject(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException | JSONException e) {
                    return Response.error(new ParseError(e));
                }
            }


            /*@Override
            public String getBodyContentType() {

                return "charset=utf-8";
            }*/
        };



        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(sr);

    }



    public void JSON_DATA_WEB_CALL(){

        String name;
        
        name = "karim";

        GET_JSON_DATA_HTTP_URL = "http://thegreatkiko2090.000webhostapp.com/mobile/My_Update.php?name="+name+"&password=123123&email=k@yahoo.com";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,GET_JSON_DATA_HTTP_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        showMessage(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showMessage(error.getMessage());


                    }
                }




        );





        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }





    private void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_LONG).show();
    }

}
