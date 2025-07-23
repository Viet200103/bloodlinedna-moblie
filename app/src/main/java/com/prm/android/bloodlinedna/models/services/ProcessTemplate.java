package com.prm.android.bloodlinedna.models.services;
import java.util.List;

public class ProcessTemplate {
    private int id;
    private String name;
    private String description;
    private int estimatedLabDays;
    private boolean isActive;
    private List<ProcessTemplateStep> processTemplateSteps; // optional

    public ProcessTemplate() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getEstimatedLabDays() { return estimatedLabDays; }
    public void setEstimatedLabDays(int estimatedLabDays) { this.estimatedLabDays = estimatedLabDays; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public List<ProcessTemplateStep> getProcessTemplateSteps() { return processTemplateSteps; }
    public void setProcessTemplateSteps(List<ProcessTemplateStep> processTemplateSteps) { this.processTemplateSteps = processTemplateSteps; }
}
