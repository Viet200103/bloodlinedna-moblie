package com.prm.android.bloodlinedna.dnaservices.booking;

import com.prm.android.bloodlinedna.models.services.booking.BookingDetails;

public interface BookingCallback {
    void onSuccess(BookingDetails details);
    void onError(Exception e);
}
