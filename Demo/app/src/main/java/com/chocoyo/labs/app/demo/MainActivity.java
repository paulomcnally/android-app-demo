package com.chocoyo.labs.app.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chocoyo.labs.app.demo.ui.JuanActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stephentuso.welcome.WelcomeHelper;

public class MainActivity extends AppCompatActivity {

    WelcomeHelper welcomeScreen;

    private Button create;
    private EditText cardNumber;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // welcome message
        welcomeScreen = new WelcomeHelper(this, MyWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);

        // database
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");

        // initialize
        initCreateNumber();

        initButtonOpen();

    }

    private void initButtonOpen() {
        Button button = (Button) findViewById(R.id.open);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openJuan();
            }
        });
    }

    private void openJuan() {
        Intent intent = new Intent(
                getApplicationContext(),
                JuanActivity.class);
        startActivity(intent);
    }

    private void initCreateNumber() {
        create = (Button) findViewById(R.id.create);
        cardNumber = (EditText) findViewById(R.id.card_number);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (cardNumber.getText().toString().isEmpty()) {
                   Toast.makeText(getApplicationContext(),
                           "Loco, no has puesto tu numero",
                           Toast.LENGTH_LONG).show();
               } else {
                   myRef.setValue(cardNumber.getText().toString());
               }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }
}
