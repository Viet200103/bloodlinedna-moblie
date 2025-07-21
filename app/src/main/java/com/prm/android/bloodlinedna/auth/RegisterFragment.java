package com.prm.android.bloodlinedna.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.prm.android.bloodlinedna.Constants;
import com.prm.android.bloodlinedna.R;
import com.prm.android.bloodlinedna.models.Gender;
import com.prm.android.bloodlinedna.models.UserRegisterModel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment implements AuthViewModel.OnAuthResponseResult {

    private AppCompatEditText fullNameInput;
    private AppCompatEditText emailInput;
    private AppCompatEditText phoneInput;
    private AppCompatEditText dateOfBirthInput;
    private AppCompatEditText addressInput;
    private Spinner genderInput;
    private AppCompatEditText passwordInput;
    private AppCompatEditText confirmPasswordInput;
    private AppCompatButton registerButton;

    private AppCompatTextView generalErrorView;

    private Gender selectedGender;

    private final MutableLiveData<Boolean> processing = new MutableLiveData<>();

    private AuthViewModel authViewModel;

    public RegisterFragment() {
        super(R.layout.fragmnet_register);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

        processing.observe(this, isProcessing -> {
            try {
                registerButton.setEnabled(!isProcessing);
                if (!isProcessing) {
                    return;
                }

                String fullName;
                try {
                    fullName = getTextFromInput(fullNameInput);
                    if (fullName == null || fullName.isBlank()) {
                        throw new Exception("Họ và tên không được để trống");
                    }
                } catch (Exception e) {
                    showError(fullNameInput, e.getMessage());
                    return;
                }

                String email;
                try {
                    email = authViewModel.validateEmailInput(getTextFromInput(emailInput));
                } catch (Exception e) {
                    showError(emailInput, e.getMessage());
                    return;
                }

                String password;
                try {
                    password = authViewModel.validatePasswordInput(getTextFromInput(passwordInput));
                } catch (Exception e) {
                    showError(passwordInput, e.getMessage());
                    return;
                }

                try {
                    authViewModel.validateConfirmPassword(
                            password,
                            getTextFromInput(confirmPasswordInput)
                    );
                } catch (Exception e) {
                    showError(confirmPasswordInput, e.getMessage());
                    return;
                }

                UserRegisterModel model = new UserRegisterModel();
                model.setFullName(fullName);
                model.setEmail(email);
                model.setPassword(password);
                model.setPhoneNumber(getTextFromInput(phoneInput));
                model.setDateOfBirth(getTextFromInput(dateOfBirthInput));
                model.setAddress(getTextFromInput(addressInput));
                model.setGender(selectedGender.getValue());

                authViewModel.register(model, this);
            } catch (Exception e) {
                // To Do
            }
        });
    }

    private String getTextFromInput(AppCompatEditText input) {
        Editable editable = input.getText();
        if (editable == null) {
            return  null;
        }
        return editable.toString();
    }

    private void showError(AppCompatEditText input, String message) {
        input.setError(message);
        input.requestFocus();
        processing.setValue(false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);

        setupDateOfBirth();
        setupGender();

        registerButton = view.findViewById(R.id.fragment_register_submit_button);
        registerButton.setOnClickListener(v -> processing.setValue(true));

        view.findViewById(R.id.fragment_register_login_link).setOnClickListener(v -> {
            authViewModel.action.setValue(AuthActivity.SIGN_IN);
        });
    }

    private void setupDateOfBirth() {
        dateOfBirthInput.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Chọn ngày")
                    .build();

            datePicker.show(getChildFragmentManager(), "DATE_PICKER");

            datePicker.addOnPositiveButtonClickListener(selection -> {
                Instant instant = Instant.ofEpochMilli(selection);
                String isoString = instant.toString();
                dateOfBirthInput.setText(isoString);
            });
        });
    }

    private void setupGender() {
        List<Gender> genderList = new ArrayList<>();
        genderList.add(new Gender(-1, "Chọn giới tính"));
        genderList.add(new Gender(1, "Nam"));
        genderList.add(new Gender(2, "Nữ"));
        genderList.add(new Gender(3, "Khác"));
        genderList.add(new Gender(4, "Không muốn tiết lộ"));

        ArrayAdapter<Gender> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                genderList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderInput.setAdapter(adapter);

        genderInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = (Gender) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedGender = genderList.get(0);
            }
        });
    }

    private void bindView(View view) {
        fullNameInput = view.findViewById(R.id.fragment_register_full_name_input);
        emailInput = view.findViewById(R.id.fragment_register_email_input);
        phoneInput = view.findViewById(R.id.fragment_register_phone_input);
        dateOfBirthInput = view.findViewById(R.id.fragment_register_date_of_birth_input);
        addressInput = view.findViewById(R.id.fragment_register_address_input);
        genderInput = view.findViewById(R.id.fragment_register_gender_input);
        passwordInput = view.findViewById(R.id.fragment_register_password_input);
        confirmPasswordInput = view.findViewById(R.id.fragment_register_confirm_password_input);
        generalErrorView = view.findViewById(R.id.fragment_register_general_error);
    }

    @Override
    public void onAuthResponse(AuthResponse authResponse) {
        requireActivity().runOnUiThread(() -> {
            processing.setValue(false);
            if (authResponse.getStatus() == Constants.UNDEFINED_ERROR) {
                showError("Đã xảy ra lỗi khi đăng ký, vui lòng thử lại");
            }
            if (authResponse.getStatus() == 200) {
                authViewModel.action.setValue(AuthActivity.SIGN_IN);
                return;
            }
            showError("Đăng ký không thành công, vui lòng thử lại!");
        });
    }

    private void showError(String error) {
        generalErrorView.setVisibility(View.VISIBLE);
        generalErrorView.setText(error);
    }
}
