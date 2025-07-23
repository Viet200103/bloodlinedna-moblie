package com.prm.android.bloodlinedna.models.services.booking;

import java.util.List;

public class CreateBookingModel {
    private int servicePriceId;
    private boolean isAdministrative;
    private boolean isPriority;
    private boolean usesSpecialSample;
    private List<Participant> participants;

    public CreateBookingModel() {
    }

    public CreateBookingModel(
            int servicePriceId, boolean isAdministrative, boolean isPriority,
            boolean usesSpecialSample, List<Participant> participants
    ) {
        this.servicePriceId = servicePriceId;
        this.isAdministrative = isAdministrative;
        this.isPriority = isPriority;
        this.usesSpecialSample = usesSpecialSample;
        this.participants = participants;
    }

    public int getServicePriceId() {
        return servicePriceId;
    }

    public void setServicePriceId(int servicePriceId) {
        this.servicePriceId = servicePriceId;
    }

    public boolean isAdministrative() {
        return isAdministrative;
    }

    public void setAdministrative(boolean administrative) {
        isAdministrative = administrative;
    }

    public boolean isPriority() {
        return isPriority;
    }

    public void setPriority(boolean priority) {
        isPriority = priority;
    }

    public boolean isUsesSpecialSample() {
        return usesSpecialSample;
    }

    public void setUsesSpecialSample(boolean usesSpecialSample) {
        this.usesSpecialSample = usesSpecialSample;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}