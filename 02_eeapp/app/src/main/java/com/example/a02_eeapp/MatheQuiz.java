package com.example.a02_eeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
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
import android.view.View.OnKeyListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MatheQuiz extends Fragment implements View.OnClickListener {
    Button gotoFinish;
    Button gotoNext;
    Integer counter = 0;
    Integer richtig = 0;
    Integer falsch = 0;
    EditText answer_et;
    Integer result;
    String symbolus;
    Integer rechenart;
    String mal_art;
    Integer range;
    List<Integer> sec_value = new ArrayList<Integer>();
    List<String> questions = new ArrayList<String>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mathe_quiz,container,false);

        richtig = 0;
        falsch = 0;
        rechenart = ((MainActivity)getActivity()).rechenart;
        mal_art = ((MainActivity)getActivity()).mal_art;
        range = ((MainActivity)getActivity()).range;
        ((MainActivity)getActivity()).subject = false;

        gotoFinish=view.findViewById(R.id.button_quit_m);
        gotoFinish.setOnClickListener(this);

        gotoNext=view.findViewById(R.id.button_senden_m);
        gotoNext.setOnClickListener(this);

        gotoFinish.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ((MainActivity)getActivity()).richtig = richtig;
                ((MainActivity)getActivity()).falsch = falsch;
                Navigation.findNavController(view).navigate(R.id.action_matheQuiz_to_finish);
            }
        });
        answer_et = view.findViewById(R.id.editText_mathe);

        answer_et.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    try {
                        String answer_st = answer_et.getText().toString();
                        if (answer_st == ""){
                            Toast.makeText(getActivity(), "Eingabe darf nicht leer sein!", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Integer answer = Integer.parseInt(answer_st);
                            checkAnswer(answer);
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

        gotoNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    String answer_st = answer_et.getText().toString();
                    if (answer_st == ""){
                        Toast.makeText(getActivity(), "Eingabe darf nicht leer sein!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Integer answer = Integer.parseInt(answer_st);
                        checkAnswer(answer);
                    }
                }
                catch (Exception ex){
                    //Toast.makeText(getActivity(), "Something really bad happened!", Toast.LENGTH_LONG).show();
                }
            }

        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshQuestion();
    }


    @Override
    public void onClick(View view) {
    }

    public Integer findRechenart(Integer rand1, Integer rand2){
        Integer result;
        if (rechenart == 1){
            result = rand1 + rand2;
            symbolus = "+";
        }
        else if (rechenart == 2){
            result = rand1 - rand2;
            symbolus = "-";
        }
        else if (rechenart == 3){
            result = rand1 * rand2;
            symbolus = "*";
        }
        else{
            result = rand1 / rand2;
            symbolus = ":";
        }
        return result;
    }

    public void refreshQuestion() {
        counter += 1;
        if (counter <= 10) {
            int min;
            int max;
            int rand1;
            int rand2;
            if(rechenart == 1 || rechenart == 2) {
                min = 1;
                max = range;
                rand1 = (int) (Math.random() * (max - min)) + min;
                rand2 = (int) (Math.random() * (max - min)) + min;
                String r1 = Integer.toString(rand1);
                String r2 = Integer.toString(rand2);
                String question = r2 + symbolus + r1;
                while (questions.contains(question)){
                    rand1 = (int) (Math.random() * (max - min)) + min;
                    rand2 = (int) (Math.random() * (max - min)) + min;
                    r1 = Integer.toString(rand1);
                    r2 = Integer.toString(rand2);
                    question = r2 + symbolus + r1;
                }
                questions.add(question);
            }
            else if (rechenart == 3){
                if (mal_art == "Mix"){
                    min = 1;
                    max = 10;
                    rand1 = (int) (Math.random() * (max - min)) + min;
                    rand2 = (int) (Math.random() * (max - min)) + min;
                    String r1 = Integer.toString(rand1);
                    String r2 = Integer.toString(rand2);
                    String question = r2 + symbolus + r1;
                    while (questions.contains(question)){
                        rand1 = (int) (Math.random() * (max - min)) + min;
                        rand2 = (int) (Math.random() * (max - min)) + min;
                        r1 = Integer.toString(rand1);
                        r2 = Integer.toString(rand2);
                        question = r2 + symbolus + r1;
                    }
                    questions.add(question);
                }
                else{
                    min = 1;
                    max = 11;
                    rand1 = Integer.parseInt(mal_art);
                    rand2 = (int) (Math.random() * (max - min)) + min;
                    while (sec_value.contains(rand2)){
                        rand2 = (int) (Math.random() * (max - min)) + min;
                    }
                    sec_value.add(rand2);
                }
            }
            else{
                min = 1;
                max = 10;
                rand1 = (int) (Math.random() * (max - min)) + min;
                rand2 = (int) (Math.random() * (max - min)) + min;

                int res = rand1*rand2;
                rand2 = rand1;
                rand1 = res;

                String r1 = Integer.toString(rand1);
                String r2 = Integer.toString(rand2);
                String question = r2 + symbolus + r1;
                while (questions.contains(question)){
                    rand1 = (int) (Math.random() * (max - min)) + min;
                    rand2 = (int) (Math.random() * (max - min)) + min;
                    res = rand1*rand2;
                    rand2 = rand1;
                    rand1 = res;
                    r1 = Integer.toString(rand1);
                    r2 = Integer.toString(rand2);
                    question = r2 + symbolus + r1;
                }
                questions.add(question);
            }

            String r1 = Integer.toString(rand1);
            String r2 = Integer.toString(rand2);
            String question;

            if (rand1 < rand2){
                result = findRechenart(rand2, rand1);
                question = r2 + symbolus + r1;
            }
            else{
                result = findRechenart(rand1, rand2);
                question = r1 + symbolus + r2;

            }

            ((TextView) getView().findViewById(R.id.fortschritt_stat)).setText(counter.toString() + "/10");
            ((TextView) getView().findViewById(R.id.text_angabe)).setText(question);
            ((TextView) getView().findViewById(R.id.editText_mathe)).setText("");

        }
        else{
            ((MainActivity)getActivity()).richtig = richtig;
            ((MainActivity)getActivity()).falsch = falsch;
            Navigation.findNavController(getView()).navigate(R.id.action_matheQuiz_to_finish);
        }
    }

    public void checkAnswer(Integer answer) {

        if(result == answer){
            Toast.makeText(getActivity(), "Toll!", Toast.LENGTH_LONG).show();
            richtig += 1;
            ((MainActivity)getActivity()).richtig = richtig;
        }
        else{
            AlertDialog.Builder builder
                    = new AlertDialog
                    .Builder(getActivity());
            builder.setMessage("Deine Antwort war falsch. Richtig wäre: " + result.toString());
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
            ((MainActivity)getActivity()).falsch = falsch;
        }
        refreshQuestion();
    }
}
