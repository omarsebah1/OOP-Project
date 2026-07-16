package com.mycompany.oop_project;

public class Food extends MenuItem {

    private final String category;

    public Food(String id, String name, double price, String category) {
        super(id, name, price);
        this.category = category;
    }

    @Override
    public String getItemType() {
        return "Food";
    }

    @Override
    public String getItemCategory() {
        return category;
    }

    @Override
    public String getItemDetails() {
        return String.format("%-10s | %-20s | %-10.2f | %-15s | %-8s |",
                getId(), getName(), getPrice(), category, getItemType());
    }
}
