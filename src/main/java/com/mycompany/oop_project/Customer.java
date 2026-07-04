package com.mycompany.oop_project;

public class Customer extends Person{
    private int loyaltyPoints;
    private int visitCount;
    
    public Customer(int id, String name, String phone, int loyaltyPoints, int visitCount) {
        super(id, name, phone);
        this.loyaltyPoints = loyaltyPoints;
        this.visitCount = visitCount;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }
    
    public void addVisitAndPoints(int points) {
        
    }
    
    public String getLoyaltyTier(){
        return "hello";
    }
    
    public double getDiscountPercentage() {
        return 0.0;
    }
}
