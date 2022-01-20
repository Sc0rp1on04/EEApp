package com.example.a02_eeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


public class Finish extends Fragment implements View.OnClickListener {
    Button gotoStartseite;
    ImageView imgfin;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_finish,container,false);

        gotoStartseite=view.findViewById(R.id.button_main);
        gotoStartseite.setOnClickListener(this);

        gotoStartseite.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_finish_to_mainWindow);

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Boolean subject = ((MainActivity)getActivity()).subject;
        Boolean wort = ((MainActivity)getActivity()).wort;
        Integer richtig = ((MainActivity)getActivity()).richtig;
        Integer falsch = ((MainActivity)getActivity()).falsch;
        Integer richtig_d = ((MainActivity)getActivity()).richtig_d;
        Integer falsch_d = ((MainActivity)getActivity()).falsch_d;
        Integer richtig_ds = ((MainActivity)getActivity()).richtig_ds;
        Integer falsch_ds = ((MainActivity)getActivity()).falsch_ds;
        Integer progress = ((MainActivity) getActivity()).classDragon.getProgress();

        if (!subject) {
            progress += richtig;
        }
        else {
            if (!wort){
                progress += richtig_d;
            }
            else {
                progress += richtig_ds;
            }

        }


        ((MainActivity) getActivity()).classDragon.setProgress(progress);
        if(progress >= 100) {
            imgfin = (ImageView) view.findViewById(R.id.startscreen_moster);
            imgfin.setImageResource(R.drawable.dinos);
        }

        String c = ((MainActivity) getActivity()).classDragon.getClassID();
        Integer p = ((MainActivity) getActivity()).classDragon.getProgress();

        ((MainActivity) getActivity()).classDragon.addDatatoFirebase(c, p);
        ((MainActivity) getActivity()).classDragon.setIsChanged(false);


        //daten berechnen
        String rich = ((MainActivity)getActivity()).userStatistik.getRichtig();
        Integer rich_i = Integer.parseInt(rich);
        String rich_d = ((MainActivity)getActivity()).userStatistik.getRichtig_d();
        Integer rich_d_i = Integer.parseInt(rich_d);
        String rich_ds = ((MainActivity)getActivity()).userStatistik.getRichtig_ds();
        Integer rich_ds_i = Integer.parseInt(rich_ds);
        String fals = ((MainActivity)getActivity()).userStatistik.getFalsch();
        Integer fals_i = Integer.parseInt(fals);
        String fals_d = ((MainActivity)getActivity()).userStatistik.getFalsch_d();
        Integer fals_d_i = Integer.parseInt(fals_d);
        String fals_ds = ((MainActivity)getActivity()).userStatistik.getFalsch_ds();
        Integer fals_ds_i = Integer.parseInt(fals_ds);

        String uID = ((MainActivity)getActivity()).userStatistik.getUserID();
        String cID = ((MainActivity)getActivity()).userStatistik.getClassID();

        Integer rich_comp = rich_i + richtig;
        Integer fals_comp = fals_i + falsch;

        Integer rich_d_comp = rich_d_i + richtig_d;
        Integer fals_d_comp = fals_d_i + falsch_d;

        Integer rich_ds_comp = rich_ds_i + richtig_ds;
        Integer fals_ds_comp = fals_ds_i + falsch_ds;

        ((MainActivity)getActivity()).addDatatoFirebase(uID, cID, rich_comp, fals_comp, rich_d_comp, fals_d_comp, rich_ds_comp, fals_ds_comp);
        ((MainActivity)getActivity()).isChanged = false;
        //(MainActivity)getActivity()).progress = progress;
        if (subject) {
            if (wort) {
                ((TextView) getView().findViewById(R.id.text_feedback)).setText("Richtig: " + richtig_ds.toString() + "\n" + "Falsch: " + falsch_ds.toString());

            } else {
                ((TextView) getView().findViewById(R.id.text_feedback)).setText("Richtig: " + richtig_d.toString() + "\n" + "Falsch: " + falsch_d.toString());
            }
        }
        else {
            ((TextView) getView().findViewById(R.id.text_feedback)).setText("Richtig: " + richtig.toString() + "\n" + "Falsch: " + falsch.toString());
        }
    }

    @Override
    public void onClick(View view) {
    }
}
