package com.prm.android.bloodlinedna;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.prm.android.bloodlinedna.auth.AuthActivity;
import com.prm.android.bloodlinedna.dnaservices.DnaServiceActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton = findViewById(R.id.activity_main_login_button);
        if (DnaPrincipal.getInstance().getUser() == null) {
            loginButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(v -> openAuthActivity());
        }

        findViewById(R.id.activity_main_view_service_button).setOnClickListener(v -> {
            Intent intent = new Intent(this, DnaServiceActivity.class);
            startActivity(intent);
        });
    }

    private void openAuthActivity() {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.putExtra(AuthActivity.ACTION_KEY, AuthActivity.SIGN_IN);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.type.equals("auth") && event.message.equals("OK")) {
            loginButton.setVisibility(View.INVISIBLE);
        }
    }
}