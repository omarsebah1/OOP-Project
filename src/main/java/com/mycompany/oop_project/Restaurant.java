package com.mycompany.oop_project;

import java.util.ArrayList;
public class Restaurant {
    private ArrayList<Cashier> cashiers;
    private ArrayList<Customer> customers;
    private ArrayList<MenuItem> menu;
    private ArrayList<Order> orders;

    public Restaurant(ArrayList<Cashier> cashiers, ArrayList<Customer> customers, ArrayList<MenuItem> menu, ArrayList<Order> orders) {
        this.cashiers = cashiers;
        this.customers = customers;
        this.menu = menu;
        this.orders = orders;
    }

    public ArrayList<Cashier> getCashiers() {
        return cashiers;
    }

    public void setCashiers(ArrayList<Cashier> cashiers) {
        this.cashiers = cashiers;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuItem> menu) {
        this.menu = menu;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
    
    public Customer findCustomerByPhone(String phone) {
        // return customer data
    }
    
    public ArrayList<Order> getOrdersByStatus(String status) {
        // return order status
    }
}
