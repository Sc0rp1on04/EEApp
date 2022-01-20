package com.example.a02_eeapp;

import android.content.Intent;
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
import androidx.navigation.Navigation;


public class MainWindow extends Fragment implements View.OnClickListener {
    Button gotoStatistikFragment;
    Button gotoChooseMatheType;
    Button gotoDeutschChoose;
    private int CurrentProgress = 0;
    private ProgressBar progressBar;
    String progress_str;
    ImageView img;
    Integer p = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) getView().findViewById(R.id.textViewProgress)).setText(progress_str);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_window,container,false);

        gotoStatistikFragment=view.findViewById(R.id.button_gotoStatistik);
        gotoStatistikFragment.setOnClickListener(this);

        gotoChooseMatheType=view.findViewById(R.id.button_gotoTypeMathematik);
        gotoChooseMatheType.setOnClickListener(this);

        gotoDeutschChoose=view.findViewById(R.id.button_gotoDeutschChoose);
        gotoDeutschChoose.setOnClickListener(this);

        img = (ImageView) view.findViewById(R.id.startscreen_moster);



        progressBar = view.findViewById(R.id.hp_bar);
        //Integer progress = ((MainActivity)getActivity()).progress;
        progress_str = ((MainActivity)getActivity()).classDragon.getStrProgress();
        Integer prog_drag = ((MainActivity)getActivity()).classDragon.getProgressForBar();

        String levelnr = ((MainActivity)getActivity()).classDragon.getLevel();
        int neulevelnr = Integer.parseInt(levelnr);
        if (neulevelnr == 2) {
            img.setImageResource(R.drawable.dinos);
        }

        progressBar.setProgress(prog_drag);
        progressBar.setMax(100);

        ((MainActivity)getActivity()).progress = CurrentProgress;

        gotoStatistikFragment.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainWindow_to_statistikFragment);
            }
        });
        gotoChooseMatheType.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainWindow_to_chooseTypeMathe);

            }
        });
        gotoDeutschChoose.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainWindow_to_chooseTypeDeutsch);

            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
    }
}
