package com.example.a02_eeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class DeutschQuiz extends Fragment implements View.OnClickListener {
    Button gotoFinish;
    Button gotoNext;
    Map<String, String> fragen = new HashMap<>();
    List<String> fragen_pos = new ArrayList<>();
    TextView angabe;
    EditText answer_et;
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
        fragen.put("Der Lehrer schreibt auf die Tafel.", "GW");
        fragen.put("Vor langer Zeit herrschte in Österreich ein König.", "MV");
        fragen.put("Die Kinder werden am Spielplatz spielen.", "Z");
        fragen.put("Am Freitag sind wir ins Kino gegangen.", "V");
        fragen.put("Immer wieder zieht es Menschen mit ihren Skiern aber auch abseits der Piste.", "GW");
        fragen.put("Der Stau löste sich aber langsam auf, hieß es weiter.", "MV");
        fragen.put("Ein Fahrstreifen konnte mittlerweile bis zum Brenner hinauf vom Schnee befreit werden.", "V");
        fragen.put("Doch es kam immer wieder zu Lkw-Staus.", "MV");
        fragen.put("Ich denke, die Wahrscheinlichkeit, dass sich meine Tochter infiziert, ist einfach sehr groß.", "GW");
        fragen.put("Es ist wahrscheinlich, dass meine Tochter mit dem Virus in Kontakt kommt.", "GW");
        fragen.put("Der Lenker ist angeheitert mit dem Auto gefahren.", "V");

        fragen_pos.add( "Der Lehrer schreibt auf die Tafel.");
        fragen_pos.add( "Vor langer Zeit herrschte in Österreich ein König.");
        fragen_pos.add( "Die Kinder werden am Spielplatz spielen.");
        fragen_pos.add( "Am Freitag sind wir ins Kino gegangen.");
        fragen_pos.add( "Immer wieder zieht es Menschen mit ihren Skiern aber auch abseits der Piste.");
        fragen_pos.add( "Der Stau löste sich aber langsam auf, hieß es weiter.");
        fragen_pos.add( "Ein Fahrstreifen konnte mittlerweile bis zum Brenner hinauf vom Schnee befreit werden.");
        fragen_pos.add( "Doch es kam immer wieder zu Lkw-Staus.");
        fragen_pos.add( "Ich denke, die Wahrscheinlichkeit, dass sich meine Tochter infiziert, ist einfach sehr groß.");
        fragen_pos.add( "Es ist wahrscheinlich, dass meine Tochter mit dem Virus in Kontakt kommt.");
        fragen_pos.add( "Der Lenker ist angeheitert mit dem Auto gefahren.");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_deutsch_quiz,container,false);
        richtig = 0;
        falsch = 0;

        gotoFinish=view.findViewById(R.id.button_quit_d);
        gotoNext=view.findViewById(R.id.button_senden_d);
        answer_et = view.findViewById(R.id.editText_deutsch);
        angabe = view.findViewById(R.id.text_angabe);
        gotoFinish.setOnClickListener(this);
        gotoNext.setOnClickListener(this);
        fillMap();
        ((MainActivity)getActivity()).subject = true;
        ((MainActivity)getActivity()).wort = false;
        gotoFinish.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((MainActivity)getActivity()).richtig_d = richtig;
                ((MainActivity)getActivity()).falsch_d = falsch;
                Navigation.findNavController(view).navigate(R.id.action_deutschQuiz_to_finish);
            }
        });
        gotoNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    String answer_st = answer_et.getText().toString().toUpperCase();
                    if (answer_st == "" || answer_st == null){
                        Toast.makeText(getActivity(), "Eingabe darf nicht leer sein!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        checkAnswer(answer_st);
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getActivity(), "Something really bad happened!", Toast.LENGTH_LONG).show();
                }
            }
        });
        answer_et.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    try {
                        String answer_st = answer_et.getText().toString().toUpperCase();
                        if (answer_st == ""|| answer_st == null){
                            Toast.makeText(getActivity(), "Eingabe darf nicht leer sein!", Toast.LENGTH_LONG).show();
                        }
                        else{

                            checkAnswer(answer_st);
                        }
                    }
                    catch (Exception ex){
                        Toast.makeText(getActivity(), "Das hat leider nicht funktioniert.", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });


        return view;
    }


    public void checkAnswer(String answer) {
        String rightAnswer = getRightAnswer();
        if (answer.equals(rightAnswer)){
            Toast.makeText(getActivity(), "Sehr gut!", Toast.LENGTH_LONG).show();
            richtig += 1;
            ((MainActivity)getActivity()).richtig_d = richtig;
        }
        else{
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(getActivity());
            builder.setMessage("Deine Antwort war falsch. Richtig wäre: " + rightAnswer);
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
            ((MainActivity)getActivity()).falsch_d = falsch;
        }
        refreshQuestion();
    }

    public String getRightAnswer(){
        String answer = "";
        answer = fragen.get(fragen_pos.get(counter));
        return answer;
    }

    @Override
    public void onClick(View view) {
    }

    public void refreshQuestion() {
        counter += 1;
        if (counter < 10) {

            ((TextView) getView().findViewById(R.id.fortschritt_stat)).setText(counter.toString() + "/10");
            ((TextView) getView().findViewById(R.id.text_angabe)).setText(fragen_pos.get(counter));
            ((TextView) getView().findViewById(R.id.editText_deutsch)).setText("");
        }
        else{
            ((MainActivity)getActivity()).richtig_d = richtig;
            ((MainActivity)getActivity()).falsch_d = falsch;
            Navigation.findNavController(getView()).navigate(R.id.action_deutschQuiz_to_finish);
        }
    }
}
