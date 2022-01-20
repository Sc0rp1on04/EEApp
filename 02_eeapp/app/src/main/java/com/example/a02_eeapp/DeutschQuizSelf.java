package com.example.a02_eeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeutschQuizSelf extends Fragment implements View.OnClickListener {
    Button gotoFinish;
    Button nomen;
    Button adjektiv;
    Button verb;
    Button artikel;
    Map<String, Integer> fragen = new HashMap<>();
    List<String> fragen_pos = new ArrayList<>();
    List<Integer> fragen_a = new ArrayList<>();
    TextView angabe;
    Integer counter = 0;
    Integer richtig = 0;
    Integer falsch = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) getView().findViewById(R.id.text_angabe)).setText(fragen_pos.get(0));
        ((TextView) getView().findViewById(R.id.fortschritt_stat)).setText(counter.toString()+"/10");

    }

    public void fillMap(){
        // 1 = Nomen, 2 = Verb, 3 = Adjektiv, 4 = Artikel

        fragen.put("Hund", 1);
        fragen.put("alt", 3);
        fragen.put("spielen", 2);
        fragen.put("schön", 3);
        fragen.put("ein", 4);
        fragen.put("Katze", 1);
        fragen.put("gehen", 2);
        fragen.put("die", 4);
        fragen.put("Fisch", 1);
        fragen.put("laufen", 2);
        fragen.put("hoch", 3);

        fragen_a.add(1);
        fragen_a.add(3);
        fragen_a.add(2);
        fragen_a.add(3);
        fragen_a.add(4);
        fragen_a.add(1);
        fragen_a.add(2);
        fragen_a.add(4);
        fragen_a.add(1);
        fragen_a.add(2);
        fragen_a.add(3);

        fragen_pos.add( "Hund");
        fragen_pos.add( "alt");
        fragen_pos.add( "spielen");
        fragen_pos.add( "schön");
        fragen_pos.add( "ein");
        fragen_pos.add( "Katze");
        fragen_pos.add( "gehen");
        fragen_pos.add( "die");
        fragen_pos.add( "Fisch");
        fragen_pos.add( "laufen");
        fragen_pos.add( "hoch");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_deutsch_quiz_self,container,false);
        richtig = 0;
        falsch = 0;
        ((MainActivity)getActivity()).subject = true;
        ((MainActivity)getActivity()).wort = true;
        angabe = view.findViewById(R.id.text_angabe);
        gotoFinish=view.findViewById(R.id.button_quit_ds);
        nomen=view.findViewById(R.id.button_nomen);
        verb=view.findViewById(R.id.button_verb);
        adjektiv=view.findViewById(R.id.button_adjetkiv);
        artikel=view.findViewById(R.id.button_artikel);
        gotoFinish.setOnClickListener(this);
        nomen.setOnClickListener(this);
        verb.setOnClickListener(this);
        adjektiv.setOnClickListener(this);
        artikel.setOnClickListener(this);
        fillMap();
        gotoFinish.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((MainActivity)getActivity()).richtig_ds = richtig;
                ((MainActivity)getActivity()).falsch_ds = falsch;
                Navigation.findNavController(view).navigate(R.id.action_deutschQuizSelf_to_finish);
            }
        });
        nomen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    checkAnswer(1);
                }
                catch (Exception ex){
                    Toast.makeText(getActivity(), "Something really bad happened!", Toast.LENGTH_LONG).show();
                }
            }
        });
        verb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    checkAnswer(2);
                }
                catch (Exception ex){
                    Toast.makeText(getActivity(), "Something really bad happened!", Toast.LENGTH_LONG).show();
                }
            }
        });
        adjektiv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    checkAnswer(3);
                }
                catch (Exception ex){
                    Toast.makeText(getActivity(), "Something really bad happened!", Toast.LENGTH_LONG).show();
                }
            }
        });
        artikel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    checkAnswer(4);
                }
                catch (Exception ex){
                    Toast.makeText(getActivity(), "Something really bad happened!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {
    }

    public void checkAnswer(Integer answer) {
        Integer rightAnswer = getRightAnswer();
        if (answer == rightAnswer){
            Toast.makeText(getActivity(), "Sehr gut!", Toast.LENGTH_LONG).show();
            richtig += 1;
            ((MainActivity)getActivity()).richtig_ds = richtig;
        }
        else{
            String type = "";
            if(rightAnswer == 1){
                type = "Nomen";
            }
            else if(rightAnswer == 2){
                type = "Verb";
            }
            else if(rightAnswer == 3){
                type = "Adjektiv";
            }
            else{
                type = "Artikel";
            }
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(getActivity());
            builder.setMessage("Deine Antwort war falsch. Richtig wäre: " + type);
            builder.setTitle("Falsch!");
            builder.setCancelable(false);
            builder
                    .setNegativeButton(
                            "OK",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            falsch += 1;
            ((MainActivity)getActivity()).falsch_ds = falsch;
        }
        refreshQuestion();
    }

    public Integer getRightAnswer(){
        Integer answer = 0;
        answer = fragen_a.get(counter);
        return answer;
    }

    public void refreshQuestion() {
        counter += 1;
        if (counter < 10) {

            ((TextView) getView().findViewById(R.id.fortschritt_stat)).setText(counter.toString() + "/10");
            ((TextView) getView().findViewById(R.id.text_angabe)).setText(fragen_pos.get(counter));
        }
        else{
            ((MainActivity)getActivity()).richtig_ds = richtig;
            ((MainActivity)getActivity()).falsch_ds = falsch;
            Navigation.findNavController(getView()).navigate(R.id.action_deutschQuizSelf_to_finish);
        }
    }
}
