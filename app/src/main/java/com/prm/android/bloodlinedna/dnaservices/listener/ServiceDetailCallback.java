package com.prm.android.bloodlinedna.dnaservices.listener;

import com.prm.android.bloodlinedna.models.services.ServiceDetail;

public interface ServiceDetailCallback {
    void onSuccess(ServiceDetail serviceDetail);
    void onError(Throwable throwable);
}
