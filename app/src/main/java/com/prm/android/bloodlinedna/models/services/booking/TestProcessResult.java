package com.prm.android.bloodlinedna.models.services.booking;

public class TestProcessResult {
    private int id;
    private int participantId;
    private String participantName;
    private String resultValue;
    private String createdAt;
    private String updatedAt;
    private Marker marker;

    public TestProcessResult() {}

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getParticipantId() { return participantId; }
    public void setParticipantId(int participantId) { this.participantId = participantId; }

    public String getParticipantName() { return participantName; }
    public void setParticipantName(String participantName) { this.participantName = participantName; }

    public String getResultValue() { return resultValue; }
    public void setResultValue(String resultValue) { this.resultValue = resultValue; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public Marker getMarker() { return marker; }
    public void setMarker(Marker marker) { this.marker = marker; }

    // --- Nested Marker class ---
    public static class Marker {
        private String name;
        private int type;
        private String description;

        public Marker() {}

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getType() { return type; }
        public void setType(int type) { this.type = type; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
