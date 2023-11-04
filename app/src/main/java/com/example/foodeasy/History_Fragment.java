package com.example.foodeasy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class History_Fragment extends Fragment {

    String apiurl="https://192.168.146.103/android/order_history_data_fetch.php";

    RecyclerView recyclerView;
    Product_History_Adapter product_history_adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_history_, container, false);
        HttpsTrustManager.allowAllSSL();

        recyclerView=view.findViewById(R.id.history_recycler_view);


        fetchdata(view);

        return view;
    }

    private void fetchdata(View view) {
        StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray ja=new JSONArray(response);
                    JSONObject jo=null;

                    ArrayList<Product_History>product_histories=new ArrayList<>();

                    for(int i=0; i<ja.length(); i++){
                        jo=ja.getJSONObject(i);
                        product_histories.add(new Product_History(jo.getString("date_and_time"),jo.getDouble("total_amount"),jo.getInt("order_id")));
                    }
                    product_history_adapter=new Product_History_Adapter(getContext(),product_histories);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(product_history_adapter);

                }catch (Exception ex)
                {
                    System.out.println(response);
                    Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                final String login_status = "login_status" ;
                SharedPreferences sharedpreferences = getContext().getSharedPreferences(login_status, Context.MODE_PRIVATE);
                String user_mail= sharedpreferences.getString("email",null);

                Map<String,String> map=new HashMap<String,String>();
                map.put("email",user_mail);
                return map;

            }
        };
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);

    }


}