package com.mycompany.oop_project;

public class Drink extends MenuItem{
    private String size;

    public Drink(String size, int id, String name, double price) {
        super(id, name, price);
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    
    @Override
    public String getItemDetails(){
        return "Drink";
    }
}
