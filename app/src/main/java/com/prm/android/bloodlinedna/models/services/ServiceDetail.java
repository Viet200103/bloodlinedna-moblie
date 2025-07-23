package com.prm.android.bloodlinedna.models.services;

import java.util.List;

public class ServiceDetail {
    private int id;
    private String name;
    private String description;
    private boolean isActive;
    private List<ServicePrice> servicePriceModel;       // optional
    private List<ProcessTemplate> processTemplateModel; // optional
    private List<String> relationship;

    public ServiceDetail() {
    }

    public ServiceDetail(
            int id, String name, String description, boolean isActive,
            List<ServicePrice> servicePriceModel,
            List<ProcessTemplate> processTemplateModel,
            List<String> relationship
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.servicePriceModel = servicePriceModel;
        this.processTemplateModel = processTemplateModel;
        this.relationship = relationship;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<ServicePrice> getServicePriceModel() {
        return servicePriceModel;
    }

    public void setServicePriceModel(List<ServicePrice> servicePriceModel) {
        this.servicePriceModel = servicePriceModel;
    }

    public List<ProcessTemplate> getProcessTemplateModel() {
        return processTemplateModel;
    }

    public void setProcessTemplateModel(List<ProcessTemplate> processTemplateModel) {
        this.processTemplateModel = processTemplateModel;
    }

    public List<String> getRelationship() {
        return relationship;
    }

    public void setRelationship(List<String> relationship) {
        this.relationship = relationship;
    }
}
