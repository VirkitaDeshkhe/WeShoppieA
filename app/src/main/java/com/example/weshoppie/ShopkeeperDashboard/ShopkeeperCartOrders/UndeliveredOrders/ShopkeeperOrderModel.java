package com.example.weshoppie.ShopkeeperDashboard.ShopkeeperCartOrders.UndeliveredOrders;

public class ShopkeeperOrderModel {
    String documentID, Status, Time, Customer_ID, Cust_Name;

    public String getCust_Name() {
        return Cust_Name;
    }

    public void setCust_Name(String cust_Name) {
        Cust_Name = cust_Name;
    }

    public ShopkeeperOrderModel() {
    }

    public ShopkeeperOrderModel(String status, String time) {
        Status = status;
        Time = time;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }
}