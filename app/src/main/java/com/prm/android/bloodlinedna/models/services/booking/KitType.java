package com.prm.android.bloodlinedna.models.services.booking;

import java.util.Objects;

public class KitType {
    private int id;
    private String name;
    private String description;
    private boolean isActive;

    public KitType() {}

    // --- Getters and Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}
