package com.mycompany.oop_project;

public class Drink extends MenuItem {

    private String size;

    public Drink(String id, String name, double price, String size) {
        super(id, name, price);
        this.size = size;
    }

    @Override
    public String getItemType() {
        return "Drink";
    }

    @Override
    public String getItemCategory() {
        return size;
    }

    @Override
    public String getItemDetails() {
        return String.format("%-10s | %-20s | %-10.2f | %-15s | %-8s |",
                getId(), getName(), getPrice(), size, getItemType());
    }
}
