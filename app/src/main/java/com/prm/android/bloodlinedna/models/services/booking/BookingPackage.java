package com.prm.android.bloodlinedna.models.services.booking;

public class BookingPackage {
    private int id;
    private String name;
    private double basePrice;
    private int baseParticipants;
    private double additionalPrice;
    private double legalFee;
    private double priorityFee;
    private double specialSampleFee;
    private String turnaroundTime;

    // Constructor
    public BookingPackage() {}

    public BookingPackage(int id, String name, double basePrice, int baseParticipants,
                          double additionalPrice, double legalFee, double priorityFee,
                          double specialSampleFee, String turnaroundTime) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.baseParticipants = baseParticipants;
        this.additionalPrice = additionalPrice;
        this.legalFee = legalFee;
        this.priorityFee = priorityFee;
        this.specialSampleFee = specialSampleFee;
        this.turnaroundTime = turnaroundTime;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public int getBaseParticipants() { return baseParticipants; }
    public void setBaseParticipants(int baseParticipants) { this.baseParticipants = baseParticipants; }

    public double getAdditionalPrice() { return additionalPrice; }
    public void setAdditionalPrice(double additionalPrice) { this.additionalPrice = additionalPrice; }

    public double getLegalFee() { return legalFee; }
    public void setLegalFee(double legalFee) { this.legalFee = legalFee; }

    public double getPriorityFee() { return priorityFee; }
    public void setPriorityFee(double priorityFee) { this.priorityFee = priorityFee; }

    public double getSpecialSampleFee() { return specialSampleFee; }
    public void setSpecialSampleFee(double specialSampleFee) { this.specialSampleFee = specialSampleFee; }

    public String getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(String turnaroundTime) { this.turnaroundTime = turnaroundTime; }
}
