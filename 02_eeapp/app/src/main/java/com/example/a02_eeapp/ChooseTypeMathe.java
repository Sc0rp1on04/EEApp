package com.example.a02_eeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class ChooseTypeMathe extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener  {
    Button button_plus;
    Button button_minus;
    Button button_mal;
    Button button_durch;
    String [] mal_spielart = {"Mix", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    Integer [] range_pm = {100, 1000, 50};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_choose_type_mathe,container,false);

        button_plus=view.findViewById(R.id.button_plus);
        button_plus.setOnClickListener(this);
        button_minus=view.findViewById(R.id.button_minus);
        button_minus.setOnClickListener(this);
        button_mal=view.findViewById(R.id.button_mal);
        button_mal.setOnClickListener(this);
        button_durch=view.findViewById(R.id.button_durch);
        button_durch.setOnClickListener(this);

        button_plus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((MainActivity)getActivity()).rechenart = 1;
                Navigation.findNavController(view).navigate(R.id.action_chooseTypeMathe_to_matheQuiz);
            }
        });
        button_minus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((MainActivity)getActivity()).rechenart = 2;
                Navigation.findNavController(view).navigate(R.id.action_chooseTypeMathe_to_matheQuiz);
            }
        });
        button_mal.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((MainActivity)getActivity()).rechenart = 3;
                Navigation.findNavController(view).navigate(R.id.action_chooseTypeMathe_to_matheQuiz);

            }
        });
        button_durch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((MainActivity)getActivity()).rechenart = 4;
                Navigation.findNavController(view).navigate(R.id.action_chooseTypeMathe_to_matheQuiz);
            }
        });

        return view;
    }


    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Spinner spin = (Spinner) getView().findViewById(R.id.malreihen_spinner);
        spin.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,mal_spielart);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        Spinner spin_range = (Spinner) getView().findViewById(R.id.range);
        spin_range.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa_r = new ArrayAdapter<Integer>(getActivity(),android.R.layout.simple_spinner_item,range_pm);
        aa_r.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin_range.setAdapter(aa_r);
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        switch (arg0.getId()){
            case R.id.malreihen_spinner:
                ((MainActivity)getActivity()).mal_art = mal_spielart[position];
                break;
            case R.id.range:
                ((MainActivity)getActivity()).range = range_pm[position];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // Auto-generated method stub
    }
}