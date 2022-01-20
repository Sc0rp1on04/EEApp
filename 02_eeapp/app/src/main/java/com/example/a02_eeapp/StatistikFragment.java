package com.example.a02_eeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class StatistikFragment extends Fragment {
    ImageView imgstat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistik,container,false);
        imgstat = (ImageView) view.findViewById(R.id.startscreen_moster);

        String levelnr = ((MainActivity)getActivity()).classDragon.getLevel();
        int neulevelnr = Integer.parseInt(levelnr);
        if (neulevelnr == 2) {
            imgstat.setImageResource(R.drawable.dinos);
        }

        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String r = ((MainActivity)getActivity()).userStatistik.getRichtig();
        Integer richtig = Integer.parseInt(r);
        String f =  ((MainActivity)getActivity()).userStatistik.getFalsch();
        Integer falsch = Integer.parseInt(f);
        ((TextView) getView().findViewById(R.id.fortschritt_rf)).setText("Richtig: " + richtig.toString() + "\n" + "Falsch: " + falsch.toString());
        String r_d = ((MainActivity)getActivity()).userStatistik.getRichtig_d();
        Integer richtig_d = Integer.parseInt(r_d);
        String f_d =  ((MainActivity)getActivity()).userStatistik.getFalsch_d();
        Integer falsch_d = Integer.parseInt(f_d);
        ((TextView) getView().findViewById(R.id.fortschritt_rf_d)).setText("Richtig: " + richtig_d.toString() + "\n" + "Falsch: " + falsch_d.toString());
        String r_ds = ((MainActivity)getActivity()).userStatistik.getRichtig_ds();
        Integer richtig_ds = Integer.parseInt(r_ds);
        String f_ds =  ((MainActivity)getActivity()).userStatistik.getFalsch_ds();
        Integer falsch_ds = Integer.parseInt(f_ds);
        ((TextView) getView().findViewById(R.id.fortschritt_rf_ds)).setText("Richtig: " + richtig_ds.toString() + "\n" + "Falsch: " + falsch_ds.toString());

    }
}