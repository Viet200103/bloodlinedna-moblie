package com.prm.android.bloodlinedna.models.services.booking;

import java.util.List;

public class InitialBookingData {

    public static class Service {
        private int id;
        private String name;
        private List<String> relationship;

        public Service() {}

        public Service(int id, String name, List<String> relationship) {
            this.id = id;
            this.name = name;
            this.relationship = relationship;
        }

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public List<String> getRelationship() { return relationship; }
        public void setRelationship(List<String> relationship) { this.relationship = relationship; }
    }

    public static class Options {
        private boolean isAdministrative;
        private boolean isPriority;
        private boolean isSpecialSample;

        public Options() {}

        public Options(boolean isAdministrative, boolean isPriority, boolean isSpecialSample) {
            this.isAdministrative = isAdministrative;
            this.isPriority = isPriority;
            this.isSpecialSample = isSpecialSample;
        }

        public boolean isAdministrative() { return isAdministrative; }
        public void setAdministrative(boolean administrative) { isAdministrative = administrative; }

        public boolean isPriority() { return isPriority; }
        public void setPriority(boolean priority) { isPriority = priority; }

        public boolean isSpecialSample() { return isSpecialSample; }
        public void setSpecialSample(boolean specialSample) { isSpecialSample = specialSample; }
    }

    private Service service;
    private BookingPackage selectedPackage;
    private Options options;
    private double finalPrice;

    public InitialBookingData() {}

    public InitialBookingData(Service service, BookingPackage selectedPackage, Options options, double finalPrice) {
        this.service = service;
        this.selectedPackage = selectedPackage;
        this.options = options;
        this.finalPrice = finalPrice;
    }

    public Service getService() { return service; }
    public void setService(Service service) { this.service = service; }

    public BookingPackage getSelectedPackage() { return selectedPackage; }
    public void setSelectedPackage(BookingPackage selectedPackage) { this.selectedPackage = selectedPackage; }

    public Options getOptions() { return options; }
    public void setOptions(Options options) { this.options = options; }

    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }
}
