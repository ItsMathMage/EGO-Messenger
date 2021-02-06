package com.utm.egomessenger;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth auth;
    private User user;
    private String passApp;
    private View root;
    private Handler handler;
    private TextView forgot_pass, try_count;
    private Button btn_login, btn_to_reg;

    private int count;

    //При запуску програми
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        myRef = database.getReference("users");
        user = new User();

        passApp = "11111111";
        count = 3;

        handler = new Handler();
        root = findViewById(R.id.root_element);

        //Блокування додатку на старті
        showPassApp(passApp);

        //Кнопка відновлення пароля
        forgot_pass = findViewById(R.id.forgot_pass);
        forgot_pass.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                restorePass();
            }
        });

        //Кнопка логіна
        btn_login = findViewById(R.id.bnt_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AppActivity.class));
                finish();
            }
        });

        //Кнопка навігації до реєстрації
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
        try_count = pass_window.findViewById(R.id.try_count);

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
                if (count != 0) {
                    applyFunction(dialog, pass);

                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
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
            pass.setHint("Хибний пароль!");
            count--;
            try_count.setText("Спроб: " + count);
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
                MaterialEditText name_field = register_window.findViewById(R.id.name_reg_field);
                MaterialEditText phone_field = register_window.findViewById(R.id.phone_reg_field);
                MaterialEditText password_field = register_window.findViewById(R.id.pass_reg_field);

                user.setPassword(password_field.getText().toString());
                user.setPhone(phone_field.getText().toString());
                user.setInitials(name_field.getText().toString());
                user.setEmail(email_field.getText().toString());

                if(user.getEmail().isEmpty() || user.getInitials().isEmpty() || user.getPhone().isEmpty() ||
                        user.getPassword().isEmpty()) {
                    dialog.dismiss();
                    Snackbar.make(root, "Помилка! Введіть усі дані.", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }

                if(user.getInitials().length() < 2) {
                    Snackbar.make(root, "Помилка! Коротке ім'я.", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }

                if(user.getPhone().length() < 10) {
                    Snackbar.make(root, "Помилка! Введіть коректний номер телефону.", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }

                if(user.getPassword().length() < 8) {
                    Snackbar.make(root, "Помилка! Пароль повинен мати мінімум 8 символів", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }

                if(!checkEmail(user.getEmail())) {
                    Snackbar.make(root, "Помилка! Введіть вірну пошту", BaseTransientBottomBar.LENGTH_LONG).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>(){
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);

                                Intent toAppActivity = new Intent(MainActivity.this, AppActivity.class);
                                toAppActivity.putExtra(AppActivity.KEY_USER, user);
                                startActivity(toAppActivity);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, "Помилка реєстрації! " + e.getMessage(), BaseTransientBottomBar.LENGTH_LONG).show();
                    }
                });

                dialog.dismiss();
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

    //Перевірка емейла на символ @
    private boolean checkEmail(String email) {
        for (int i = 0; i < email.length(); i++) {
            if(email.charAt(i) == '@'){
                return true;
            }
        }
        return false;
    }

    //Кпопка відновлення аккаунту
    private void restorePass() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View forgot_pass_window = inflater.inflate(R.layout.forgor_pass_window, null);
        AlertDialog dialog = builder.create();
        dialog.setView(forgot_pass_window);
        dialog.show();

        //Кнопка повернення на ActivityMain
        final Button btn_back = forgot_pass_window.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}