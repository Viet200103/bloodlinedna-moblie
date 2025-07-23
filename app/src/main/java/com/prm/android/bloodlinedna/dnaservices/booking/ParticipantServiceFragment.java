package com.prm.android.bloodlinedna.dnaservices.booking;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prm.android.bloodlinedna.R;
import com.prm.android.bloodlinedna.models.services.ServicePrice;
import com.prm.android.bloodlinedna.models.services.booking.BookingDetails;
import com.prm.android.bloodlinedna.models.services.booking.BookingSelection;
import com.prm.android.bloodlinedna.models.services.booking.CreateBookingModel;

import java.text.NumberFormat;
import java.util.Locale;

public class ParticipantServiceFragment extends Fragment implements BookingCallback {

    private RecyclerView recyclerView;

    private ParticipantAdapter adapter;

    private AppCompatButton checkoutButton;

    private ServiceBookingViewModel serviceBookingViewModel;

    private BookingSelection bookingSelection;

    public ParticipantServiceFragment() {
        super(R.layout.fragment_booking_service_layout);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceBookingViewModel = new ViewModelProvider(requireActivity()).get(ServiceBookingViewModel.class);
        adapter = new ParticipantAdapter(serviceBookingViewModel);
        adapter.setParticipants(serviceBookingViewModel.participants);
        bookingSelection = serviceBookingViewModel.getBookingSelection();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.fragment_booking_service_participant_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        checkoutButton = view.findViewById(R.id.fragment_booking_service_participant_checkout_button);
        checkoutButton.setOnClickListener(v -> {
            CreateBookingModel bookingModel = new CreateBookingModel();

            bookingModel.setAdministrative(bookingSelection.getServiceType() == BookingSelection.ServiceType.LEGAL);
            bookingModel.setUsesSpecialSample(bookingSelection.isSpecialSample());
            bookingModel.setParticipants(serviceBookingViewModel.participants);
            bookingModel.setPriority(bookingSelection.isPriority());
            bookingModel.setServicePriceId(bookingSelection.getServicePriceId());

            serviceBookingViewModel.createBooking(bookingModel, this);
        });

        ServicePrice servicePrice = serviceBookingViewModel.getServiceDetail().getServicePriceModel().get(0);

        TextView baseCostView =view.findViewById(R.id.tvBaseCost);
        baseCostView.setText(servicePrice.getBasePrice() + "");

        TextView fastCostView =view.findViewById(R.id.tvFastFee);
        if (serviceBookingViewModel.getBookingSelection().isPriority()) {
            fastCostView.setText(formatCurrency(servicePrice.getPriorityFee()));
        } else  {
            fastCostView.setText("0");
        }

        TextView legalCostView =view.findViewById(R.id.tvAdminFee);
        if (bookingSelection.getServiceType() == BookingSelection.ServiceType.LEGAL) {
            legalCostView.setText(formatCurrency(servicePrice.getLegalFee()));
        } else {
            legalCostView.setText("0");
        }

        TextView totalCostView =view.findViewById(R.id.tvTotal);
        totalCostView.setText(formatCurrency(serviceBookingViewModel.getBookingSelection().getFinalPrice()));
    }


    @Override
    public void onSuccess(BookingDetails details) {
        requireActivity().runOnUiThread(() -> {
            serviceBookingViewModel.success.setValue(true);
        });
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public static String formatCurrency(Double value) {
        double numberValue = (value != null) ? value : 0.0;
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(numberValue);
    }
}
