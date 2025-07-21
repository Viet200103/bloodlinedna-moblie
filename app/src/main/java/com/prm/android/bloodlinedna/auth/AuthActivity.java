package com.prm.android.bloodlinedna.auth;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.prm.android.bloodlinedna.R;

public class AuthActivity extends AppCompatActivity {

    public static String ACTION_KEY = "kind";

    public static final int SIGN_IN = 0;
    public static final int REGISTER = 1;

    private AuthViewModel authViewModel;

    private final AuthStateAdapter stateAdapter = new AuthStateAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewPager2 viewPager2 = findViewById(R.id.activity_auth_viewpager);
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.setAdapter(stateAdapter);
        viewPager2.setUserInputEnabled(false);
        viewPager2.setSaveEnabled(false);
        viewPager2.setSaveFromParentEnabled(false);

        authViewModel.action.setValue(this.getIntent().getIntExtra(ACTION_KEY, SIGN_IN));
        authViewModel.action.observe(this, action -> {
            switch (action) {
                case SIGN_IN:
                    viewPager2.setCurrentItem(SIGN_IN, false);
                    break;
                case REGISTER:
                    viewPager2.setCurrentItem(REGISTER, false);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        });
    }

    private static class AuthStateAdapter extends FragmentStateAdapter {

        public AuthStateAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case SIGN_IN:
                    return new LoginFragment();
                case REGISTER:
                    return new RegisterFragment();
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