package com.prm.android.bloodlinedna.models.services;

public class BookingSelection {
    public enum ServiceType {
        CIVIL,
        LEGAL
    }

    private int packageId;
    private ServiceType serviceType; // Enum for "civil" or "legal"
    private boolean isPriority;
    private boolean isSpecialSample;
    private double finalPrice;

    public BookingSelection() {}

    public BookingSelection(int packageId, ServiceType serviceType, boolean isPriority,
                            boolean isSpecialSample, double finalPrice) {
        this.packageId = packageId;
        this.serviceType = serviceType;
        this.isPriority = isPriority;
        this.isSpecialSample = isSpecialSample;
        this.finalPrice = finalPrice;
    }

    public int getPackageId() { return packageId; }
    public void setPackageId(int packageId) { this.packageId = packageId; }

    public ServiceType getServiceType() { return serviceType; }
    public void setServiceType(ServiceType serviceType) { this.serviceType = serviceType; }

    public boolean isPriority() { return isPriority; }
    public void setPriority(boolean priority) { isPriority = priority; }

    public boolean isSpecialSample() { return isSpecialSample; }
    public void setSpecialSample(boolean specialSample) { isSpecialSample = specialSample; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }

    @Override
    public String toString() {
        return "BookingSelection{" +
                "packageId=" + packageId +
                ", serviceType=" + serviceType +
                ", isPriority=" + isPriority +
                ", isSpecialSample=" + isSpecialSample +
                ", finalPrice=" + finalPrice +
                '}';
    }
}
