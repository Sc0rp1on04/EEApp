package com.example.a02_eeapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ClassDragon {
    private String classID;
    private Integer progress = 0;
    private Integer progressForBar = 0;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Boolean isChanged = false;
    Integer x = 0;
    ImageView img;



    public ClassDragon(String classID){
        this.classID=classID;
        getDB();
        getDatafromFirebase();
    }

    public String getClassID(){
        return classID;
    }
    public Boolean getIsChanged(){
        return isChanged;
    }
    public void setClassID(String classID){
        this.classID = classID;
    }
    public void setIsChanged(Boolean isChanged){
        this.isChanged = isChanged;
    }
    public Integer getProgressForBar(){
        return progressForBar;
    }
    public void setProgressForBar(Integer progressForBar){
        this.progressForBar = progressForBar;
    }
    public Integer getProgress(){

        return progress;
    }

    public String getLevel(){
        progressForBar = this.progress % 100;
        Integer y = (this.progress / 100) + 1;
        String lv = y.toString();
        return lv;
    }

    public String getStrProgress(){
        progressForBar = this.progress % 100;
        Integer x = (this.progress / 100) + 1;

        String full = this.progress.toString();
        String level = "";
        String progress = "";

        progress = "Level: " + x.toString() + "\n" + "Fortschritt: " + progressForBar.toString() + "/100";

        return progress;
    }
    public void setProgress(Integer progress){
        this.progress = progress;
    }

    public void getDB(){
        firebaseDatabase = FirebaseDatabase.getInstance("https://eeapp-d4a14-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = firebaseDatabase.getReference();
    }


    public void addDatatoFirebase(String classID, Integer progress) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(isChanged == false){
                    try {

                        Map<String, String> prog = new HashMap<>();
                        prog.put("progress", progress.toString());
                        prog.put("type", "class");

                        databaseReference.child(classID).setValue(prog);

                    }
                    catch (Exception ex){

                    }
                    isChanged = true;
                }


                // after adding this data we are showing toast message.

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                //Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getDatafromFirebase() {

        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference();
        Query query = Ref.orderByKey().equalTo(classID);
        DatabaseReference fRef = FirebaseDatabase.getInstance().getReference();
        Query fquery = fRef.orderByKey().equalTo(classID);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String p = "";

                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    p = userSnapshot.child("progress").getValue(String.class);
                    setProgress(Integer.parseInt(p));
                    break;
                }


                if(p == null){
                    progress = 0;
                    addDatatoFirebase(classID, 0);
                }
                else {
                    setProgress(Integer.parseInt(p));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}
