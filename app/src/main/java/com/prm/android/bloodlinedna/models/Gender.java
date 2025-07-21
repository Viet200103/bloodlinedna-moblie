package com.prm.android.bloodlinedna.models;

import androidx.annotation.NonNull;

public class Gender {
    private final int value;
    private final String label;

    public Gender(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return label; // Hiển thị label trên Spinner
    }
}
