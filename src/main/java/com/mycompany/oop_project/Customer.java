package com.mycompany.oop_project;

public class Customer extends Person {
    private int loyaltyPoints;
    private int visitCount;

    public Customer(int id, String name, String phone) {
        super(id, name, phone);
        this.loyaltyPoints = 0;
        this.visitCount = 0;
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
        this.visitCount++;
        this.loyaltyPoints += points;
    }

    public String getLoyaltyTier() {
        if (loyaltyPoints >= 15) {
            return "Diamond";
        } else if (loyaltyPoints > 7) {
            return "Gold";
        } else if (loyaltyPoints >= 4) {
            return "Silver";
        } else {
            return "Bronze";
        }
    }

    public double getDiscountPercentage() {
        if (visitCount < 5) {
            return 0.0;
        }
        String tier = getLoyaltyTier();
        if (tier.equals("Silver")) {
            return 0.02;
        } else if (tier.equals("Gold")) {
            return 0.05;
        } else if (tier.equals("Diamond")) {
            return 0.10;
        }
        return 0.0;
    }
}