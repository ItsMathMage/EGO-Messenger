package com.utm.egomessenger;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private User user;
    private String passApp;
    private View root;
    private Handler handler;

    //При запуску програми
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passApp = "11111111";
        handler = new Handler();
        root = findViewById(R.id.root_element);

        showPassApp(passApp);

    }

    //Вхід через пароль у програму
    private void showPassApp(final String passApp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View pass_window = inflater.inflate(R.layout.pass_window, null);
        AlertDialog dialog = builder.create();

        final MaterialEditText pass = pass_window.findViewById(R.id.pass_app_field);
        final Button btnCancel = pass_window.findViewById(R.id.btn_cancel);
        final Button btnApply = pass_window.findViewById(R.id.btn_apply);

        dialog.setView(pass_window);
        dialog.setCancelable(false);
        dialog.show();

        pass.setOnEditorActionListener( new MaterialEditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if( event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    applyFunction(dialog, pass);
                    return true;
                }
                return false;
            }
        });

        //Кпопка відміни
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        //Кпопка підттвердження
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyFunction(dialog, pass);
            }
        });


    }

    //Функція підтвердження
    private void applyFunction(AlertDialog dialog, MaterialEditText pass) {
        if (passApp.equals(pass.getText().toString())) {
            dialog.dismiss();
        } else {
            pass.setText("");

            //Затримка в 3 секунди
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pass.setHint("Введіть пароль");
                }
            }, 3000);
            pass.setHint("Помилка! Не вірний пароль.");
        }
    }
}