package com.prm.android.bloodlinedna.models.services;

public class ProcessTemplateStep {
    private int id;
    private int stepOrder;
    private int processTemplateId;
    private String stepName;
    private String description;
    private int estimatedDurationDays;

    public ProcessTemplateStep() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStepOrder() { return stepOrder; }
    public void setStepOrder(int stepOrder) { this.stepOrder = stepOrder; }

    public int getProcessTemplateId() { return processTemplateId; }
    public void setProcessTemplateId(int processTemplateId) { this.processTemplateId = processTemplateId; }

    public String getStepName() { return stepName; }
    public void setStepName(String stepName) { this.stepName = stepName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getEstimatedDurationDays() { return estimatedDurationDays; }
    public void setEstimatedDurationDays(int estimatedDurationDays) { this.estimatedDurationDays = estimatedDurationDays; }
}
