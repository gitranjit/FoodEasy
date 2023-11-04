package com.example.foodeasy;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.foodeasy.databinding.ProductsLayoutBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product_adapter extends RecyclerView.Adapter<Product_adapter.ProductViewHolder> {

   Context context;
   ArrayList<Product>products;

   String apiurl="https://192.168.146.103/android/cart_add_data.php";

   public Product_adapter(Context context,ArrayList<Product>products){
       this.context=context;
       this.products=products;
   }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.products_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product=products.get(position);
        Glide.with(context).load(product.getImage()).into(holder.binding.productImage);
        holder.binding.productDescription.setText(product.getDescription());
        holder.binding.productName.setText(product.getName());
        holder.binding.productPrice.setText("INR "+product.getPrice());

        holder.binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                ){
                    @Nullable
                    @Override
                    protected Map getParams() throws AuthFailureError {
                        final String login_status = "login_status" ;
                        SharedPreferences sharedpreferences = context.getSharedPreferences(login_status, Context.MODE_PRIVATE);
                        String user_mail= sharedpreferences.getString("email",null);
                        Map map=new HashMap();
                        map.put("email",user_mail);
                        map.put("count","1");
                        map.put("product_id",product.getId()+"");

                        return map;

                    }
                };
                RequestQueue queue= Volley.newRequestQueue(context);
                queue.add(request);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ProductsLayoutBinding binding;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=ProductsLayoutBinding.bind(itemView);
        }
    }
}
