package com.mm.conceptrewind.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mm.conceptrewind.R;
import com.mm.conceptrewind.model.Transcript;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView speechText, summary;
    private EditText topic;
    private ImageButton speechBtn, instantsummary;
    DatabaseReference firebaseDatabase, getFirebaseDatabase,checkIndexFirebase;
    private int num = 1;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        topic = findViewById(R.id.topicSpeech);
        speechBtn = findViewById(R.id.speechBtn);
        speechText = findViewById(R.id.speechText);
        summary = findViewById(R.id.summary);
        instantsummary = findViewById(R.id.summaryInstant);


        checkIndexFirebase = FirebaseDatabase.getInstance().getReference("Transcripts");

        checkIndexFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot index : dataSnapshot.getChildren())
                {
                    num=Integer.parseInt(index.getKey().toString())+1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        instantsummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instantSummary();
            }
        });

        speechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }


    private void instantSummary() {

        String s = speechText.getText().toString();
        speechText.setMovementMethod(new ScrollingMovementMethod());
        String t = topic.getText().toString();

        Transcript transcript = new Transcript(s, t);
        if (s != "" && t != "") {

            firebaseDatabase = FirebaseDatabase.getInstance().getReference("Transcripts").child(num + "");
            getFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Summary").child(num + "").child("summary");

            FirebaseDatabase.getInstance().getReference("Summary").child(num + "").child("summary").setValue("");
            FirebaseDatabase.getInstance().getReference("Summary").child(num + "").child("topic").setValue("");

            firebaseDatabase.setValue(transcript).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(MainActivity.this, "On firebase", Toast.LENGTH_SHORT).show();


                    getFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            summary.setText(dataSnapshot.getValue().toString());
                            summary.setMovementMethod(new ScrollingMovementMethod());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });


        } else {
            Toast.makeText(this, "Enter All Fields", Toast.LENGTH_SHORT).show();
        }
    }


    public void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi Speak Something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if (data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    speechText.setText(result.get(0));
                    speechText.setMovementMethod(new ScrollingMovementMethod());
                    String t = topic.getText().toString();

                    Transcript transcript = new Transcript(result.get(0), t);
                    if (result.get(0) != "" && t != "") {

                        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Transcripts").child(num + "");
                        getFirebaseDatabase = FirebaseDatabase.getInstance().getReference("Summary").child(num + "").child("summary");

                        FirebaseDatabase.getInstance().getReference("Summary").child(num + "").child("summary").setValue("");
                        FirebaseDatabase.getInstance().getReference("Summary").child(num + "").child("topic").setValue("");

                        firebaseDatabase.setValue(transcript).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "On firebase", Toast.LENGTH_SHORT).show();


                                getFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        summary.setText(dataSnapshot.getValue().toString());
                                        summary.setMovementMethod(new ScrollingMovementMethod());
                                        summary.setLinksClickable(true);
                                        summary.setMovementMethod(LinkMovementMethod.getInstance());

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });


                    } else {
                        Toast.makeText(this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Not working", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }

    }
}
