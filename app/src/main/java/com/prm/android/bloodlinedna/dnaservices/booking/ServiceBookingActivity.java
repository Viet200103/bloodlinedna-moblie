package com.prm.android.bloodlinedna.dnaservices.booking;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.prm.android.bloodlinedna.R;
import com.prm.android.bloodlinedna.dnaservices.ServiceViewModel;
import com.prm.android.bloodlinedna.dnaservices.listener.ServiceDetailCallback;
import com.prm.android.bloodlinedna.models.services.ServiceDetail;
import com.prm.android.bloodlinedna.models.services.booking.Participant;

public class ServiceBookingActivity extends AppCompatActivity implements ServiceDetailCallback {

    private ServiceBookingViewModel bookingViewModel;
    private ServiceViewModel serviceViewModel;
    private ProgressBar progressBar;
    private View contentContainer;

    private ViewPager2 viewPager2;

    private final BookingStateAdapter bookingStateAdapter = new BookingStateAdapter(this);

    private final MutableLiveData<Boolean> serviceLoading = new MutableLiveData<>(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        ViewModelProvider modelProvider = new ViewModelProvider(this);
        serviceViewModel =  modelProvider.get(ServiceViewModel.class);
        bookingViewModel = modelProvider.get(ServiceBookingViewModel.class);

        setContentView(R.layout.activity_service_booking);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindView();

        bookingViewModel.participants.add(new Participant());
        bookingViewModel.participants.add(new Participant());

        serviceLoading.observe(this, isLoading -> {
            if (isLoading) {
                new Thread(() -> {
                    String rawData = getIntent().getStringExtra("booking_selection");

                    if (rawData == null) {

                        runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                            finish();
                        });
                        return;
                    }

                    bookingViewModel.setBookingSelection(rawData);

                    bookingViewModel.loadSampleTypes(null, null);

                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.VISIBLE);
                        contentContainer.setVisibility(View.INVISIBLE);
                    });
                    serviceViewModel.getServiceById(
                            bookingViewModel.getBookingSelection().getServiceId(),
                            ServiceBookingActivity.this
                    );
                }).start();
            }
            progressBar.setVisibility(View.INVISIBLE);
        });
        bookingViewModel.success.observe(this, isSuccess -> {
            if (isSuccess) {
                viewPager2.setCurrentItem(1, true);
            }
        });
    }

    private void bindView() {
        progressBar = findViewById(R.id.activity_service_booking_loading_progress_view);
        contentContainer = findViewById(R.id.activity_service_booking_content_container);

        viewPager2 = findViewById(R.id.activity_service_booking_viewpager);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.setAdapter(bookingStateAdapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setSaveEnabled(false);
        viewPager2.setSaveFromParentEnabled(false);

        findViewById(R.id.activity_service_booking_back_button).setOnClickListener(v -> this.finish());
    }

    @Override
    public void onSuccess(ServiceDetail serviceDetail) {
        bookingViewModel.setServiceDetail(serviceDetail);
        runOnUiThread(() -> {
            contentContainer.setVisibility(View.VISIBLE);
            serviceLoading.setValue(false);
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }

    private static class BookingStateAdapter extends FragmentStateAdapter {

        public BookingStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new ParticipantServiceFragment();
                case 1:
                    return new CompleteBookingFragment();
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}