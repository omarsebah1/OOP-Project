package com.mycompany.oop_project;

public class Food extends MenuItem {
    private String category;
    
    public Food(int id, String name, double price, String category) {
        super(id, name, price);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    @Override
    public String getItemDetails(){
        return "Food";
    }
    
}
