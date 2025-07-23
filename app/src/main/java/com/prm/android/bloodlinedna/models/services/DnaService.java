package com.prm.android.bloodlinedna.models.services;

import java.util.Objects;

public class DnaService {
    private int id;
    private String name;
    private String description;
    private double basePrice;
    private String accuracy; // optional field
    private String turnaroundTime;
    private boolean isActive;

    // Constructors
    public DnaService() {}

    public DnaService(int id, String name, String description, double basePrice,
                      String accuracy, String turnaroundTime, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.accuracy = accuracy;
        this.turnaroundTime = turnaroundTime;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public String getAccuracy() { return accuracy; }
    public void setAccuracy(String accuracy) { this.accuracy = accuracy; }

    public String getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(String turnaroundTime) { this.turnaroundTime = turnaroundTime; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DnaService)) return false;
        DnaService that = (DnaService) o;
        return id == that.id &&
                Double.compare(that.basePrice, basePrice) == 0 &&
                isActive == that.isActive &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(accuracy, that.accuracy) &&
                Objects.equals(turnaroundTime, that.turnaroundTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, basePrice, accuracy, turnaroundTime, isActive);
    }
}
