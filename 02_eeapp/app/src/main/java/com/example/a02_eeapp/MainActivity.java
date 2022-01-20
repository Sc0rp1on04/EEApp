package com.example.a02_eeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static int AUTH_REQUEST_CODE = 7192; // Any number
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener listener;

    private List<AuthUI .IdpConfig> providers;
    Integer rechenart;
    String mal_art;
    Integer range;
    Integer richtig = 0;
    Integer falsch = 0;
    Integer richtig_d = 0;
    Integer falsch_d = 0;
    Integer richtig_ds = 0;
    Integer falsch_ds = 0;
    Integer progress = 0;
    String test = "";
    String userID;
    Boolean check_exists = false;
    String classID = "4a";
    Boolean newUser = false;
    Boolean isChanged = false;
    ClassDragon classDragon = new ClassDragon(classID);
    Boolean subject = false;
    Boolean wort = false;
    //mathe


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    UserStatistik userStatistik;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        if(listener != null) firebaseAuth.removeAuthStateListener(listener);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        firebaseDatabase = FirebaseDatabase.getInstance("link to FirebaseDatabase");
        databaseReference = firebaseDatabase.getReference(test);
        userStatistik = new UserStatistik();
        userStatistik.setUserID(userID);
        userStatistik.setClassID(classID);
    }

    public void addDatatoFirebase(String userID, String classID, Integer richtig, Integer falsch, Integer richtig_d, Integer falsch_d, Integer richtig_ds, Integer falsch_ds) {
        userStatistik.setUserID(userID);
        userStatistik.setRichtig(richtig.toString());
        userStatistik.setFalsch(falsch.toString());
        userStatistik.setRichtig_d(richtig_d.toString());
        userStatistik.setFalsch_d(falsch_d.toString());
        userStatistik.setRichtig_ds(richtig_ds.toString());
        userStatistik.setFalsch_ds(falsch_ds.toString());
        firebaseDatabase = FirebaseDatabase.getInstance("Link to FirebaseDatabase");
        databaseReference = firebaseDatabase.getReference(test);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(isChanged == false){

                    try {

                        Map<String, String> stat = new HashMap<>();
                        stat.put("falsch", userStatistik.getFalsch());
                        stat.put("richtig", userStatistik.getRichtig());
                        stat.put("falsch_d", userStatistik.getFalsch_d());
                        stat.put("richtig_d", userStatistik.getRichtig_d());
                        stat.put("falsch_ds", userStatistik.getFalsch_ds());
                        stat.put("richtig_ds", userStatistik.getRichtig_ds());
                        stat.put("classID", userStatistik.getClassID());
                        stat.put("userId", userStatistik.getUserID());
                        stat.put("type", "player");


                        databaseReference.setValue(stat);

                    }
                    catch (Exception ex){
                        Toast.makeText(MainActivity.this, "can't add data" + ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                    isChanged = true;
                }


                // after adding this data we are showing toast message.

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getDatafromFirebase() {

        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference();
        Query query = Ref.orderByKey().equalTo(userID);
        DatabaseReference fRef = FirebaseDatabase.getInstance().getReference();
        Query fquery = fRef.orderByKey().equalTo(userID);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String rich = "";
                String fals = "";
                String rich_d = "";
                String fals_d = "";
                String rich_ds = "";
                String fals_ds = "";
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    if (userSnapshot.child("type").getValue(String.class).equals("player")){
                        if(userSnapshot.getKey().equals(test)){
                            rich = userSnapshot.child("richtig").getValue(String.class);
                            fals = userSnapshot.child("falsch").getValue(String.class);
                            rich_d = userSnapshot.child("richtig_d").getValue(String.class);
                            fals_d = userSnapshot.child("falsch_d").getValue(String.class);
                            rich_ds = userSnapshot.child("richtig_ds").getValue(String.class);
                            fals_ds = userSnapshot.child("falsch_ds").getValue(String.class);

                            userStatistik.setRichtig(rich);
                            userStatistik.setFalsch(fals);
                            userStatistik.setRichtig_d(rich_d);
                            userStatistik.setFalsch_d(fals_d);
                            userStatistik.setRichtig_ds(rich_ds);
                            userStatistik.setFalsch_ds(fals_ds);
                            userStatistik.setUserID(userID);
                            check_exists = true;

                        }

                    }

                }
                if(check_exists == false){
                    addDatatoFirebase(test, classID, 0, 0, 0, 0, 0, 0);
                    getDatafromFirebase();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }

    private void init() {
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build() // Email
        );

        firebaseAuth = FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Get user
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    test =  classID + "_" + user.getUid();
                    userID = user.getUid();
                    if (newUser == true){
                        addDatatoFirebase(test, classID, 0, 0, 0, 0, 0, 0);
                    }
                    else {
                        newUser = false;
                        getDatafromFirebase();
                    }
                }
                else {
                    //Login
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(), AUTH_REQUEST_CODE);
                    newUser = true;
                }
            }
        };
    }
}
