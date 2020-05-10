package com.example.shoppers_beta.model;

public class Cart {
    private String pid,pname,price,qty,discount,oprice;

    public String getOprice() {
        return oprice;
    }

    public void setOprice(String oprice) {
        this.oprice = oprice;
    }

    public Cart() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Cart(String pid, String pname, String price, String qty, String discount, String oprice) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.qty = qty;
        this.discount = discount;
        this.oprice = oprice;
    }

}
