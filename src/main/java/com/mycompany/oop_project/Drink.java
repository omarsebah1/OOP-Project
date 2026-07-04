package com.mycompany.oop_project;

public class Drink extends MenuItem {
    private String size;
    private String type; // hot, cold :)

    public Drink(int id, String name, double price, String size, String type) {
        super(id, name, price);
        this.size = size;
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String getItemDetails() {
        return "Drink: " + getName() + " (" + type + ") | Size: " + size + " | Price: " + getPrice();
    }
}