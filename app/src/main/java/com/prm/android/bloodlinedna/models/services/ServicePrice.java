package com.prm.android.bloodlinedna.models.services;

public class ServicePrice {
    private int id;
    private int serviceId;
    private int processTemplateId;
    private String name;
    private String description;
    private double basePrice;
    private int baseParticipantCount;
    private double additionalParticipantPrice;
    private double additionalSamplePrice;
    private double legalFee;
    private double priorityFee;
    private double specialSampleFee;
    private int bufferDays;
    private String turnaroundTime;
    private boolean isActive;

    public ServicePrice() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }

    public int getProcessTemplateId() { return processTemplateId; }
    public void setProcessTemplateId(int processTemplateId) { this.processTemplateId = processTemplateId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public int getBaseParticipantCount() { return baseParticipantCount; }
    public void setBaseParticipantCount(int baseParticipantCount) { this.baseParticipantCount = baseParticipantCount; }

    public double getAdditionalParticipantPrice() { return additionalParticipantPrice; }
    public void setAdditionalParticipantPrice(double additionalParticipantPrice) { this.additionalParticipantPrice = additionalParticipantPrice; }

    public double getAdditionalSamplePrice() { return additionalSamplePrice; }
    public void setAdditionalSamplePrice(double additionalSamplePrice) { this.additionalSamplePrice = additionalSamplePrice; }

    public double getLegalFee() { return legalFee; }
    public void setLegalFee(double legalFee) { this.legalFee = legalFee; }

    public double getPriorityFee() { return priorityFee; }
    public void setPriorityFee(double priorityFee) { this.priorityFee = priorityFee; }

    public double getSpecialSampleFee() { return specialSampleFee; }
    public void setSpecialSampleFee(double specialSampleFee) { this.specialSampleFee = specialSampleFee; }

    public int getBufferDays() { return bufferDays; }
    public void setBufferDays(int bufferDays) { this.bufferDays = bufferDays; }

    public String getTurnaroundTime() { return turnaroundTime; }
    public void setTurnaroundTime(String turnaroundTime) { this.turnaroundTime = turnaroundTime; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
