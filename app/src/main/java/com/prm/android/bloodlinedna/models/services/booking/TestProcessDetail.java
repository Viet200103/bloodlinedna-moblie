package com.prm.android.bloodlinedna.models.services.booking;

import java.util.List;

public class TestProcessDetail {
    private int id;
    private String stepName;
    private String stepDescription;
    private int stepOrder;
    private int status;
    private String startedAt;
    private String completedAt;
    private String notes;
    private List<TestProcessResult> results;

    public TestProcessDetail() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStepName() { return stepName; }
    public void setStepName(String stepName) { this.stepName = stepName; }

    public String getStepDescription() { return stepDescription; }
    public void setStepDescription(String stepDescription) { this.stepDescription = stepDescription; }

    public int getStepOrder() { return stepOrder; }
    public void setStepOrder(int stepOrder) { this.stepOrder = stepOrder; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getStartedAt() { return startedAt; }
    public void setStartedAt(String startedAt) { this.startedAt = startedAt; }

    public String getCompletedAt() { return completedAt; }
    public void setCompletedAt(String completedAt) { this.completedAt = completedAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<TestProcessResult> getResults() { return results; }
    public void setResults(List<TestProcessResult> results) { this.results = results; }
}
