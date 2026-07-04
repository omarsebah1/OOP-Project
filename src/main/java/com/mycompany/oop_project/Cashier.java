package com.mycompany.oop_project;

public class Cashier extends Person {
    private String emloyeeId;

    public Cashier(String emloyeeId, int id, String name, String phone) {
        super(id, name, phone);
        this.emloyeeId = emloyeeId;
    }

    public String getEmloyeeId() {
        return emloyeeId;
    }

    public void setEmloyeeId(String emloyeeId) {
        this.emloyeeId = emloyeeId;
    }
    
    
}