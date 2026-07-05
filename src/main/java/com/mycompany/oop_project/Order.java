package com.mycompany.oop_project;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private Customer customer;
    private ArrayList<OrderItem> itemsList;
    private String orderStatus;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.itemsList = new ArrayList<>();
        this.orderStatus = "pending";
    }

    public int getOrderId() { return orderId; }
    public String getFormattedOrderId() { return String.format("%05d", orderId); }
    public Customer getCustomer() { return customer; }
    public ArrayList<OrderItem> getItemsList() { return itemsList; }
    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String status) { this.orderStatus = status; }

    public void addItem(MenuItem item, int qty) {
        itemsList.add(new OrderItem(item, qty));
    }

    public double calculateTotalBeforeDiscount() {
        double total = 0;
        for (OrderItem oi : itemsList) {
            total += oi.calculateSubTotal();
        }
        return total;
    }

    public double calculateFinalTotal() {
        double subTotal = calculateTotalBeforeDiscount();
        double discount = subTotal * customer.getDiscountPercentage(subTotal);
        return subTotal - discount;
    }
}