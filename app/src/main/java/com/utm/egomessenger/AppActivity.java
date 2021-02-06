package com.utm.egomessenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class AppActivity extends AppCompatActivity {

    private User user;
    private View app_activity;
    private TextView testText;
    public static final String KEY_USER  = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        app_activity = findViewById(R.id.app_activity);
        testText = (TextView)findViewById(R.id.textView);
        user = (User)getIntent().getSerializableExtra(KEY_USER);

        testText.setText(user.getEmail() + user.getInitials() + user.getPhone() + user.getPassword());
        String logMsg = "Вітаю " + user.getInitials() + "!";

        Snackbar.make(app_activity, logMsg, Snackbar.LENGTH_LONG).show();
    }
}