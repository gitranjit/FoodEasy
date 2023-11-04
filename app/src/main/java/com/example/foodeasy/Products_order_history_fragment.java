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

public class Products_order_history_fragment extends Fragment {

    String apiurl="https://192.168.146.103/android/Products_in_history.php";
    RecyclerView recyclerView;
    TextView date_and_time,total;
    int order_id;

    double total_amt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_products_order_history_fragment, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        date_and_time=view.findViewById(R.id.date_and_time);
        total=view.findViewById(R.id.total_amt);

        Bundle arguments = getArguments();
        if (arguments!=null){
            order_id= (int)arguments.get("order_id");
            total.setText(arguments.get("total_amt").toString());
            date_and_time.setText(arguments.get("date_and_time").toString());
        }
        else{
            total.setText("");
            date_and_time.setText("");
        }

        fetchdata();

        return view;
    }

    private void fetchdata() {
        StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray ja=new JSONArray(response);
                    JSONObject jo=null;
                    ArrayList<Product_history_sample>product_history_samples=new ArrayList<>();

                    for(int i=0; i<ja.length(); i++){
                        jo=ja.getJSONObject(i);
                        product_history_samples.add(new Product_history_sample(jo.getString("product_name"),jo.getInt("product_count"),jo.getDouble("price")));
                    }
                    Product_history_sample_adapter product_history_sample_adapter=new Product_history_sample_adapter(getContext(),product_history_samples);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(product_history_sample_adapter);

                }catch (Exception ex)
                {
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
                Map<String,String> map=new HashMap<String,String>();
                map.put("order_id",order_id+"");
                return map;

            }
        };
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}