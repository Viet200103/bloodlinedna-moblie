package com.prm.android.bloodlinedna.models.services.booking;

public class SampleCollection {
    private int id;
    private int bookingParticipantId;
    private String bookingParticipantName;
    private String sampleTypeName;
    private Integer sampleTypeCode; // optional
    private String kitTypeName;     // optional
    private Integer kitCode;        // optional
    private String collectorId;
    private String collectionDate;
    private int status;
    private String notes;           // optional

    public SampleCollection() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookingParticipantId() { return bookingParticipantId; }
    public void setBookingParticipantId(int bookingParticipantId) { this.bookingParticipantId = bookingParticipantId; }

    public String getBookingParticipantName() { return bookingParticipantName; }
    public void setBookingParticipantName(String bookingParticipantName) { this.bookingParticipantName = bookingParticipantName; }

    public String getSampleTypeName() { return sampleTypeName; }
    public void setSampleTypeName(String sampleTypeName) { this.sampleTypeName = sampleTypeName; }

    public Integer getSampleTypeCode() { return sampleTypeCode; }
    public void setSampleTypeCode(Integer sampleTypeCode) { this.sampleTypeCode = sampleTypeCode; }

    public String getKitTypeName() { return kitTypeName; }
    public void setKitTypeName(String kitTypeName) { this.kitTypeName = kitTypeName; }

    public Integer getKitCode() { return kitCode; }
    public void setKitCode(Integer kitCode) { this.kitCode = kitCode; }

    public String getCollectorId() { return collectorId; }
    public void setCollectorId(String collectorId) { this.collectorId = collectorId; }

    public String getCollectionDate() { return collectionDate; }
    public void setCollectionDate(String collectionDate) { this.collectionDate = collectionDate; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
