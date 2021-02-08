package com.utm.egomessenger;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class AppFragment extends Fragment {

    private View app;
    private static boolean isLock = true;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static AppFragment newInstance(String param1, String param2) {
        AppFragment fragment = new AppFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AppFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        app = inflater.inflate(R.layout.fragment_app, container, false);

        return app;
    }

    //При створенні віджета
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        ImageView profile_btn = view.findViewById(R.id.profile_btn);
        ImageView settings_btn = view.findViewById(R.id.settings_btn);
        ImageView logout_btn = view.findViewById(R.id.logout_btn);

        //Кнопка Блокування додатку
        ImageView lock_btn = view.findViewById(R.id.lock_btn);
        lock_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLock) {
                    lock_btn.setImageResource(R.drawable.ic_unlc_btn);
                } else {
                    lock_btn.setImageResource(R.drawable.ic_lock_btn);
                }
                isLock = !isLock;
            }
        });

        //Навігація до профіля
        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.profileFragment);
            }
        });

        //Навігація до налаштувань
        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.settingsFragment);
            }
        });

        //Кнопка інформації
        ImageView info_btn = view.findViewById(R.id.info_btn);
        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppActivity root = (AppActivity)getActivity();

                AlertDialog.Builder builder = new AlertDialog.Builder(root);
                LayoutInflater inflater = LayoutInflater.from(root);
                View info_window = inflater.inflate(R.layout.info_window, null);

                AlertDialog dialog = builder.create();
                dialog.setView(info_window);

                //Кпопка окей
                Button no_exit_btn = info_window.findViewById(R.id.no_exit_btn);
                no_exit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        //Вихід з аккаунта
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppActivity root = (AppActivity)getActivity();

                AlertDialog.Builder builder = new AlertDialog.Builder(root);
                LayoutInflater inflater = LayoutInflater.from(root);
                View confirm_exit = inflater.inflate(R.layout.confirm_exit, null);

                AlertDialog dialog = builder.create();
                dialog.setView(confirm_exit);

                //Відміна виходу з аккаунту
                Button no_exit_btn = confirm_exit.findViewById(R.id.no_exit_btn);
                no_exit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                //Підтвердження виходу з аккаунту
                Button yes_exit_btn = confirm_exit.findViewById(R.id.yes_exit_btn);
                yes_exit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent toAppActivity = new Intent(root, MainActivity.class);
                        startActivity(toAppActivity);
                        root.finish();
                    }
                });

                dialog.show();
            }
        });


    }
}