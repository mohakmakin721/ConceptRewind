package com.mm.conceptrewind.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mm.conceptrewind.R;


public class SecondFragment extends AppCompatActivity {


    private TextView summaryElaborate,topicElaborate;
    private String summary,topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);

        summaryElaborate = findViewById(R.id.summaryHomeElaborate);
        topicElaborate = findViewById(R.id.topicHomeElaborate);

        topic = getIntent().getStringExtra("Topic");
        summary = getIntent().getStringExtra("Notes");


        topicElaborate.setText(topic);
        summaryElaborate.setMovementMethod(new ScrollingMovementMethod());
        summaryElaborate.setLinksClickable(true);
        summaryElaborate.setMovementMethod(LinkMovementMethod.getInstance());
        summaryElaborate.setText(summary);

    }
}
