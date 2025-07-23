package com.prm.android.bloodlinedna.dnaservices.booking;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.prm.android.bloodlinedna.R;

public class CompleteBookingFragment extends Fragment {

    public CompleteBookingFragment() {
        super(R.layout.fragment_complete_booking);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnSuccess).setOnClickListener(v -> {
            requireActivity().finish();
        });
    }
}
