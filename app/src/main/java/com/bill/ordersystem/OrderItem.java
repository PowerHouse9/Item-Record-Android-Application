package com.bill.ordersystem;

public class OrderItem {
    private String OrderName, Supplier, Quantity, Date, Description;

    //empty constructor needed
    public OrderItem() {

    }

    public OrderItem (String OrderName, String Supplier, String Quantity, String Date, String Description) {
        this.OrderName = OrderName;
        this.Supplier = Supplier;
        this.Quantity = Quantity;
        this.Date = Date;
        this.Description = Description;
    }

    public String getOrderName() {
        return OrderName;
    }

    public String getSupplier() {
        return Supplier;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getDate() {
        return Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setOrderName(String orderName) {
        OrderName = orderName;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
