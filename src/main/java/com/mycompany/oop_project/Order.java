package com.mycompany.oop_project;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private Cashier cashier;
    private Customer customer;
    private ArrayList<OrderItem> itemsList;
    private String orderStatus;
    private String paymentStatus;
    private String paymentMethod;

    public Order(int orderId, Cashier cashier, ArrayList<OrderItem> itemsList, String orderStatus, String paymentStatus, String paymentMethod) {
        this.orderId = orderId;
        this.cashier = cashier;
        this.itemsList = itemsList;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }
    
    public void addItem(MenuItem item, int qty){
        
    }

    public double calculateTotalBeforeDiscount(){
        return 0;
    }
    
    public double calculateFinalTotal() {
        return 0;
    }
    
    // calls add points (loyal, visitcount) for customer and change status
    public void finalizeOrder(String method) {
        
    }
}
