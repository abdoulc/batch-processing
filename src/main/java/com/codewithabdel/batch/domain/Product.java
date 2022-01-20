package com.codewithabdel.batch.domain;

public class Product {
    private String name;
    private int qty;
    private double price;
    private String description;
    private double total;

    public Product() {
    }

    public Product(String name, int qty, double price, String description, double total) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.description = description;
        this.total = total;
    }
    public Product(String name, int qty, double price, String description) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String toString(){
        return "name: "+ name +", qty: "+qty+", price: "+ price+ ", desc: "+ description+ ", total: "+total;
    }
}
