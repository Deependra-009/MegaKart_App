package com.example.app.Model;

public class Addtocartmodel {

    public String name;
    public String price;
    public String url;
    public String quantity;
    public String id;
    public String newprice;

    public Addtocartmodel() {
    }

    public Addtocartmodel(String name, String price, String url, String quantity,String id,String newprice) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.quantity = quantity;
        this.id=id;
        this.newprice=newprice;
    }
}
