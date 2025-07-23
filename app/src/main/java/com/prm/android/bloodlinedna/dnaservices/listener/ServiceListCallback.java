package com.prm.android.bloodlinedna.dnaservices.listener;

import com.prm.android.bloodlinedna.models.services.ServiceApiResponse;

public interface ServiceListCallback {
    void onSuccess(ServiceApiResponse response);
    void onError(Throwable t);
}
