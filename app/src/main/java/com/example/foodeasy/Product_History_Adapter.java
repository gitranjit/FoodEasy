package com.example.foodeasy;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodeasy.databinding.HistoryLayoutBinding;

import java.util.ArrayList;

public class Product_History_Adapter extends RecyclerView.Adapter<Product_History_Adapter.ProductHistoryViewHolder> {

    Context context;
    ArrayList<Product_History>product_histories;


    public Product_History_Adapter(Context context, ArrayList<Product_History>product_histories){
        this.context=context;
        this.product_histories=product_histories;
        HttpsTrustManager.allowAllSSL();
    }

    @NonNull
    @Override
    public ProductHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new ProductHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.history_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHistoryViewHolder holder, int position) {
       Product_History product_history=product_histories.get(position);

       holder.binding.dateAndTime.setText(product_history.getDate_and_time());
       holder.binding.totalAmt.setText(product_history.getTotal()+"");
       holder.binding.orderHistoryBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Bundle bundle=new Bundle();
               bundle.putString("date_and_time",product_history.getDate_and_time());
               bundle.putDouble("total_amt",product_history.getTotal());
               bundle.putInt("order_id",product_history.getOrder_id());

               Products_order_history_fragment products_order_history_fragment=new Products_order_history_fragment();
               products_order_history_fragment.setArguments(bundle);


               ((FragmentActivity)view.getContext()).getSupportFragmentManager()
                       .beginTransaction()
                       .replace(R.id.container, products_order_history_fragment).addToBackStack(null)
                       .commit();
           }
       });
    }

    @Override
    public int getItemCount() {
        return product_histories.size();
    }

    public class ProductHistoryViewHolder extends RecyclerView.ViewHolder{
        HistoryLayoutBinding binding;
        public ProductHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= HistoryLayoutBinding.bind(itemView);
        }
    }
}
