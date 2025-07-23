package com.prm.android.bloodlinedna.models.services.booking;

import java.util.List;

public class BookingDetails {
    private int id;
    private String bookingCode;
    private BookingUser bookingUser;
    private int resultSummaryId;
    private int servicePriceId;
    private String serviceName;
    private double totalAmount;
    private boolean isAdministrative;
    private boolean isPriority;
    private boolean usesSpecialSample;
    private int status;
    private List<Payment> payments;
    private String createdAt;
    private String updatedAt;
    private List<ParticipantData> participantModels;
    private List<TestProcessDetail> testProcesses;
    private List<SampleCollection> sampleCollections;

    public BookingDetails() {}

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBookingCode() { return bookingCode; }
    public void setBookingCode(String bookingCode) { this.bookingCode = bookingCode; }

    public BookingUser getBookingUser() { return bookingUser; }
    public void setBookingUser(BookingUser bookingUser) { this.bookingUser = bookingUser; }

    public int getResultSummaryId() { return resultSummaryId; }
    public void setResultSummaryId(int resultSummaryId) { this.resultSummaryId = resultSummaryId; }

    public int getServicePriceId() { return servicePriceId; }
    public void setServicePriceId(int servicePriceId) { this.servicePriceId = servicePriceId; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public boolean isAdministrative() { return isAdministrative; }
    public void setAdministrative(boolean administrative) { isAdministrative = administrative; }

    public boolean isPriority() { return isPriority; }
    public void setPriority(boolean priority) { isPriority = priority; }

    public boolean isUsesSpecialSample() { return usesSpecialSample; }
    public void setUsesSpecialSample(boolean usesSpecialSample) { this.usesSpecialSample = usesSpecialSample; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public List<Payment> getPayments() { return payments; }
    public void setPayments(List<Payment> payments) { this.payments = payments; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }

    public List<ParticipantData> getParticipantModels() { return participantModels; }
    public void setParticipantModels(List<ParticipantData> participantModels) { this.participantModels = participantModels; }

    public List<TestProcessDetail> getTestProcesses() { return testProcesses; }
    public void setTestProcesses(List<TestProcessDetail> testProcesses) { this.testProcesses = testProcesses; }

    public List<SampleCollection> getSampleCollections() { return sampleCollections; }
    public void setSampleCollections(List<SampleCollection> sampleCollections) { this.sampleCollections = sampleCollections; }
}
