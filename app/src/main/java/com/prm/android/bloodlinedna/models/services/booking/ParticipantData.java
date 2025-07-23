package com.prm.android.bloodlinedna.models.services.booking;

public class ParticipantData {
    private int id;
    private String email;
    private String fullName;
    private String dateOfBirth;
    private int sexForTesting;
    private int sampleCollectionMethod;
    private int sampleTypeId;
    private String appointmentDate;
    private String collectionAddress;
    private String relationship;

    public ParticipantData() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public int getSexForTesting() { return sexForTesting; }
    public void setSexForTesting(int sexForTesting) { this.sexForTesting = sexForTesting; }

    public int getSampleCollectionMethod() { return sampleCollectionMethod; }
    public void setSampleCollectionMethod(int sampleCollectionMethod) { this.sampleCollectionMethod = sampleCollectionMethod; }

    public int getSampleTypeId() { return sampleTypeId; }
    public void setSampleTypeId(int sampleTypeId) { this.sampleTypeId = sampleTypeId; }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getCollectionAddress() { return collectionAddress; }
    public void setCollectionAddress(String collectionAddress) { this.collectionAddress = collectionAddress; }

    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
}

