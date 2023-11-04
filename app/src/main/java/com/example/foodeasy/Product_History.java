package com.example.foodeasy;

public class Product_History {
    private  String name,date_and_time;
    private  int count,order_id;
    private  double total;

    public Product_History(String date_and_time, double total,int order_id) {
//        this.name = name;
        this.date_and_time = date_and_time;
//        this.count = count;
        this.total = total;
        this.order_id=order_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
