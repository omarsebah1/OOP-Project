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

    public Order(int orderId, Cashier cashier, Customer customer) {
        this.orderId = orderId;
        this.cashier = cashier;
        this.customer = customer;
        this.itemsList = new ArrayList<>();
        this.orderStatus = "In Progress";
        this.paymentStatus = "Unpaid";
        this.paymentMethod = "None";
    }

    public int getOrderId() {
        return orderId;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<OrderItem> getItemsList() {
        return itemsList;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void addItem(MenuItem item, int qty) {
        this.itemsList.add(new OrderItem(item, qty));
    }

    public double calculateTotalBeforeDiscount() {
        double total = 0;
        for (OrderItem orderItem : itemsList) {
            total += orderItem.calculateSubTotal();
        }
        return total;
    }

    public double calculateFinalTotal() {
        double total = calculateTotalBeforeDiscount();
        double discount = total * customer.getDiscountPercentage();
        return total - discount;
    }

    public void finalizeOrder(String method) {
        this.paymentMethod = method;
        this.paymentStatus = "Paid";
        this.orderStatus = "Completed";
        int earnedPoints = (int) (calculateFinalTotal() / 10);
        customer.addVisitAndPoints(earnedPoints);
    }
}