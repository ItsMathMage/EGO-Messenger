package com.utm.egomessenger;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    //При створенні віджета
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        ImageView profile_btn = view.findViewById(R.id.profile_btn);
        ImageView settings_btn = view.findViewById(R.id.settings_btn);
        ImageView logout_btn = view.findViewById(R.id.logout_btn);

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