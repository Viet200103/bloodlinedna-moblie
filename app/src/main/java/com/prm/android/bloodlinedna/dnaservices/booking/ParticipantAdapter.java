package com.prm.android.bloodlinedna.dnaservices.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.prm.android.bloodlinedna.R;
import com.prm.android.bloodlinedna.models.auth.Gender;
import com.prm.android.bloodlinedna.models.services.booking.Participant;
import com.prm.android.bloodlinedna.models.services.booking.SampleType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {

    private Gender[] genderOption = new Gender[]{
            new Gender(1, "Nam"),
            new Gender(2, "Nữ")
    };

    private List<Participant> participants = new ArrayList<>();
    private ServiceBookingViewModel viewModel;

    public ParticipantAdapter(ServiceBookingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_participant, parent, false);
        return new ParticipantViewHolder(view);
    }

    public abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        Participant participant = participants.get(position);
        Context context = holder.itemView.getContext();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                viewModel.getServiceDetail().getRelationship()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spnRelation.setAdapter(adapter);

        holder.tvTitle.setText("Người tham gia " + (position + 1));
        holder.edtName.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                participant.setFullName(s.toString());
            }
        });

        ArrayAdapter<Gender> genderArrayAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                genderOption
        );
        genderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holder.spnGender.setAdapter(genderArrayAdapter);

        ArrayAdapter<SampleType> sampleTypeAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                viewModel.sampleTypes
        );
        sampleTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spnSampleType.setAdapter(sampleTypeAdapter);

        holder.edtBirthDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Chọn ngày")
                    .build();

            datePicker.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                Instant instant = Instant.ofEpochMilli(selection);
                participant.setDateOfBirth(instant.toString());
                holder.edtBirthDate.setText(datePicker.getHeaderText());
            });
        });

        holder.edtAppointmentDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Chọn ngày")
                    .build();

            datePicker.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                Instant instant = Instant.ofEpochMilli(selection);
                participant.setAppointmentDate(instant.toString());
                holder.edtAppointmentDate.setText(datePicker.getHeaderText());
            });
        });

        holder.edtAddress.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                participant.setCollectionAddress(s.toString());
            }
        });

        holder.spnRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                participant.setRelationship(viewModel.getServiceDetail().getRelationship().get(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        holder.spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                participant.setSexForTesting(genderOption[pos].getValue());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        holder.spnSampleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                participant.setSampleTypeId(viewModel.sampleTypes.get(pos).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        holder.rgMethod.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbHome) {
                participant.setSampleCollectionMethod(1);
            } else if (checkedId == R.id.rbStaff) {
                participant.setSampleCollectionMethod(2);
            } else if (checkedId == R.id.rbOffice) {
                participant.setSampleCollectionMethod(3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
        notifyDataSetChanged();
    }

    public static class ParticipantViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        AppCompatEditText edtName, edtBirthDate, edtAddress, edtAppointmentDate;
        Spinner spnRelation, spnGender, spnSampleType, spnTimeSlot;
        RadioGroup rgMethod;
        RadioButton rbHome, rbStaff, rbOffice;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            edtName = itemView.findViewById(R.id.edtName);
            edtBirthDate = itemView.findViewById(R.id.edtBirthDate);
            edtAddress = itemView.findViewById(R.id.edtAddress);
            edtAppointmentDate = itemView.findViewById(R.id.edtAppointmentDate);

            spnRelation = itemView.findViewById(R.id.spnRelation);
            spnGender = itemView.findViewById(R.id.spnGender);
            spnSampleType = itemView.findViewById(R.id.spnSampleType);
//            spnTimeSlot = itemView.findViewById(R.id.spnTimeSlot);

            rgMethod = itemView.findViewById(R.id.rgMethod);
            rbHome = itemView.findViewById(R.id.rbHome);
            rbStaff = itemView.findViewById(R.id.rbStaff);
            rbOffice = itemView.findViewById(R.id.rbOffice);
        }
    }

}
