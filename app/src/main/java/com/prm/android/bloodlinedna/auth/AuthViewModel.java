package com.prm.android.bloodlinedna.auth;

import android.content.SharedPreferences;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.prm.android.bloodlinedna.BuildConfig;
import com.prm.android.bloodlinedna.Constants;
import com.prm.android.bloodlinedna.DnaPrincipal;
import com.prm.android.bloodlinedna.OkHttpClientProvider;
import com.prm.android.bloodlinedna.models.auth.LoginRequest;
import com.prm.android.bloodlinedna.models.auth.User;
import com.prm.android.bloodlinedna.models.auth.UserRegisterModel;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AuthViewModel extends ViewModel {
    private final OkHttpClient client = OkHttpClientProvider.getInstance();
    private final String authUrl = BuildConfig.API_BASE_URL + "/auth";
    private final Gson gson = new Gson();

    public final MutableLiveData<Integer> action = new MutableLiveData<>();

    public void signIn(String username, String password, OnAuthResponseResult responseResult) {
        LoginRequest loginModel = new LoginRequest(username, password);

        RequestBody requestBody = RequestBody.create(
                gson.toJson(loginModel),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(authUrl + "/login")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            AuthResponse authResponse = new AuthResponse();

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                authResponse.setStatus(Constants.UNDEFINED_ERROR);
                authResponse.setError(e.getMessage());
                responseResult.onAuthResponse(authResponse);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Gson gson = new Gson();

                try(ResponseBody body = response.body()){
                    assert body != null;
                    String rawBody = body.string();
                    authResponse = gson.fromJson(rawBody, AuthResponse.class);
                    authResponse.setStatus(response.code());
                } catch (Exception e) {
                    authResponse.setStatus(response.code());
                    authResponse.setError(e.getMessage());
                }

                responseResult.onAuthResponse(authResponse);
            }
        });
    }

    public void register(UserRegisterModel model, OnAuthResponseResult responseResult) {
        RequestBody requestBody = RequestBody.create(
                gson.toJson(model),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(authUrl + "/register")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            final AuthResponse authResponse = new AuthResponse();

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                authResponse.setStatus(Constants.UNDEFINED_ERROR);
                authResponse.setError(e.getMessage());
                responseResult.onAuthResponse(authResponse);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try(ResponseBody ignored = response.body()){
                    if (response.code() == 200) {
                        authResponse.setStatus(response.code());
                    } else {
                        throw new Exception("Đăng ký không thành công");
                    }
                } catch (Exception e) {
                    authResponse.setStatus(response.code());
                    authResponse.setError(e.getMessage());
                }
                responseResult.onAuthResponse(authResponse);
            }
        });
    }

    public void savePrincipal(AuthResponse authResponse, SharedPreferences preferences) {
        preferences.edit()
                .putString(Constants.TOKEN_KEY, authResponse.getToken())
                .apply();

        User user = authResponse.getUser();

        if (user != null) {
            String raw = gson.toJson(authResponse.getUser());

            preferences.edit()
                    .putString(Constants.USER_KEY, raw)
                    .apply();
        }

        DnaPrincipal.loadFromStorage(preferences);
    }

    public void validateConfirmPassword(String password, String confirmPassword) throws Exception {
        if (!password.equals(confirmPassword)) {
            throw new Exception("Mật khẩu xác nhận không khớp");
        }
    }

    public String validatePasswordInput(String password) throws Exception {
        if (password == null || password.isBlank()) {
            throw new Exception("Mật khẩu không được để trống");
        }

        if (password.length() < 6) {
            throw new Exception("Mật khẩu phải có ít nhất 6 ký tự");
        }

         if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+=|<>?{}\\[\\]~-]).{8,}$")) {
             throw new Exception("Mật khẩu phải có tối thiểu 8 ký tự, một chữ hoa, một chữ thường, một số và một ký tự đặc biệt.");
         }

        return password;
    }

    public String validateEmailInput(String email) throws Exception {
        if (email == null || email.isBlank()) {
            throw new Exception("Email không được để trống");
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new Exception("Email không hợp lệ");
        }

        return email;
    }

    public interface OnAuthResponseResult {
        void onAuthResponse(AuthResponse authResponse);
    }
}
