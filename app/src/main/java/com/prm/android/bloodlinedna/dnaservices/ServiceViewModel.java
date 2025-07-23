package com.prm.android.bloodlinedna.dnaservices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.prm.android.bloodlinedna.BuildConfig;
import com.prm.android.bloodlinedna.OkHttpClientProvider;
import com.prm.android.bloodlinedna.dnaservices.listener.ServiceDetailCallback;
import com.prm.android.bloodlinedna.dnaservices.listener.ServiceListCallback;
import com.prm.android.bloodlinedna.models.services.ServiceApiResponse;
import com.prm.android.bloodlinedna.models.services.ServiceDetail;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ServiceViewModel extends ViewModel {
    private OkHttpClient client = OkHttpClientProvider.getInstance();

    private final Gson gson = new Gson();
    private final HttpUrl serviceUrl = HttpUrl.parse( BuildConfig.API_BASE_URL + "/services");

    public AtomicInteger selectedServiceId = null;

    public void getServiceById(int serviceId, @NonNull ServiceDetailCallback callback) {

        HttpUrl.Builder urlBuilder = serviceUrl.newBuilder()
                .addPathSegment(serviceId + "");

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onError(new RuntimeException("Error when fetching service " + e.getMessage(), e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try (ResponseBody body = response.body()) {
                    if (!response.isSuccessful()) {
                        callback.onError(new IOException("HTTP " + response.code() + " " + response.message()));
                        return;
                    }
                    if (body == null) {
                        callback.onError(new IOException("Empty response body"));
                        return;
                    }
                    String json = body.string();
                    ServiceDetail detail = gson.fromJson(json, ServiceDetail.class);
                    callback.onSuccess(detail);
                } catch (Exception e) {
                    callback.onError(new RuntimeException("Error parsing service detail " + e.getMessage(), e));
                }
            }
        });
    }

    public void getServices(
            @Nullable
            String searchName,
            int type,
            int page,
            int pageSize,
            @NonNull
            ServiceListCallback callback
    ) {

        // Build URL: /services?{page,...}
        HttpUrl.Builder urlBuilder = serviceUrl.newBuilder()
                .addQueryParameter("page", Integer.toString(page))
                .addQueryParameter("pageSize", Integer.toString(pageSize));

        if (searchName != null && !searchName.trim().isEmpty()) {
            urlBuilder.addQueryParameter("searchName", searchName.trim());
        }
        if (type > 0) {
            urlBuilder.addQueryParameter("type", Integer.toString(type));
        }

        HttpUrl url = urlBuilder.build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onError(new RuntimeException("Error when fetching service", e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try (ResponseBody body = response.body()) {
                    if (!response.isSuccessful()) {
                        callback.onError(new IOException("HTTP " + response.code() + " " + response.message()));
                        return;
                    }
                    if (body == null) {
                        callback.onError(new IOException("Empty response body"));
                        return;
                    }
                    String json = body.string();
                    ServiceApiResponse data = gson.fromJson(json, ServiceApiResponse.class);
                    callback.onSuccess(data);
                } catch (Exception ex) {
                    callback.onError(new RuntimeException("Error parsing service response", ex));
                }
            }
        });
    }
}
