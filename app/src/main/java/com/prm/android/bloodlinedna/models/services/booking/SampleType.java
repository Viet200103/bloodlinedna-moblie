package com.prm.android.bloodlinedna.models.services.booking;

import androidx.annotation.NonNull;

public class SampleType {
    private int id;
    private String name;
    private String description;
    private boolean isSpecial;
    private boolean isActive;
    private KitType kitType;

    public SampleType() {}

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isSpecial() { return isSpecial; }
    public void setSpecial(boolean special) { isSpecial = special; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public KitType getKitType() { return kitType; }
    public void setKitType(KitType kitType) { this.kitType = kitType; }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
