package com.mycompany.oop_project;

import java.util.ArrayList;

public class Restaurant {
    private ArrayList<Cashier> cashiers;
    private ArrayList<Customer> customers;
    private ArrayList<MenuItem> menu;
    private ArrayList<Order> orders;

    public Restaurant() {
        this.cashiers = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public void addCashier(Cashier c) {
        cashiers.add(c);
    }

    public void addCustomer(Customer c) {
        customers.add(c);
    }

    public void addMenuItem(MenuItem m) {
        menu.add(m);
    }

    public void addOrder(Order o) {
        orders.add(o);
    }

    public ArrayList<Cashier> getCashiers() {
        return cashiers;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<MenuItem> getMenu() {
        return menu;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public Customer findCustomerByPhone(String phone) {
        for (Customer c : customers) {
            if (c.getPhone().equals(phone)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Order> getOrdersByStatus(String status) {
        ArrayList<Order> filteredOrders = new ArrayList<>();
        for (Order o : orders) {
            if (o.getOrderStatus().equalsIgnoreCase(status)) {
                filteredOrders.add(o);
            }
        }
        return filteredOrders;
    }
}