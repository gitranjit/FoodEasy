package com.example.foodeasy;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class Home_Fragment extends Fragment {
    Product_adapter product_adapter;
    static  ArrayList<Product>products;
    ArrayList<Product>filterd_list;
    RecyclerView recyclerView;
    SearchView searchView;

    public final String apiurl="https://192.168.146.103/android/product_data_fetch.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home_, container, false);

        HttpsTrustManager.allowAllSSL();

        initiateProducts(view);

        searchView=view.findViewById(R.id.searchview);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filterd_list=new ArrayList<>();
                if(s.length()>0){
                    for(Product product :products){
                        if(product.getName().toLowerCase().contains(s.toLowerCase()) || product.getDescription().toLowerCase().contains(s.toLowerCase())||product.getCategory().toLowerCase().contains(s.toLowerCase())){
                            filterd_list.add(product);
                        }
                    }
                    product_adapter=new Product_adapter(getContext(),filterd_list);
                    if(filterd_list.size()==0){
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                    }
                    else{
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView=view.findViewById(R.id.product_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(product_adapter);
                    }
                }
                else{
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchproductdata(view);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.length()>0){
                    filterd_list=new ArrayList<>();
                    for(Product product :products){
                        if(product.getName().toLowerCase().contains(s.toLowerCase()) || product.getDescription().toLowerCase().contains(s.toLowerCase())||product.getCategory().toLowerCase().contains(s.toLowerCase())){
                            filterd_list.add(product);
                        }
                    }
                    product_adapter=new Product_adapter(getContext(),filterd_list);
                    if(filterd_list.size()==0){
                        Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                    }
                    else{
                        recyclerView.setVisibility(View.VISIBLE);
                        recyclerView=view.findViewById(R.id.product_recycler_view);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(product_adapter);
                    }

                }
                else{
                    recyclerView.setVisibility(View.VISIBLE);
                    fetchproductdata(view);
                }
                return true;
            }
        });



        return view;
    }

    private void initiateProducts(View view) {

        fetchproductdata(view);
    }

    private void fetchproductdata(View view) {

        class dbManager extends AsyncTask<String,Void,String>
        {
            protected void onPostExecute(String data)
            {
                try{
                    JSONArray ja=new JSONArray(data);
                    JSONObject jo=null;
                    products=new ArrayList<>();

                    for(int i=0; i<ja.length(); i++){
                        jo=ja.getJSONObject(i);
                        Product product=new Product(jo.getString("name"), jo.getString("description"),jo.getString("image"), jo.getString("category"), jo.getDouble("price"),jo.getInt("id") );
                        products.add(product);
                    }
                    product_adapter=new Product_adapter(getContext(),products);
                    recyclerView=view.findViewById(R.id.product_recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(product_adapter);

                }catch (Exception ex)
                {
                    Toast.makeText(getContext(),data,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuffer data = new StringBuffer();
                    String line;

                    while((line=br.readLine())!=null)
                    {
                        data.append(line+"\n");
                    }
                    br.close();
                    return data.toString();
                }catch (Exception ex)
                {
                    return ex.getMessage();
                }
            }
        }

        dbManager obj= new dbManager();
        obj.execute(apiurl);


    }
}