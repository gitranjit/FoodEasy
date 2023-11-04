package com.example.foodeasy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodeasy.databinding.CartLayoutBinding;
import com.example.foodeasy.databinding.PrductsInHistoryListviewBinding;

import java.util.ArrayList;
import java.util.List;

public class Product_history_sample_adapter extends RecyclerView.Adapter<Product_history_sample_adapter.ProductHistoryViewHolder>{
    Context context;
//    TextView total,date_and_time;
    ArrayList<Product_history_sample>arrayList;

    public Product_history_sample_adapter(Context context,  ArrayList<Product_history_sample> arrayList) {
        this.context = context;
//        this.total = total;
//        this.date_and_time = date_and_time;
        this.arrayList = arrayList;
        HttpsTrustManager.allowAllSSL();
    }

    @NonNull
    @Override
    public ProductHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.prducts_in_history_listview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHistoryViewHolder holder, int position) {
        Product_history_sample product=arrayList.get(position);
        holder.binding.count.setText(product.getCount()+"");
        holder.binding.productName.setText(product.getName());
        holder.binding.price.setText(product.getPrice()*product.getCount()+"");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ProductHistoryViewHolder extends RecyclerView.ViewHolder{

        PrductsInHistoryListviewBinding binding;
        public ProductHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= PrductsInHistoryListviewBinding.bind(itemView);
        }
    }

}


