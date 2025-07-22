package com.prm.android.bloodlinedna.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.prm.android.bloodlinedna.Constants;
import com.prm.android.bloodlinedna.MessageEvent;
import com.prm.android.bloodlinedna.R;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

public class LoginFragment extends Fragment implements AuthViewModel.OnAuthResponseResult {

    private AuthViewModel authViewModel;

    private AppCompatEditText emailInput;
    private AppCompatEditText passwordInput;

    private AppCompatButton loginButton;

    private AppCompatTextView generalErrorView;

    private final MutableLiveData<Boolean> processing = new MutableLiveData<>();

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(requireActivity()).get(AuthViewModel.class);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailInput = view.findViewById(R.id.fragment_login_email_input);
        passwordInput = view.findViewById(R.id.fragment_login_password_input);
        generalErrorView = view.findViewById(R.id.fragment_login_general_error);

        view.findViewById(R.id.fragment_login_register_link).setOnClickListener(v -> {
            authViewModel.action.setValue(AuthActivity.REGISTER);
        });

        loginButton = view.findViewById(R.id.fragment_login_button);
        loginButton.setOnClickListener(v -> {
            processing.setValue(true);
        });

        processing.observe(this.getViewLifecycleOwner(), isProcessing -> {
//            loginButton.setEnabled(!isProcessing);
            if (!isProcessing) return;

            generalErrorView.setVisibility(View.GONE);

            String email;
            try {
                email = authViewModel.validateEmailInput(Objects.requireNonNull(emailInput.getText()).toString());
            } catch (Exception e) {
                emailInput.setError(e.getMessage());
                emailInput.requestFocus();
                processing.setValue(false);
                return;
            }

            String password = null;
            if (passwordInput.getText() != null) {
                password = passwordInput.getText().toString();
            }

            authViewModel.signIn(email, password, this);
        });
    }

    @Override
    public void onAuthResponse(AuthResponse authResponse) {
        requireActivity().runOnUiThread(() -> {

            if (authResponse.getStatus() == Constants.UNDEFINED_ERROR) {
                showError("Đã xảy ra lỗi khi đăng nhập, vui lòng thử lại");
            }

            if (authResponse.getStatus() == 200 && authResponse.getToken() != null) {
                SharedPreferences preferences = requireActivity()
                        .getApplicationContext()
                        .getSharedPreferences(Constants.PRINCIPAL_KEY, Context.MODE_PRIVATE);

                authViewModel.savePrincipal(authResponse, preferences);

                EventBus.getDefault().post(new MessageEvent("OK", "auth"));
                requireActivity().finish();
            } else {
                showError("Đăng nhập không thành công, vui lòng thử lại!");
            }

            processing.setValue(false);
        });
    }

    private void showError(String error) {
        generalErrorView.setVisibility(View.VISIBLE);
        generalErrorView.setText(error);
    }
}
