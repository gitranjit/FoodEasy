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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Cart_Fragment extends Fragment {

    String apiurl="https://192.168.146.103/android/cart_fetch_data.php";
    RecyclerView recyclerView;
    TextView total;

    LinearLayout checkout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_cart_, container, false);
        HttpsTrustManager.allowAllSSL();
        recyclerView=view.findViewById(R.id.history_recycler_view);
        total=view.findViewById(R.id.total_amt);
        checkout=view.findViewById(R.id.checkout);


        fetchdata(view,total,checkout);
        return view;
    }

    private void fetchdata(View view,TextView total,LinearLayout checkout) {
        StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray ja=new JSONArray(response);
                    ArrayList<Product_Cart> product_histories=new ArrayList<>();

                    for(int i=0; i<ja.length(); i++){
                        JSONObject jo=ja.getJSONObject(i);
                        Product_Cart product=new Product_Cart(jo.getString("name"),jo.getInt("id"),jo.getInt("count"),jo.getDouble("price"),0);
                        product_histories.add(product);
                    }

                    Product_Cart_Adapter product_history_adapter=new Product_Cart_Adapter(getContext(),product_histories,total,checkout);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(product_history_adapter);



                }catch (Exception ex)
                {
//                    Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
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
                final String login_status = "login_status" ;
                SharedPreferences sharedpreferences = getContext().getSharedPreferences(login_status, Context.MODE_PRIVATE);
                String user_mail= sharedpreferences.getString("email",null);
                map.put("email",user_mail);
                return map;

            }
        };
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}