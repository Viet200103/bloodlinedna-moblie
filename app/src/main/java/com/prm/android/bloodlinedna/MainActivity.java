package com.prm.android.bloodlinedna;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.prm.android.bloodlinedna.auth.AuthActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        findViewById(R.id.register).setOnClickListener(v -> {
            openAuthActivity(AuthActivity.REGISTER);
        });

        findViewById(R.id.login).setOnClickListener(v -> {
            openAuthActivity(AuthActivity.SIGN_IN);
        });
    }

    private void openAuthActivity(int type) {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.putExtra(AuthActivity.ACTION_KEY, type);
        startActivity(intent);
    }
}