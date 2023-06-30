package com.example.foodeasy;

import android.content.Context;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodeasy.databinding.ProductsLayoutBinding;

import java.util.ArrayList;

public class Product_adapter extends RecyclerView.Adapter<Product_adapter.ProductViewHolder> {

//   ProductsLayoutBinding binding;

   Context context;
   ArrayList<Product>products;

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
