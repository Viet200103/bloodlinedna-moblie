package com.prm.android.bloodlinedna.dnaservices.booking;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm.android.bloodlinedna.BuildConfig;
import com.prm.android.bloodlinedna.DnaPrincipal;
import com.prm.android.bloodlinedna.OkHttpClientProvider;
import com.prm.android.bloodlinedna.models.services.ServiceDetail;
import com.prm.android.bloodlinedna.models.services.booking.BookingDetails;
import com.prm.android.bloodlinedna.models.services.booking.Participant;
import com.prm.android.bloodlinedna.models.services.booking.BookingSelection;
import com.prm.android.bloodlinedna.models.services.booking.CreateBookingModel;
import com.prm.android.bloodlinedna.models.services.booking.InitialBookingData;
import com.prm.android.bloodlinedna.models.services.booking.SampleType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ServiceBookingViewModel extends ViewModel {
    public MutableLiveData<Boolean> success = new MutableLiveData<>(false);
    private OkHttpClient client = OkHttpClientProvider.getInstance();
    private final Gson gson = new Gson();
    private final HttpUrl serviceUrl = HttpUrl.parse( BuildConfig.API_BASE_URL + "/bookings");

    private BookingSelection bookingSelection;
    private ServiceDetail serviceDetail;

    public InitialBookingData bookingData = new InitialBookingData();
    public List<Participant> participants = new ArrayList<>();
    public List<SampleType> sampleTypes = new ArrayList<>();

    public void loadSampleTypes(Boolean isSpecialSample, Boolean isActive)  {

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(
                HttpUrl.parse(BuildConfig.API_BASE_URL + "/sample-types")
        ).newBuilder();

        if (isSpecialSample != null) {
            urlBuilder.addQueryParameter("isSpecial", isSpecialSample.toString());
        }

        if (isActive != null) {
            urlBuilder.addQueryParameter("isActive", isActive.toString());
        }

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Authorization", "Bearer " + DnaPrincipal.getInstance().getToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            if (response.isSuccessful() && responseBody != null) {
                String json = responseBody.string();
                Type listType = new TypeToken<List<SampleType>>() {}.getType();
                sampleTypes = gson.fromJson(json, listType);

                if (!bookingSelection.isSpecialSample()) {
                    sampleTypes = sampleTypes.stream()
                            .filter(sampleType -> !sampleType.isSpecial())
                            .collect(Collectors.toList());
                }
            }
        } catch (IOException ignored) {
        }
    }

    public void createBooking(CreateBookingModel bookingData, BookingCallback callback) {
        String jsonData = gson.toJson(bookingData);

        RequestBody body = RequestBody.create(
                jsonData,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(serviceUrl.url())
                .post(body)
                .addHeader("Authorization", "Bearer " + DnaPrincipal.getInstance().getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onError(new Exception("Failed to create booking", e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        BookingDetails details = gson.fromJson(response.body().string(), BookingDetails.class);
                        callback.onSuccess(details);
                    } else {
                        String errorMsg = response.body() != null ? response.body().string() : "No response body";
                        callback.onError(new Exception("Request failed: " + errorMsg));
                    }
                } catch (IOException e) {
                    callback.onError(e);
                }
            }
        });
    }

    public void setBookingSelection(BookingSelection booking) {
        this.bookingSelection = booking;
    }

    public void setBookingSelection(String raw) {
        this.bookingSelection = gson.fromJson(raw, BookingSelection.class);
    }
    public void setServiceDetail(ServiceDetail serviceDetail) {
        this.serviceDetail = serviceDetail;
    }

    public ServiceDetail getServiceDetail() {
        return serviceDetail;
    }

    public BookingSelection getBookingSelection() {
        return bookingSelection;
    }
}
