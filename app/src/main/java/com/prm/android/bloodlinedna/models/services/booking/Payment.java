package com.prm.android.bloodlinedna.models.services.booking;

public class Payment {
    private int id;
    private double amount;
    private int paymentMethod;
    private int transactionId;
    private String paymentDate;
    private int status;
    private String createdAt;
    private String updatedAt;

    public Payment() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public int getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(int paymentMethod) { this.paymentMethod = paymentMethod; }

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public String getPaymentDate() { return paymentDate; }
    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
