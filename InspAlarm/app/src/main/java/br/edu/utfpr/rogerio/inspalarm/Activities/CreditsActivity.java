package br.edu.utfpr.rogerio.inspalarm.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.utfpr.rogerio.inspalarm.R;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        setTitle("Credits");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
