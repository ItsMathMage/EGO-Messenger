package com.utm.egomessenger;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private User user;
    private String passApp;
    private View root;
    private Handler handler;
    private TextView forgot_pass;
    private Button btn_login, btn_to_reg;

    //При запуску програми
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        user = new User();

        passApp = "11111111";
        handler = new Handler();
        root = findViewById(R.id.root_element);

        showPassApp(passApp);

        forgot_pass = findViewById(R.id.forgot_pass);
        forgot_pass.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                restorePass();
            }
        });

        btn_login = findViewById(R.id.bnt_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppActivity.class));
                finish();
            }
        });



        btn_to_reg = findViewById(R.id.btn_to_reg);
        btn_to_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegister();
            }
        });
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

        //Кпопка підтвердження через Enter
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
            Snackbar.make(root, "Успішно", BaseTransientBottomBar.LENGTH_LONG).show();
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

    //Кпопка яка відкриває форму реєстрації
    private void toRegister() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window, null);
        AlertDialog dialog = builder.create();
        dialog.setView(register_window);
        dialog.show();

        //Кнопка яка здійснює реєстрацію користувача
        final Button btn_register = register_window.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialEditText email_field = register_window.findViewById(R.id.email_reg_field);
                user.setEmail(email_field.getText().toString());
                MaterialEditText name_field = register_window.findViewById(R.id.name_reg_field);
                user.setInitials(name_field.getText().toString());
                MaterialEditText phone_field = register_window.findViewById(R.id.phone_reg_field);
                user.setPhone(phone_field.getText().toString());
                MaterialEditText password_field = register_window.findViewById(R.id.pass_reg_field);
                user.setPassword(password_field.getText().toString());

                myRef.push().setValue(user);

                dialog.dismiss();
                Snackbar.make(root, "Успішно", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        });

        //Кнопка яка закриває форму реєстрації
        final Button btn_to_main = register_window.findViewById(R.id.btn_to_main);
        btn_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //Кпопка відновлення аккаунту
    private void restorePass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View forgot_pass_window = inflater.inflate(R.layout.forgor_pass_window, null);
        AlertDialog dialog = builder.create();
        dialog.setView(forgot_pass_window);
        dialog.show();


        final Button btn_back = forgot_pass_window.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void textMessage(View v, String message) {
        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show();
    }

    private boolean validate(String field) {
        if (field.isEmpty()) {
            return false;
        }
        return true;
    }
}