package com.example.a02_eeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class ChooseTypeDeutsch extends Fragment implements View.OnClickListener {
    Button button_zeiten;
    Button button_wortarten;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_choose_type_deutsch,container,false);

        button_wortarten=view.findViewById(R.id.button_wortarten);
        button_wortarten.setOnClickListener(this);
        button_zeiten=view.findViewById(R.id.button_zeiten);
        button_zeiten.setOnClickListener(this);
        button_wortarten.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_chooseTypeDeutsch_to_deutschQuizSelf);
            }
        });
        button_zeiten.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_chooseTypeDeutsch_to_deutschQuiz);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
    }
}